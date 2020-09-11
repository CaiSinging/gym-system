#!/bin/bash

PREVIOUS_DIR=$(pwd)
BASE_DIR=$(dirname $(realpath $BASH_SOURCE))

set -x

cd $BASE_DIR
source build-env.sh
build_serial=$(date "+%s")
NETWORK_NAME=gym-app-net

docker network ls | grep -Fq $NETWORK_NAME
if [[ $? -ne 0 ]]; then
	docker network create --subnet=192.168.65.0/24 $NETWORK_NAME
fi

echo "Build with serial: $build_serial"
docker build -t $IMAGE_PREFIX:$build_serial .
docker run -d \
	--name $CONTAINER_PREFIX-$build_serial \
	--network $NETWORK_NAME \
	--ip 192.168.65.5 \
	$IMAGE_PREFIX:$build_serial

