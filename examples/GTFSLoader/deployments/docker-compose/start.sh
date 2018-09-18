#!/bin/bash


export path=`pwd`


cd $1
./start.sh 
eval $(docker-machine env cigo)                                                                                                       
cd $path 


docker-compose up -d
