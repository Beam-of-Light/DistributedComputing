package main

import (
	"fmt"
	"sync"
	"time"
)

const (
	PotCapacity = 10
	Bees = 3
)

var potChannel = make(chan bool, 1)
var mutex sync.Mutex
var potValue = 0


func main() {
	for i := 0; i < Bees; i++ {
		go workBees(i)
	}
	go eatBear()

	for {}
}


func workBees(id int) {
	for {
		// Working
		time.Sleep(time.Millisecond * 100)

		mutex.Lock()
		potValue++
		fmt.Println("Bee ", id, ": added honey to pot - ", potValue)
		if potValue == PotCapacity {
			potChannel <- true

			for potValue != 0 {}
		}
		mutex.Unlock()

	}
}

func eatBear() {
	for {
		select {
		case <- potChannel:
			// Eating
			time.Sleep(time.Millisecond * 300)

			fmt.Println("Bear: ate honey")
			potValue = 0
		}
	}
}