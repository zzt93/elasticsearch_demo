FROM 192.168.1.202/library/basejava
VOLUME /tmp
ADD ./target/searcher-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9500 5005
ENTRYPOINT ["java","-javaagent:/data/pp-agent/pinpoint-bootstrap-1.6.0.jar","-Dpinpoint.agentId=searcher","-Dpinpoint.applicationName=searcher","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005", "-jar", "/app.jar"]
