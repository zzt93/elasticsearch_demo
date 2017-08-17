export BUILD_ID=dontKillMe
fuser -k -n tcp 9500
nohup java -jar ./searchlogic/target/search-logic-0.0.1-SNAPSHOT.jar &