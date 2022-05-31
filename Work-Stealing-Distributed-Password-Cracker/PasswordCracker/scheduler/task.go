package scheduler

//Runable struct holds execute method which runs a single function using given args
type Runnable struct {
	execute func(arg interface{})
}

/*
Wrap func into runnable to pass into Task
*/
func BuildRunnable(f func(arg interface{})) Runnable {
	return Runnable{f}
}

/*
Task for scheduler to allocate. Holds function which
a worker thread will execute and args to pass into that func
*/
type Task struct {
	Func Runnable
	Arg  interface{}
}

/*
Build new task from func and args
*/
func NewTask(Func Runnable, Arg interface{}) *Task {
	return &Task{Func, Arg}
}
