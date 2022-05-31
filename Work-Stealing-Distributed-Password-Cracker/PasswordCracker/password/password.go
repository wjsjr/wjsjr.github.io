package password

import (
	"crypto/md5"
	"encoding/hex"
	"math/rand"
	"time"
)

var SearchStyle = ""

//Constant which holds all valid password characters
const ValidChars = " !#$%&()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[]^_`abcdefghijklmnopqrstuvwxyz{|}~"

/*
	Apply md5 hashs function to given input string and return hashed output
*/

func SetSearchStyle(s string) {
	SearchStyle = s
}

//Get list of valid chars. Static list if we are benchmarking, otherwise shuffle it
func GetValidChars() string {
	inRune := []rune(ValidChars)

	//In production mode Shuffle ValidChars every time they are used to avoid pathological bad inputs
	if SearchStyle == "p" {
		//https://golangbyexample.com/shuffle-string-golang/
		rand.Seed(time.Now().Unix())
		rand.Shuffle(len(inRune), func(i, j int) {
			inRune[i], inRune[j] = inRune[j], inRune[i]
		})
	}

	return string(inRune)
}

func GenerateHash(rawPassword string) string {
	rawHash := md5.Sum([]byte(rawPassword))
	return hex.EncodeToString(rawHash[:])
}

/*
Given a prefix, max length and targetHash, search all possible passwords <= maxLength, starting with prefix.
If password founds that hashes to correct target, return that key. Else, return empty string.
*/
func SearchPrefixSpace(prefix rune, maxLength int, targetHash string) string {

	//Check prefix by itself
	if GenerateHash(string(prefix)) == targetHash {
		return string(prefix)
	}

	//Iterate over all possible bodies in range 1, maxLength - 1
	for i := 1; i <= maxLength-1; i++ {

		//Build "permuter" w/ length = i
		nextPermutation := permuter(i, GetValidChars())

		var body string
		for {
			//Get Next Permutation
			body = nextPermutation()

			//If permutation empty, break out
			if len(body) == 0 {
				break
			}

			//Test Guess
			if testPassword(targetHash, string(prefix)+body) {
				return string(prefix) + body
			}
		}

	}

	//Iterate until permuter returns empty string, test prefix + permutation
	return ""

}

/*
Helper function which tests whether a guess hashes to correct target
*/
func testPassword(hash string, guess string) bool {
	return GenerateHash(guess) == hash
}

/*
Helper function which builds "permuter", stores all possible permutations of given set of chars == a given length

Source: peterSO stackoverflow.https://stackoverflow.com/questions/22739085/generate-all-possible-n-character-passwords
*/
func permuter(n int, c string) func() string {
	r := []rune(c)
	p := make([]rune, n)
	x := make([]int, len(p))
	return func() string {
		p := p[:len(x)]
		for i, xi := range x {
			p[i] = r[xi]
		}
		for i := len(x) - 1; i >= 0; i-- {
			x[i]++
			if x[i] < len(r) {
				break
			}
			x[i] = 0
			if i <= 0 {
				x = x[0:0]
				break
			}
		}
		return string(p)
	}
}
