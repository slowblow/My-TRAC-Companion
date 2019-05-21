#!/bin/bash

echo "Deploying CSV TO KAFKA TOPICS module..."

kubectl apply -f mysql-companion-backend-deployment.yaml
sleep 60


#kubectl apply -f tracking-container-deployment.yaml


