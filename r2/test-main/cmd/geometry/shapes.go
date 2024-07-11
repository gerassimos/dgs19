package geometry

import "fmt"

type Point struct {
	X int
	Y int
}

type Circle struct {
	Point  // Center anonymous field
	Radius int
}

type Wheel struct {
	Circle // Circle anonymous field
	Spokes int
}

func (p Point) PrintFormatedVal() {
	fmt.Printf("%#v\n", p)
}
