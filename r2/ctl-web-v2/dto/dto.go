package dto

type TestDTO struct {
	Target   string   `json:"target"`
	DataList []string `json:"data"`
	//ServerTime time.Time `json:"time"`
}
