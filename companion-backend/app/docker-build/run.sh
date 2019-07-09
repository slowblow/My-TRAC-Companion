#!/bin/bash

git clone https://github.com/My-TRAC/data-model.git
cd data-model/Resources/MYSQLInitDataModel/app
mvn assembly::assembly
cp target/MYSQLInitDataModel-1.0-SNAPSHOT-jar-with-dependencies.jar /root/MYSQLInitDataModel-1.0-SNAPSHOT-jar-with-dependencies.jar
cd /root


git clone https://github.com/My-TRAC/ConfigurationScripts.git
chmod +x ./ConfigurationScripts/*.sh

/root/ConfigurationScripts/waitForSchemaRegistry.sh
/root/ConfigurationScripts/waitForKafkaConenct.sh
/root/ConfigurationScripts/waitForMySQL.sh

sleep 60

java -cp /root/MYSQLInitDataModel-1.0-SNAPSHOT-jar-with-dependencies.jar MYSQLInitDataModel --username $MYSQL_USER --password $MYSQL_PASSWORD --database $MYSQL_DATABASE --topic-names activity,mobility_trace --schema-registry http://schema-registry:8081 --mysql $MYSQL_HOST':'$MYSQL_PORT


sleep 20

# sources 
# OLD WORKS Incr /root/scripts/setJDBCConnectorCompanionIncr.sh cigo-jdbc-source_COMPANION_BACKEND_prova2 "prova2" "id" "SELECT id,updated_at,username FROM users"
### incrementing+timestamp source(con el script no va, deduzco que el + de icrementing+timestamp casca la query por parametro)
#/root/scripts/setJDBCConnectorCompanionIncTim.sh cigo-jdbc-source_COMPANION_BACKEND_activity "activity" "mytrac_id" "mytrac_last_modified" "SELECT mytrac_id,mytrac_last_modified FROM activity_fakes" 
curl -X POST kafka-connect:28083/connectors -H 'Content-Type: application/json' -d '{"name": "connector_source_activity","config": {"connector.class": "io.confluent.connect.jdbc.JdbcSourceConnector","tasks.max": 1,"connection.url": "jdbc:mysql://'$MYSQL_HOST':'$MYSQL_PORT'/'$MYSQL_DATABASE'?user='$MYSQL_USER'&password='$MYSQL_PASSWORD'","mode": "timestamp+incrementing","incrementing.column.name": "mytrac_id","timestamp.column.name": "mytrac_last_modified","topic.prefix": "activity","poll.interval.ms": 1000,"validate.non.null":false,"timestamp.delay.interval.ms":1000,"query":"SELECT mytrac_id,mytrac_last_modified,activity_creation_date,IF(deleted_at is null, 1,0) AS valido,activity_id,activity_name,activity_lat,activity_lon,activity_type,activity_start,activity_end,activity_timezone FROM activity_fakes","transforms": "RenameField,createKey,extractLong", "transforms.RenameField.type": "org.apache.kafka.connect.transforms.ReplaceField$Value","transforms.RenameField.renames": "valido:mytrac_is_valid","transforms.createKey.type":"org.apache.kafka.connect.transforms.ValueToKey","transforms.createKey.fields":"mytrac_id","transforms.extractLong.type":"org.apache.kafka.connect.transforms.ExtractField$Key","transforms.extractLong.field":"mytrac_id"}}'
## WORKS curl -X POST kafka-connect:28083/connectors -H 'Content-Type: application/json' -d '{"name": "connector_source_mobility_trace","config": {"connector.class": "io.confluent.connect.jdbc.JdbcSourceConnector","tasks.max": 1,"connection.url": "jdbc:mysql://'$MYSQL_HOST':'$MYSQL_PORT'/'$MYSQL_DATABASE'?user='$MYSQL_USER'&password='$MYSQL_PASSWORD'","mode": "timestamp+incrementing","incrementing.column.name": "id","timestamp.column.name": "updated_at","topic.prefix": "mobility_trace","poll.interval.ms": 1000,"validate.non.null":false,"timestamp.delay.interval.ms":1000,"query":"SELECT CAST(id as UNSIGNED INTEGER) AS id,updated_at,IF(deleted_at is null, 1,0) AS mytrac_is_valid,CAST(user_id as CHAR) AS user_id,latitude AS trace_lat, longitude AS trace_lon, provider AS trace_prov, trace_time, accuracy AS trace_acc, speed AS trace_speed  FROM user_points","transforms": "RenameField", "transforms.RenameField.type": "org.apache.kafka.connect.transforms.ReplaceField$Value","transforms.RenameField.renames": "id:mytrac_id,updated_at:mytrac_last_modified"}}'
curl -X POST kafka-connect:28083/connectors -H 'Content-Type: application/json' -d '{"name": "connector_source_mobility_trace","config": {"connector.class": "io.confluent.connect.jdbc.JdbcSourceConnector","tasks.max": 1,"connection.url": "jdbc:mysql://'$MYSQL_HOST':'$MYSQL_PORT'/'$MYSQL_DATABASE'?user='$MYSQL_USER'&password='$MYSQL_PASSWORD'","mode": "timestamp+incrementing","incrementing.column.name": "id","timestamp.column.name": "updated_at","topic.prefix": "mobility_trace","poll.interval.ms": 1000,"validate.non.null":false,"timestamp.delay.interval.ms":1000,"query":"SELECT CAST(id as UNSIGNED INTEGER) AS id,updated_at,IF(deleted_at is null, 1,0) AS mytrac_is_valid,CAST(user_id as CHAR) AS user_id,latitude AS trace_lat, longitude AS trace_lon, provider AS trace_prov, trace_time, accuracy AS trace_acc, speed AS trace_speed  FROM user_points","transforms": "RenameField,createKey,extractLong", "transforms.RenameField.type": "org.apache.kafka.connect.transforms.ReplaceField$Value","transforms.RenameField.renames": "id:mytrac_id,updated_at:mytrac_last_modified","transforms.createKey.type":"org.apache.kafka.connect.transforms.ValueToKey","transforms.createKey.fields":"mytrac_id","transforms.extractLong.type":"org.apache.kafka.connect.transforms.ExtractField$Key","transforms.extractLong.field":"mytrac_id"}}'


sleep 20

# sinks
# OLD WORKS Incr /root/scripts/setJDBCSinkConnector.sh "prova2-sink" "prova2"
### incrementing+timestamp sink
/root/scripts/setJDBCSinkConnector.sh "connector_sink_activity" "activity"
/root/scripts/setJDBCSinkConnector.sh "connector_sink_mobility_trace" "mobility_trace"
#curl -X POST kafka-connect:28083/connectors -H 'Content-Type: application/json' -d '{"name":"connector_sink_activity","config": {"connector.class":"io.confluent.connect.jdbc.JdbcSinkConnector","tasks.max":"1","topics":"activity","connection.url": "jdbc:mysql://'$MYSQL_HOST':'$MYSQL_PORT'/'$MYSQL_DATABASE'?user='$MYSQL_USER'&password='$MYSQL_PASSWORD'", "key.converter":"io.confluent.connect.avro.AvroConverter","key.converter.schema.registry.url":"http://schema-registry:8081","value.converter":"io.confluent.connect.avro.AvroConverter","value.converter.schema.registry.url":"http://schema-registry:8081","insert.mode":"upsert","batch.size":"0","auto.create":"false","pk.mode":"record_value","pk.fields":"mytrac_id"}}'
#curl -X POST kafka-connect:28083/connectors -H 'Content-Type: application/json' -d '{"name":"connector_sink_mobility_trace","config": {"connector.class":"io.confluent.connect.jdbc.JdbcSinkConnector","tasks.max":"1","topics":"mobility_trace","connection.url": "jdbc:mysql://'$MYSQL_HOST':'$MYSQL_PORT'/'$MYSQL_DATABASE'?user='$MYSQL_USER'&password='$MYSQL_PASSWORD'", "key.converter":"io.confluent.connect.avro.AvroConverter","key.converter.schema.registry.url":"http://schema-registry:8081","value.converter":"io.confluent.connect.avro.AvroConverter","value.converter.schema.registry.url":"http://schema-registry:8081","insert.mode":"upsert","batch.size":"0","auto.create":"false","pk.mode":"record_value","pk.fields":"mytrac_id"}}'


/root/scripts/infinite.sh




    

