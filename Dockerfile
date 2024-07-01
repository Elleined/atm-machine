FROM openjdk:17-alpine
MAINTAINER Elleined

ENV MYSQL_HOST=mysql_server
ENV MYSQL_PORT=3306
ENV MYSQL_DATABASE=ama_db
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=root
ENV PORT=8087

ADD ./target/*.jar atm-machine-api.jar
EXPOSE 8087
CMD ["java", "-jar", "atm-machine-api.jar"]