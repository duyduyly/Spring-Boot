#base image: linux with java 21
FROM openjdk:21-rc-jdk-oracle

# copy jar from local into docker image.
COPY target/springtboot_with_mysql-0.0.1-SNAPSHOT.jar /springtboot_with_mysql-0.0.1-SNAPSHOT.jar

# command line to run jar
ENTRYPOINT ["java","-jar","/springtboot_with_mysql-0.0.1-SNAPSHOT.jar"]
