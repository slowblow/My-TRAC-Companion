#!/bin/bash

/opt/GTFSLoader/scripts/waitForKafkaConenct.sh
/opt/RatingModeler/scripts/waitForMySQL.sh
/opt/GTFSLoader/scripts/setJDBCConnector.sh
/opt/GTFSLoader/scripts/setElasticSearchConnector.sh
java -jar /opt/GTFSLoader/GTFSLoader-1.0-SNAPSHOT-jar-with-dependencies.jar -db connect_test -ip $MYSQL_HOST 
