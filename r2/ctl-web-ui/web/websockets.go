package web

import (
	"encoding/json"
	"github.com/gin-gonic/gin"
	"github.com/gorilla/websocket"
	"gm-dev/ctl-web-ui/dto"
	"log"
	"time"
)

var upgrader = websocket.Upgrader{
	//check origin will check the cross region source (note : please not using in production)
	//CheckOrigin: func(r *http.Request) bool {
	//	return true
	//},
	ReadBufferSize:  1024,
	WriteBufferSize: 1024,
}

func TestWs(c *gin.Context) {
	//upgrade get request to websocket protocol
	ws, err := upgrader.Upgrade(c.Writer, c.Request, nil)
	if err != nil {
		log.Println("Error upgrading connection:", err)
		c.JSON(500, gin.H{"error": err.Error()})
		return
	}
	defer ws.Close()
	//serverTime := time.Now()
	for {
		testDto := dto.TestDTO{
			Target:   "Test",
			DataList: []string{"Test1", "Test2"},
			//ServerTime: serverTime,

		}
		// Convert the response object to JSON
		jsonResponseMsg, err := json.Marshal(testDto)
		if err != nil {
			log.Println("Error marshaling JSON:", err)
			continue
		}

		err = ws.WriteMessage(websocket.TextMessage, jsonResponseMsg)
		if err != nil {
			log.Println("Error writing message to webSocket:", err)
			break
		}
		time.Sleep(time.Second)
	}

}
