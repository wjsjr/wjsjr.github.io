package main

import (
	"fmt"
	"os"
	"proj3/cracker"
	"proj3/jsonwriter"
	"strconv"
	"time"
)

const usage = "Usage: go run driver.go testSize outputPath searchStyle (numThreads)\n" +
	" testSize =  small, medium, or large \n" +
	" outputPath =  Relative write path from data/out/ \n" +
	" searchStyle =  p (Production: randomly shuffle search space to avoid pathologically bad inputs) | b (Benchmark: Standardize search space to reduce randomness for benchmarking)\n" +
	" numThreads =  if empty, run sequentially. Otherwise this is # of threads"

/*
Helper method to print usage statement and exit when
*/
func invalidArgs() {
	fmt.Println(usage)
	os.Exit(1)
}

/*
Prints out the total runtime of the driver.main() after executing
*/
func printRuntime(t time.Time) {
	fmt.Println(time.Now().Sub(t).Seconds())
}

func main() {
	//Start timer
	start := time.Now()
	defer printRuntime(start)

	//Read in args from user and parse them
	args := os.Args[1:]
	if len(args) < 3 || len(args) > 4 {
		invalidArgs()
	}
	size := args[0]
	outputPath := args[1]
	searchStyle := args[2]
	if searchStyle != "p" && searchStyle != "b" {
		invalidArgs()
	}
	mode := "s"
	numThreads := -1
	if len(args) == 4 {
		mode = "p"
		var e error
		numThreads, e = strconv.Atoi(args[3])
		if e != nil {
			invalidArgs()
		}
	}
	if size != "small" && size != "medium" && size != "large" {
		invalidArgs()
	}

	//Read in crack jobs from user
	jobs := jsonwriter.Read(size + ".in")

	var results []string
	var cracked string

	//Execute each job, keep results in slice
	for _, job := range jobs {
		if mode == "s" {
			cracked = cracker.CrackSequential(job.Hash, job.MaxLength)
		} else {
			cracked = cracker.CrackConcurrent(job.Hash, job.MaxLength, numThreads)
		}

		results = append(results, cracked)
	}

	//Write results slice
	jsonwriter.Write(results, outputPath)
}
