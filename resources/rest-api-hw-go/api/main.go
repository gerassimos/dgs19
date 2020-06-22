package api

import (
	"net/http"
	"github.com/gin-gonic/gin"
)

// StartWebServer set the end point 
func StartWebServer() {
	r := gin.Default()

	r.GET("/", func(c *gin.Context) {
		c.JSON(http.StatusOK, gin.H{"data": "hello world"})
	})

	r.Run()
}
