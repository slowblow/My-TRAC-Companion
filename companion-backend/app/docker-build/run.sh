#!/bin/bash

git clone https://github.com/My-TRAC/ConfigurationScripts.git
chmod +x ./ConfigurationScripts/*.sh


/root/ConfigurationScripts/waitForSchemaRegistry.sh
/root/ConfigurationScripts/waitForKafkaConenct.sh
/root/ConfigurationScripts/waitForMySQL.sh

# va sed -i 's+3306/connect_test?user=root&password=confluent+$MYSQL_PORT/$MYSQL_DATABASE?user=$MYSQL_USER\&password=$MYSQL_PASSWORD+g' /root/ConfigurationScripts/setJDBCConnector.sh
#sed -i 's+\\"table.blacklist\\":\\"\\"+\\"table.blacklist\\":\\"users-value\\"+g' setJDBCConnector.sh
#sed -i 's+\\"table.blacklist\\"+\\"validate.non.null\\": false,\\"table.types\\": \\"TABLE,VIEW\\", \\"table.blacklist\\"+g' /root/ConfigurationScripts/setJDBCConnector.sh
#sed -i 's+,\\"table.blacklist\\":\\"$3\\"+ +g' /root/ConfigurationScripts/setJDBCConnector.sh
#sed -i 's+blacklist+whitelist+g' /root/ConfigurationScripts/setJDBCConnector.sh

#sed -i 's+\\"incrementing.column.name\\": \\"id\\"+\\"incrementing.column.name\\": \\"$4\\"+g' /root/ConfigurationScripts/setJDBCConnector.sh
# va sed -i 's+\\"incrementing.column.name\\": \\"id\\"+\\"incrementing.column.name\\": \\"$3\\"+g' /root/ConfigurationScripts/setJDBCConnector.sh
# va sed -i 's+\\"table.blacklist\\":\\"$3\\"+\\"query\\":\\"$4\\"+g' /root/ConfigurationScripts/setJDBCConnector.sh

#los definidos como facility aparecen siempre porque se han definido en otro docker-compose
#/root/ConfigurationScripts/setJDBCConnector.sh cigo-jdbc-source_COMPANION_BACKEND_prova1 "prova1" "user_id" "SELECT user_id,latitude FROM user_points"
sleep 20
# va /root/ConfigurationScripts/setJDBCConnector.sh cigo-jdbc-source_COMPANION_BACKEND_prova2 "prova2" "id" "SELECT id,username FROM users"
# va /root/ConfigurationScripts/setJDBCConnector.sh cigo-jdbc-source_COMPANION_BACKEND_mobility_trace "mobility_trace" "user_id" "SELECT user_id,latitude AS trace_lat, longitude as trace_lon,provider AS trace_prov, time AS trace_time, accuracy AS trace_acc, speed AS trace_speed FROM user_points"

/root/scripts/setJDBCConnectorCompanion.sh cigo-jdbc-source_COMPANION_BACKEND_prova2 "prova2" "id" "SELECT id,username FROM users"
/root/scripts/setJDBCConnectorCompanion.sh cigo-jdbc-source_COMPANION_BACKEND_mobility_trace "mobility_trace" "user_id" "SELECT user_id,latitude AS trace_lat, longitude as trace_lon,provider AS trace_prov, time AS trace_time, accuracy AS trace_acc, speed AS trace_speed FROM user_points"

#/root/ConfigurationScripts/setJDBCConnector.sh cigo-jdbc-source_COMPANION_BACKEND_arnau "" "arnau" "user_id"


# FAILED /root/ConfigurationScripts/setJDBCConnector.sh cigo-jdbc-source_COMPANION_BACKEND_mobility_trace2 "mobility_trace2" "user_id" "SELECT user_id, latitude AS trace_lat, longitude as trace_lon, provider AS trace_prov, time AS trace_time, accuracy AS trace_acc, speed AS trace_speed FROM user_points;"
# WORKS /root/ConfigurationScripts/setJDBCConnector.sh cigo-jdbc-source_COMPANION_BACKEND_prova1 "prova1" "user_id" "SELECT user_id,latitude AS trace_lat FROM user_points"
# WORKS /root/ConfigurationScripts/setJDBCConnector.sh cigo-jdbc-source_COMPANION_BACKEND_prova1 "prova1" "user_id" "SELECT user_id,latitude AS trace_lat, longitude as trace_lon,provider AS trace_prov FROM user_points"
# WORKS /root/ConfigurationScripts/setJDBCConnector.sh cigo-jdbc-source_COMPANION_BACKEND_prova1 "prova1" "user_id" "SELECT user_id,latitude AS trace_lat, longitude as trace_lon,provider AS trace_prov, time AS trace_time FROM user_points"
# WORKS /root/ConfigurationScripts/setJDBCConnector.sh cigo-jdbc-source_COMPANION_BACKEND_mobility_trace "mobility_trace" "user_id" "SELECT user_id,latitude AS trace_lat, longitude as trace_lon,provider AS trace_prov, time AS trace_time, accuracy AS trace_acc,speed AS trace_speed FROM user_points"
# FAILED por coma final /root/ConfigurationScripts/setJDBCConnector.sh cigo-jdbc-source_COMPANION_BACKEND_prova1 "prova1" "user_id" "SELECT user_id,latitude AS trace_lat, longitude as trace_lon,provider AS trace_prov, time AS trace_time, accuracy AS trace_acc, speed AS trace_speed FROM user_points;"

#users,mobility_trace
/root/scripts/infinite.sh




    

