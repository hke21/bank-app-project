FROM adoptopenjdk/openjdk11:jre-11.0.9_11.1-alpine
MAINTAINER hakob
COPY target/SmsNotificationService-0.0.1-SNAPSHOT.jar SmsNotificationService-0.0.1-SNAPSHOT.jar
#ENTRYPOINT ["java","-jar", "-Dspring.datasource.username=postgres", "-Dspring.datasource.url=jdbc:postgresql://172.17.0.2:5432/postgres", "-Dspring.datasource.password=pass", "/TodoStore-0.0.1-SNAPSHOT.jar"]
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=kubernetes", "SmsNotificationService-0.0.1-SNAPSHOT.jar"]