#!/bin/bash

eval $(docker-machine env cigo)                                                                                                       
docker-compose stop
docker-compose rm

