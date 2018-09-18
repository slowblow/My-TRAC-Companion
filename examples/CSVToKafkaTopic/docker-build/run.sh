#!/bin/bash

/opt/CSVToKafkaTopic/scripts/waitForKafkaConenct.sh
/opt/CSVToKafkaTopic/scripts/waitForMySQL.sh
/opt/CSVToKafkaTopic/scripts/setJDBCConnector.sh
#/opt/CSVToKafkaTopic/scripts/setJDBCSinkConnector.sh
#/opt/CSVToKafkaTopic/scripts/setElasticSearchConnector.sh
java -jar /opt/CSVToKafkaTopic/CSVToKafkaTopic-1.0-SNAPSHOT-jar-with-dependencies.jar -db $MYSQL_DATABASE -ip $MYSQL_HOST
