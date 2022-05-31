package jsonwriter

import (
	"encoding/json"
	"fmt"
	"io/ioutil"
	"os"
)

/*
Struct used to read jobs as JSON in from input file
*/
type InputJob struct {
	Hash      string
	MaxLength int
}

/*
Read in input JSON file and store as slice of InputJobs
*/
func Read(input string) []InputJob {
	path := "./data/in/" + input
	effectsFile, err := os.Open(path)
	if err != nil {
		fmt.Println("INVALID INPUT PATH!")
		os.Exit(1)
	}
	reader := json.NewDecoder(effectsFile)

	jobs := []InputJob{}

	for reader.More() {
		var input InputJob
		if err := reader.Decode(&input); err != nil {
			os.Exit(0)
		}
		jobs = append(jobs, input)
	}
	return jobs
}

/*
Write slice of results to output file
*/
func Write(results []string, writePath string) {
	path := "./data/out/" + writePath
	file, _ := json.Marshal(results)
	ioutil.WriteFile(path, file, 0644)

}
