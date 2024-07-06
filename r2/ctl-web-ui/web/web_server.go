package web

import (
	"github.com/gin-gonic/gin"
	"gm-dev/ctl-web-ui/config"
	"net/http"
	"strconv"
)

type TmplData struct {
	Name string
	Age  int
}

// StartWebServer set the end point
func StartWebServer() {
	//route := gin.Default()
	route := gin.New()

	//
	route.Static("/assets", "./web/assets")
	route.LoadHTMLGlob("web/templates/*.gohtml")

	route.GET("/", func(c *gin.Context) {
		c.JSON(http.StatusOK, gin.H{"data": "is working"})
	})

	route.GET("/index.html", RenderTemplates)

	route.Run(getWebServerAddress())
}

func RenderTemplates(c *gin.Context) {
	//person struct :=  {
	//	Name: "John2",
	//	Age:  25,
	//}
	p1 := TmplData{"John2", 25}

	tmplMap := make(map[string]any)
	tmplMap["var1"] = "val1"
	tmplMap["var2"] = "val2"
	tmplMap["var3"] = "val3"
	tmplMap["p1"] = p1
	ginMap := gin.H{}
	for k, v := range tmplMap {
		ginMap[k] = v
	}
	c.HTML(http.StatusOK, "main.page.gohtml", ginMap)
}

func getWebServerAddress() string {
	return "0.0.0.0:" + strconv.Itoa(config.EnvVar.WebServerPort)
}
