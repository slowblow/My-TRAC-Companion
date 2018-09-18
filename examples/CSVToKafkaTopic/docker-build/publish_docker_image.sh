#!/bin/bash

cp ../app/target/CSVToKafkaTopic-1.0-SNAPSHOT-jar-with-dependencies.jar .
docker build -t sparsitytechnologies/csvtokafkatopic:latest .
docker push sparsitytechnologies/csvtokafkatopic:latest

