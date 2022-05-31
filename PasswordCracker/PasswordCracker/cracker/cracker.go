package cracker

import (
	"proj3/cracktask"
	"proj3/password"
	"proj3/scheduler"
)

/*
Run Sequential Version of Password Cracker
*/
func CrackSequential(hashedPassword string, maxLength int) string {
	chars := password.GetValidChars()

	//For every prefix, search prefixSpace. Break out and return valid key if found.
	//Iterate backwards over char set so sequential behavior follows task ordering of concurrent
	//otherwise benchmarking isn't accurate (since task queue is FIFO)
	for i := len(chars) - 1; i >= 0; i-- {
		prefixResult := password.SearchPrefixSpace(rune(chars[i]), maxLength, hashedPassword)

		if prefixResult != "" {
			return prefixResult
		}
	}
	return ""

}

/*
Run Concurrent Version Version of Password Cracker
*/
func CrackConcurrent(hashedPassword string, maxLength int, numThreads int) string {
	var result string

	//Build Scheduler
	s := scheduler.NewStealingScheduler(numThreads)

	//Build Channels
	passwordChan := make(chan string)
	canaryChan := make(chan bool)

	//Generate list of cracktasks
	t := cracktask.BuildTasks(hashedPassword, maxLength, passwordChan)

	//Add Tasks to scheduler
	for _, task := range t {
		s.PushTask(scheduler.BuildRunnable(cracktask.ExecuteCrackTask), task)
	}

	//Launch Scheduler
	s.Run()

	//Launch Canary to alert main thread if scheduler finishes without producing result (i.e. key not found)
	go func() {
		s.Wait()
		canaryChan <- true
	}()

	//Listen for results / canary. Break out when signal recieved
listener:
	for {
		select {

		//If result recieved, store it
		case result = <-passwordChan:
			break listener

		//If canary recieved, store empty string (i.e. crack failed)
		case _ = <-canaryChan:
			result = ""
			break listener

		}
	}

	//Tell scheduler to shut down
	s.Done()

	return result
}
