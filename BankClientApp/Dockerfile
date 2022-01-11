FROM adoptopenjdk/openjdk11:jre-11.0.9_11.1-alpine
MAINTAINER hakob
COPY target/BankClientApp-0.0.1-SNAPSHOT.jar BankClientApp-0.0.1-SNAPSHOT.jar
#ENTRYPOINT ["java","-jar", "-Dspring.datasource.username=postgres", "-Dspring.datasource.url=jdbc:postgresql://172.17.0.2:5432/postgres", "-Dspring.datasource.password=pass", "/TodoStore-0.0.1-SNAPSHOT.jar"]
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=kubernetes", "BankClientApp-0.0.1-SNAPSHOT.jar"]
RUN apk --no-cache add curl
ENV JAEGER_SERVICE_NAME=customer\
    JAEGER_ENDPOINT=http://jaeger-collector.istio-system.svc:14268/api/traces\
    JAEGER_PROPAGATION=b3\
    JAEGER_SAMPLER_TYPE=const\
    JAEGER_SAMPLER_PARAM=1