package main

import (
	"fmt"
	"gm-dev/test-main/cmd/geometry"
)

func main() {
	fmt.Println("Starting main...")
	var p1 geometry.Point
	var c1 geometry.Circle
	var w1 geometry.Wheel
	w1.X = 8 //this not working if Circle and Point is not anonymous
	c1.X = 8

	p1.PrintFormatedVal()
	c1.PrintFormatedVal()
	w1.PrintFormatedVal()

	fmt.Printf("%#v\n", w1)
	fmt.Printf("%#v\n", c1)

}
