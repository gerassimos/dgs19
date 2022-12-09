GNMI_SERVER_SIM_BINARY=gnmi-srv-sim-app

build: build_gnmi_server_sim
	@echo "End building all"

clean: clean_gnmi_server_sim
	@echo "End cleaning all"

build_gnmi_server_sim:
	@echo "Building gnmi-server-sim binary..."
	cd gnmi-server-sim && go build -o app ./cmd
	@echo "Done!"

run_gnmi_server_sim:
	@echo "Starting gnmi-server-sim binary..."
	cd gnmi-server-sim && ./app

build_proto:
	@echo "Building gnmi-server-sim binary..."
	cd gnmi-common/proto &&  \
	protoc --go_out=. --go_opt=paths=source_relative \
        --go-grpc_out=. --go-grpc_opt=paths=source_relative \
         greet/greet.proto && \
	protoc --go_out=. --go_opt=paths=source_relative \
        --go-grpc_out=. --go-grpc_opt=paths=source_relative \
         calc/*.proto

clean_gnmi_server_sim:
	@echo "Deleting gnmi-server-sim binary..."
	cd gnmi-server-sim && rm -v app
	@echo "Done!"

clean_proto:
	@echo "Deleting gnmi-server-sim binary..."
	cd gnmi-common/proto/ && rm -v greet/*.go && rm -v calc/*.go
	@echo "Done!"

