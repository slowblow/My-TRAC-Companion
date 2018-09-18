#!/bin/bash


#export URL="kafka-connect-avor:28083"
export URL="$KAFKA_CONNECT_HOST:28083"
export MYSQL="$MYSQL_HOST"


  

echo ""
echo "Adding connector to $URL/connectors"

echo ""
echo ""

curl -X POST \
  -H "Content-Type: application/json" \
  --data "{ \"name\": \"cigo-jdbc-sink\", \"config\": { \"connector.class\": \"io.confluent.connect.jdbc.JdbcSinkConnector\", \"connection.url\": \"jdbc:mysql://$MYSQL_HOST:3306/sink_table?user=root&password=confluent\"} }" \
  $URL/connectors



  echo ""
  echo ""
