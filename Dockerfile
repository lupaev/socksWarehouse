FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=target/*.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]



#./mvnw package && java -jar target/app.jar
#docker build -t socks .
#docker run -d -p8080:8080 socks:latest

