FROM 192.168.1.202/library/basejava
VOLUME /tmp
ADD ./target/search-logic-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9500 5005
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005", "-jar", "/app.jar"]
