FROM amazoncorretto:17.0.3-alpine
EXPOSE 8080
ARG JAR_FILE=target/bank-api-1.0.0.jar
COPY ${JAR_FILE} .
CMD [ "java", "-jar",  "/bank-api-1.0.0.jar"]