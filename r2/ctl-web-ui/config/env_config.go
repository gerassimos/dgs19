package config

import (
	"github.com/joho/godotenv"
	// "fmt"
	"os"
	"strconv"
)

// EnvVar reference to the struct with env variables
var EnvVar *Config

// Config env variables are set here
type Config struct {
	WebServerPort int
}

// init is invoked before main()
func init() {
	// loads values from .env into the system
	if err := godotenv.Load(); err != nil {
		// 	    fmt.Println prints the error message in random places in production
		//      logger could be used instead
		// 		fmt.Println("No .env file found")
	}
	EnvVar = createConfig()
}

// New returns a new Config struct
func createConfig() *Config {
	return &Config{
		WebServerPort: getEnvAsInt("WEB_SERVER_PORT", 8104),
	}
}

// Simple helper function to read an environment or return a default value
func getEnv(key string, defaultVal string) string {
	if value, exists := os.LookupEnv(key); exists {
		return value
	}

	return defaultVal
}

// Simple helper function to read an environment variable into integer or return a default value
func getEnvAsInt(name string, defaultVal int) int {
	valueStr := getEnv(name, "")
	if value, err := strconv.Atoi(valueStr); err == nil {
		return value
	}

	return defaultVal
}

//
//func getEnvAsStrArray(name string, defaultVal string) []string {
//	valueStr := getEnv(name, defaultVal)
//	return strings.Split(valueStr, ",")
//}
//
//// Helper to read an environment variable into a bool or return default value
//func getEnvAsBool(name string, defaultVal bool) bool {
//	valStr := getEnv(name, "")
//	if val, err := strconv.ParseBool(valStr); err == nil {
//		return val
//	}
//
//	return defaultVal
//}
//
//// Helper to read an environment variable into a string slice or return default value
//func getEnvAsSlice(name string, defaultVal []string, sep string) []string {
//	valStr := getEnv(name, "")
//
//	if valStr == "" {
//		return defaultVal
//	}
//
//	val := strings.Split(valStr, sep)
//
//	return val
//}
