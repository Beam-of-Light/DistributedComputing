package main
import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

const (
	NumRows    = 10
	NumColumns = 10
	Bees       = 3
)

var wg sync.WaitGroup
var mutex sync.Mutex
var isBearFound bool
var rowCounter int = 0

func main() {
	forest := createForest(NumRows, NumColumns)
	printForest(forest)
	fmt.Print("...\n\n")


	wg.Add(Bees)
	for i := 0; i < Bees; i++ {
		go workBees(forest, i)
	}
	wg.Wait()
}



func workBees(forest [][]bool, id int) {
	defer wg.Done()
	defer fmt.Printf("Bee patrol %d: finished\n", id)

	for {
		mutex.Lock()
		if isBearFound {
			mutex.Unlock()
			return
		}
		currentRow := rowCounter
		rowCounter++
		mutex.Unlock()

		if currentRow >= NumRows { return }
		fmt.Printf("Bee patrol %d: checking %d row\n", id, currentRow)

		for i := range forest[currentRow] {
			time.Sleep(time.Millisecond * 100)
			if forest[currentRow][i] {
				mutex.Lock()
				isBearFound = true
				mutex.Unlock()

				fmt.Printf("Bee patrol %d: found the bear and sent him to China\n", id)
				return
			}
		}
		time.Sleep(time.Millisecond * 500)
	}
}



func createForest(rows, columns int) [][]bool {
	forest := make([][]bool, rows)
	for i := range forest {
		forest[i] = make([]bool, columns)
	}
	hideBear(forest)
	return forest
}

func hideBear(forest [][]bool) {
	rows := len(forest)
	columns := len(forest[0])

	rand.Seed(time.Now().UnixNano())
	forest[rand.Intn(rows)][rand.Intn(columns)] = true
}

func printForest(forest [][]bool) {
	for i := range forest {
		for j := range forest[i] {
			if forest[i][j] {
				fmt.Print("B")
			} else {
				fmt.Print("_")
			}
		}
		fmt.Println()
	}
}