package scheduler

import (
	"math/rand"
	"sync"
	"time"
)

/*
Stealing Scheduler managers a pool of tasks and workers
*/
type StealingScheduler struct {
	wg         *sync.WaitGroup
	taskPool   []DEqueue
	workerPool []*stealingWorker
	capacity   int
	nextThread int
}

/*
Stealing worker manages a single thread in a thread pool
*/
type stealingWorker struct {
	id       int
	parent   *StealingScheduler
	killChan chan bool
	running  bool
}

/*
Create Stealing Scheduler
*/
func NewStealingScheduler(capacity int) *StealingScheduler {

	//Initalize task pool and Worker pool
	taskPool := []DEqueue{}
	workerPool := []*stealingWorker{}

	//Initialize scheduler
	s := &StealingScheduler{new(sync.WaitGroup), taskPool, workerPool, capacity, 0}

	//Build task pool and worker pool
	for i := 0; i < capacity; i++ {
		s.taskPool = append(s.taskPool, NewDEqueue())
		s.workerPool = append(s.workerPool, NewStealingWorker(i, s))
	}

	return s
}

/*
Creates a new stealer worker (i.e., a thread in the pool of workers)
*/
func NewStealingWorker(id int, parent *StealingScheduler) *stealingWorker {
	return &stealingWorker{id, parent, make(chan bool), false}
}

/*
PushTask inserts a new task into a queue of one of the threads in the pool. Each thread is given ~= same number of tasks
*/
func (scheduler *StealingScheduler) PushTask(task Runnable, taskArg interface{}) {
	scheduler.taskPool[scheduler.nextThread].PushBottom(NewTask(task, taskArg))
	scheduler.nextThread++
	if scheduler.nextThread == scheduler.capacity {
		scheduler.nextThread = 0
	}
}

/*
Run starts the run() method for each thread in the pool
*/
func (scheduler *StealingScheduler) Run() {
	scheduler.wg.Add(scheduler.capacity)
	for i := 0; i < scheduler.capacity; i++ {
		scheduler.workerPool[i].running = true
		scheduler.workerPool[i].run()
	}

}

/*
Run is the function being executed by a stealing worker.
*/
func (worker *stealingWorker) run() {
	go func() {
		defer worker.parent.wg.Done()
		var task *Task

		//Run until no more tasks left in any DEQs or kill signal recieved
	runner:
		for {
			select {
			case <-worker.killChan:
				//Break out immediately when kill signal recieved from parent
				break runner
			default:
				//First try to pop off workers own DEQ
				task = worker.parent.taskPool[worker.id].PopBottom()

				if task == nil {

					//If workers own DEQ empty, try to steal from another workers DEQ
					task = worker.stealTask()

					//If all DEQs are empty break out
					if task == nil {
						break runner
					}
				}

				//Execute task
				task.Func.execute(task.Arg)
			}
		}
		worker.running = false
	}()

}

/*
Helper Function which tries to steal task. Returns nil if all DEQs are empty
*/
func (worker *stealingWorker) stealTask() *Task {
	var task *Task
	indices := getRandomSequence(worker.parent.capacity)
	for _, i := range indices {
		if i != worker.id {
			task = worker.parent.taskPool[i].PopTop()
			if task != nil {
				return task
			}
		}
	}
	return nil
}

/*
Done sends signal to all workers in scheduler pool to stop executing.
Workers will exit gracefully as soon as they are able to.
This method must be followed with a call to Wait() and EmptyTaskQueue() if the scheduler
will be reused, otherwise unexpected behavior might occur.
*/
func (scheduler *StealingScheduler) Done() {
	for _, worker := range scheduler.workerPool {
		worker.kill()
	}
}

/*
Wait() is called when a goroutine wants to wait until all of the stealing threads in the pool have executed.
*/
func (scheduler *StealingScheduler) Wait() {
	scheduler.wg.Wait()
}

/*
Empties the task queue
*/

func (scheduler *StealingScheduler) EmptyTaskQueue() {
	for i := 0; i < scheduler.capacity; i++ {
		scheduler.taskPool[i] = NewDEqueue()
	}
}

/*
Helper function to kill worker if it is running
*/
func (w *stealingWorker) kill() {
	if w.running {
		go func() {
			w.killChan <- true
		}()
	}

}

/*
Helper function to generate random sequence. https://yourbasic.org/golang/shuffle-slice-array/
*/
func getRandomSequence(n int) []int {
	indices := []int{}
	for i := 0; i < n; i++ {
		indices = append(indices, i)
	}
	rand.Seed(time.Now().UnixNano())
	rand.Shuffle(len(indices), func(i, j int) { indices[i], indices[j] = indices[j], indices[i] })
	return indices
}
