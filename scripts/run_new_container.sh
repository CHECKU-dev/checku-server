#!/bin/bash

CURRENT_PORT=$(cat $HOME/service_url.inc | grep -Po '[0-9]+' | tail -1)
TARGET_PORT=0

echo "> Current port of running container is ${CURRENT_PORT}."

if [ ${CURRENT_PORT} -eq 8081 ]; then
  TARGET_PORT=8082
elif [ ${CURRENT_PORT} -eq 8082 ]; then
  TARGET_PORT=8081
else
  echo "> No WAS is connected to nginx"
fi

echo "[checku] 이미지 가져오기"
docker image pull yoon11/checku

echo "[checku] 이미지를 통해 Container Run "
docker run --name checku${TARGET_PORT} -d -p ${TARGET_PORT}:8080 --network redis-net yoon11/checku