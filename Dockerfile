FROM amazoncorretto:17.0.3-alpine
EXPOSE 8080
ARG JAR_FILE=/target/bank-api-*.jar
COPY ${JAR_FILE} /opt/bank-api.jar
CMD [ "java", "-jar",  "/opt/bank-api.jar"]