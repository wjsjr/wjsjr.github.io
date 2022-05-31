import matplotlib
matplotlib.use("agg")
import matplotlib.pyplot as plt
import sys
import subprocess
import os

def buildGraphs(speedups, sizes, numThreads):
    for i in range(len(sizes)):
        graph = plt.subplot()
        graph.set_xlabel("Num Threads")
        graph.set_ylabel("Speedup")
        plt.xticks(numThreads)
        graph.set_title(sizes[i])
        graph.scatter(numThreads, speedups[i])
        path = f"benchmark/graphs/{sizes[i]}.png"
        plt.savefig(path, facecolor='w')
        plt.clf()

def parseTestResults(testResults):
	speedups = []
	for row in testResults:
		spRow = []
		for j in range(len(row)):
			spRow.append(round(row[0] / row[j], 3))
		speedups.append(spRow)
	return speedups


def runTrial(threads, size, n):
	mode = "p"

	if threads == 1:
		mode = "s"

	outputhPath = "benchmark/" + size + "_" + str(threads) + "_" + str(n) + ".out"

	args = ["go", "run", "driver.go", size, outputhPath, "b"]

	if mode == "p":
		args.append(str(threads))

	job = subprocess.Popen(args, stdout=subprocess.PIPE)
	job.wait()
	time = float(job.stdout.read().decode("utf-8"))
	return time
	
def runTests(numThreads, sizes):
	resultsPerSize = []
	for size in sizes:
		resultsPerThreadCount = []
		for threadCount in numThreads:
			avgResults = 0.0
			for i in range(3):
				avgResults += runTrial(threadCount, size, i)
			avgResults /= 3
			print(str(size) + "_" + str(threadCount) + " : " + str(avgResults))
			resultsPerThreadCount.append(round(avgResults, 3))
		resultsPerSize.append(resultsPerThreadCount)
	return parseTestResults(resultsPerSize)
	

def main():	
	numThreads = [1, 2, 4, 6]
	sizes = ["small", "medium", "large"]

	if len(sys.argv) == 2:
		if sys.argv[1] == "small":
			sizes = ["small"]
		else:
			print("Invalid arg")
			exit()
	
	speedups = runTests(numThreads, sizes)
	buildGraphs(speedups, sizes, numThreads)


if __name__ == '__main__':
	main()
