FROM openjdk:17-alpine
MAINTAINER Elleined

ENV MYSQL_HOST=ama_mysql_server
ENV MYSQL_PORT=3306
ENV MYSQL_DATABASE=ama_db
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=root

ADD ./target/*.jar atm-machine-api.jar
EXPOSE 8082
CMD ["java", "-jar", "atm-machine-api.jar"]