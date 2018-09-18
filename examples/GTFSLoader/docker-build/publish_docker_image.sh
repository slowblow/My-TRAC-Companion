#!/bin/bash

cp ../app/target/GTFSLoader-1.0-SNAPSHOT-jar-with-dependencies.jar .
docker build -t sparsitytechnologies/gtfsloader:latest .
docker push sparsitytechnologies/gtfsloader:latest

