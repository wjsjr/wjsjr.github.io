Project Type: Brute Force Password Cracker Using Work-Stealing Thread Scheduler

This was my final capstone project for Paralell Programming course I took at UChicago. I built a library for concurrent brute force password cracking in Golang. My system is configured to break passwords that are hashed using the MD5 hash protocol but it could easily be repurposed for other applications. I also built a work-stealing thread scheduler which is designed to be used by other concurrent Golang applications.

How to run:

Benchmark tests
=> python3 benchmark/benchmark.py (small)
    - Small flag runs tests using only small dataset, omitting this flag runs full benchmark tests

Run Manually

"Usage: go run driver.go testSize outputPath searchStyle (numThreads)\n" +
	" testSize =  small, medium, or large \n" +
	" outputPath =  Relative write path from data/out/ \n" +
	" searchStyle =  p (Production: randomly shuffle search space to avoid pathologically bad inputs) | b (Benchmark: Standardize search space to reduce randomness for benchmarking)\n" +
	" numThreads =  if empty, run sequentially. Otherwise this is # of threads"


Performance Analysis:
Please see attached Performance Analysis dir
