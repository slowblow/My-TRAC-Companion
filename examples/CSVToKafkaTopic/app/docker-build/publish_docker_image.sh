#!/bin/bash

cp ../target/CSVToKafkaTopic-1.0-SNAPSHOT-jar-with-dependencies.jar .
docker build -t sparsitytechnologies/csvtokafkatopic:latest .
docker push sparsitytechnologies/csvtokafkatopic:latest
rm CSVToKafkaTopic-1.0-SNAPSHOT-jar-with-dependencies.jar

