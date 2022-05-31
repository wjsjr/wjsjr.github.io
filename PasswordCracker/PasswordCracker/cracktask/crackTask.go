package cracktask

import (
	"proj3/password"
)

/*
Struct to hold args for crack task
*/
type CrackTaskArgs struct {
	prefix       rune
	target       string
	maxLength    int
	passwordChan chan<- string
}

/*
Construct task
*/
func buildTask(prefix rune, target string, maxLength int, passwordChan chan<- string) *CrackTaskArgs {
	return &CrackTaskArgs{prefix, target, maxLength, passwordChan}
}

/*
Given hashedPassword and max length, construct slice of tasks
*/
func BuildTasks(hashedPassword string, maxLength int, passwordChan chan<- string) []*CrackTaskArgs {
	taskPayloads := []*CrackTaskArgs{}
	chars := password.GetValidChars()
	for _, c := range chars {
		taskPayloads = append(taskPayloads, buildTask(c, hashedPassword, maxLength, passwordChan))
	}
	return taskPayloads
}

/*
Handle a given crack task
*/
func ExecuteCrackTask(t interface{}) {
	//Cast from generic interface to concrete CrackTaskArgs
	task := t.(*CrackTaskArgs)

	//Execute task
	result := password.SearchPrefixSpace(task.prefix, task.maxLength, task.target)

	if result != "" {
		task.passwordChan <- result
	}
}
