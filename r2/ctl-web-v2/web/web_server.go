package web

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"gm-dev/ctl-web-v2/config"
	"net/http"
	"strconv"
)

type TmplData struct {
	Name string
	Age  int
}

// StartWebServer set the end point
func StartWebServer() {
	route := gin.Default()
	//route := gin.New()
	route.Use(gin.Logger())

	//
	route.Static("/assets", "./web/assets")
	route.LoadHTMLGlob("web/templates/**/*")

	route.GET("/", func(c *gin.Context) {
		c.JSON(http.StatusOK, gin.H{"data": "is working"})
	})

	route.GET("/page1.html", RenderHtmlPage1)
	route.GET("/page2.html", RenderHtmlPage2)
	route.GET("/ws/test", TestWs)

	route.Run(getWebServerAddress())
}

func RenderHtmlPage1(c *gin.Context) {
	fmt.Println("RenderHtmlPage1")
	//person struct :=  {
	//	Name: "John2",
	//	Age:  25,
	//}
	p1 := TmplData{"John2", 25}

	tmplMap := make(map[string]any)
	tmplMap["html."] = "page1"
	tmplMap["var2"] = "val2"
	tmplMap["var3"] = "val3"
	tmplMap["p1"] = p1
	ginMap := gin.H{}
	for k, v := range tmplMap {
		ginMap[k] = v
	}
	c.HTML(http.StatusOK, "pages/page1.tpl", ginMap)
}

func RenderHtmlPage2(c *gin.Context) {
	fmt.Println("RenderHtmlPage2")
	//person struct :=  {
	//	Name: "John2",
	//	Age:  25,
	//}
	p1 := TmplData{"John2", 25}

	tmplMap := make(map[string]any)
	tmplMap["html."] = "page1"
	tmplMap["var2"] = "val2"
	tmplMap["var3"] = "val3"
	tmplMap["p1"] = p1
	ginMap := gin.H{}
	for k, v := range tmplMap {
		ginMap[k] = v
	}
	c.HTML(http.StatusOK, "page2.tpl", ginMap)
}

func getWebServerAddress() string {
	return "0.0.0.0:" + strconv.Itoa(config.EnvVar.WebServerPort)
}
