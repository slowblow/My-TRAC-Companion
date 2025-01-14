#!/bin/bash

git clone https://github.com/My-TRAC/ConfigurationScripts.git

chmod +x ./ConfigurationScripts/*.sh

/opt/CSVToKafkaTopic/ConfigurationScripts/waitForKafkaConenct.sh
/opt/CSVToKafkaTopic/ConfigurationScripts/waitForMySQL.sh

/opt/CSVToKafkaTopic/ConfigurationScripts/setJDBCSourceConnector.sh -c "cigo-jdbc-source_CSV_TO_KAFKA"\
                                                -k $KAFKA_CONNECT_HOST\
                                                -m $MYSQL_HOST\
                                                -d $MYSQL_DATABASE\
                                                -u $MYSQL_USER\
                                                -p $MYSQL_PASSWORD\
                                                -i "mytrac_id"\
                                                -t "mytrac_last_modified"

#/opt/CSVToKafkaTopic/ConfigurationScripts/setElasticSearchConnector.sh

java -jar /opt/CSVToKafkaTopic/CSVToKafkaTopic-1.0-SNAPSHOT-jar-with-dependencies.jar -db $MYSQL_DATABASE -ip $MYSQL_HOST -sr $SCHEMA_REGISTRY_HOST
