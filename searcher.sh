#!/usr/bin/env bash
export BUILD_ID=dontKillMe
fuser -k -n tcp 9500
nohup java "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005" -jar ./searchlogic/target/search-logic-0.0.1-SNAPSHOT.jar &