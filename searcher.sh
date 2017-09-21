#!/usr/bin/env bash
mvn -DES_USER=$1 package
export BUILD_ID=dontKillMe
fuser -k -n tcp 9500
ES_USER=$1
nohup java "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005" -jar ./searchlogic/target/search-logic-0.0.1-SNAPSHOT.jar &