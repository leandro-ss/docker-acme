#!/bin/bash

mvn install

docker build . --tag docker-acme

docker run -it -p 8080:8080 docker-acme