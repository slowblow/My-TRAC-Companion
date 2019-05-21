#!/bin/bash

source <(sed 's/ = /=/g' mytrac/app.conf)
source <(sed 's/ = /=/g' mytrac/local.conf)


export MYSQL_ROOT_PASSWORD=$mysqlpass
export MYSQL_DATABASE=$mysqldb
export MYSQL_PORT=$mysqlport

export MONGO_INITDB_ROOT_USERNAME=`echo $mongourl | sed -e 's$mongodb://\(.*\)[:].*[@].*[:][0-9]\+$\1$'`
export MONGO_INITDB_ROOT_PASSWORD=`echo $mongourl | sed -e 's$mongodb://.*[:]\(.*\)[@].*[:][0-9]\+$\1$'`
export MONGO_PORT=`echo $mongourl | sed -e 's$mongodb://.*[:].*[@].*[:]\([0-9]\+\)$\1$'`

export TRACKING_PORT="8604"
export TRIP_PORT="8601"
export POLLS_PORT="8607"
export ACTIVITY_PORT="8602"
export REGISTER_PORT="8603"
export LOGIN_PORT="8600"
export FIREBASE_PORT="7072"
export WEBAPP_PORT="8606"

export BEEGO_RUNMODE="dev"

export DOCKER_COMPOSE_PATH=`pwd`


if [[ "$OSTYPE" == *"darwin"* ]]
then
eval $(docker-machine env cigo)                                                                                                       
fi

docker-compose up -d
