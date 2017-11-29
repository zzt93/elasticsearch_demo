FROM 192.168.1.202/common/basejava
VOLUME /tmp
ADD ./searchlogic/target/searcher-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9500 5005
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005", "-jar", "/app.jar"]
