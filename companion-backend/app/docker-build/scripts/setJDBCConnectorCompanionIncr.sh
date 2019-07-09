#!/bin/bash


#export URL="kafka-connect-avor:28083"
export URL="$KAFKA_CONNECT_HOST:28083"
export MYSQL="$MYSQL_HOST"


echo ""
echo "Adding JDBC SOURCE connector to $URL/connectors"

echo ""
echo ""

#if [ ! -z "$1" ]                                                                                                                       
#then
#    echo "Overwritting JDBC_SOURCE_CONNECTOR_NAME to $1"
#    JDBC_SOURCE_CONNECTOR_NAME=$1
#fi

CONNECTOR="{ 
\"name\": \"$1\", 
\"config\": { 
\"connector.class\": \"io.confluent.connect.jdbc.JdbcSourceConnector\", 
\"tasks.max\": 1,
\"connection.url\": \"jdbc:mysql://$MYSQL_HOST:$MYSQL_PORT/$MYSQL_DATABASE?user=$MYSQL_USER&password=$MYSQL_PASSWORD\", 
\"mode\": \"incrementing\", 
\"incrementing.column.name\": \"$3\",
\"topic.prefix\": \"$2\", 
\"poll.interval.ms\": 1000,
\"validate.non.null\":false,
\"query\":\"$4\" } 
}"

#\"timestamp.column.name\":\"$4\",
#\"timestamp.delay.interval.ms\":1000,



echo $CONNECTOR | curl -X POST \
  -H "Content-Type: application/json" \
  --data @- \
  $URL/connectors

  echo ""
  echo ""
