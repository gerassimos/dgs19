package sh

import (
	"bufio"
	"fmt"
	"os/exec"
)

func ExecStreamEx1() {
	cmd := exec.Command("ping", "in.gr")
	// some command output will be input into stderr
	// e.g.
	// cmd := exec.Command("../../bin/master_build")
	// stderr, err := cmd.StderrPipe()
	stdout, err := cmd.StdoutPipe()
	if err != nil {
		fmt.Println(err)
	}

	err = cmd.Start()
	fmt.Println("The command is running")
	if err != nil {
		fmt.Println(err)
	}

	// print the output of the subprocess
	scanner := bufio.NewScanner(stdout)
	for scanner.Scan() {
		m := scanner.Text()
		fmt.Println(m)
	}
	cmd.Wait()
}

func ExecStreamEx2() {
	cmd := exec.Command("ls", "-la", "/", "--color=always")
	// some command output will be input into stderr
	// e.g.
	// cmd := exec.Command("../../bin/master_build")
	stderr, err := cmd.StderrPipe()
	if err != nil {
		fmt.Println(err)
	}
	stdout, err := cmd.StdoutPipe()
	if err != nil {
		fmt.Println(err)
	}

	err = cmd.Start()
	fmt.Println("The command is running")
	if err != nil {
		fmt.Println(err)
	}

	// print the output of the subprocess
	scanner := bufio.NewScanner(stdout)
	go func() {
		for scanner.Scan() {
			m := scanner.Text()
			fmt.Println(m)
		}
	}()

	// print the output of the subprocess
	scannerErr := bufio.NewScanner(stderr)
	for scannerErr.Scan() {
		m := scannerErr.Text()
		fmt.Println("stderr: ", m)
	}
	cmd.Wait()
}
