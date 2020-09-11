#!/bin/bash

PREVIOUS_DIR=$(pwd)
BASE_DIR=$(dirname $(realpath $BASH_SOURCE))

set -x

cd $BASE_DIR
source build-env.sh

for x in $(docker ps -a | grep -F $CONTAINER_PREFIX | cut -d " " -f 1); do
	docker rm -f $x
done
for x in $(docker images --format "{{.Repository}}:{{.Tag}} {{.ID}}" | grep -F $IMAGE_PREFIX | cut -d " " -f 2); do
	docker rmi $x
done

git pull
./build.sh

