#/bin/bash

kubectl apply -f mysql-deployment.yaml 
sleep 60

kubectl apply -f gtfsloader-deployment.yaml

