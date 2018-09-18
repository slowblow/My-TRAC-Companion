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
  --data "{ \"name\": \"cigo-jdbc-source_GTFSLoader\", \"config\": { \"connector.class\": \"io.confluent.connect.jdbc.JdbcSourceConnector\", \"tasks.max\": 1,\"connection.url\": \"jdbc:mysql://$MYSQL_HOST:3306/connect_test?user=root&password=confluent\", \"mode\": \"incrementing\", \"incrementing.column.name\": \"id\", \"timestamp.column.name\": \"modified\", \"topic.prefix\": \"cigo-jdbc-\", \"poll.interval.ms\": 1000 } }" \
  $URL/connectors

  echo ""
  echo ""
