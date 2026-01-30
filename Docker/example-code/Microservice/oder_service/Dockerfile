#base image: linux with java 21
FROM openjdk:21-rc-jdk-oracle

# copy jar from local into docker image.
COPY target/order_service-0.0.1-SNAPSHOT.jar /order_service-0.0.1-SNAPSHOT.jar

# command line to run jar
ENTRYPOINT ["java","-jar","/order_service-0.0.1-SNAPSHOT.jar"]