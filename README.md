# EircodesAPI

API Service to share Irish Eircodes inside.

Tecnologies:
  1. Netflix Zull
  2. Docker
  3. Spring Boot
  4. Redis

Genarate project:

Prerequisite:
  1. Java 8 (and Enviroment Variables)
  2. Maven

Installing, Step by Step:
  1. git clone git@github.com:pett4j/EircodesAPI.git
  Inside the EircodesAPI directory:
  2. mvn clean install
  3. mvn package docker:build 
  4. docker run -p 8080:8080 -t pett4j/eircodesapi
  5. docker run --name eircode-redis -d redis redis-server --appendonly yes

Install on docker hub:
  1. Change your dockerbuh id inside maven pom.xml
  2. docker push ${dockerhub_id_account}/eircodesapi
