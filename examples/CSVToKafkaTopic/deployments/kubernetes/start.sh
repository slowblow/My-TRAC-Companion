#!/bin/bash

echo "Deploying CSV TO KAFKA TOPICS module..."

kubectl apply -f mysql-csvtokafkatopic-deployment.yaml
sleep 60


kubectl apply -f csvtokafkatopic-deployment.yaml


