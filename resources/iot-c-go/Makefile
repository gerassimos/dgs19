include iot-c-go.properties
GNMI_SERVER_SIM_BINARY=gnmi-srv-sim-app

build: build_gnmi_server_sim build_gnmi-client-worker
	@echo "End building all"

build_gnmi_server_sim:
	@echo "Building gnmi-server-sim binary..."
	cd gnmi-server-sim && go build -o app ./cmd
	@echo "Done!"

build_gnmi-client-worker:
	@echo "Building gnmi-client-worker binary..."
	cd gnmi-client-worker && go build -o app ./cmd
	@echo "Done!"

build_docker:
	@echo "Building Docker images..."
	export TAG=latest && docker compose build && \
	echo "iot_c_go_version ${iot_c_go_version}" && export TAG=${iot_c_go_version} && echo "TAG ${TAG}" && \
	docker compose build

docker_push:
	@echo "Pushing Docker images..."
	export TAG=latest docker compose push && \
	echo "iot_c_go_version ${iot_c_go_version}" && export TAG=${iot_c_go_version} && echo "TAG ${TAG}" && \
	docker compose push

build_proto:
	@echo "Building gnmi-common proto..."
	cd gnmi-common/proto &&  \
	protoc --go_out=. --go_opt=paths=source_relative \
        --go-grpc_out=. --go-grpc_opt=paths=source_relative \
         greet/greet.proto && \
	protoc --go_out=. --go_opt=paths=source_relative \
        --go-grpc_out=. --go-grpc_opt=paths=source_relative \
         calc/*.proto

clean: clean_gnmi_server_sim clean_gnmi-client-worker
	@echo "End cleaning all"

clean_gnmi_server_sim:
	@echo "Deleting gnmi-server-sim binary..."
	cd gnmi-server-sim && rm -v app
	@echo "Done!"

clean_gnmi-client-worker:
	@echo "Deleting gnmi-client-worker binary..."
	cd gnmi-client-worker && rm -v app
	@echo "Done!"

clean_proto:
	@echo "Deleting gnmi-server-sim binary..."
	cd gnmi-common/proto/ && rm -v greet/*.go && rm -v calc/*.go
	@echo "Done!"

run_gnmi_server_sim:
	@echo "Starting gnmi-server-sim binary..."
	cd gnmi-server-sim && ./app