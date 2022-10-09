FROM khipu/openjdk17-alpine:latest
EXPOSE 8085
ADD /target/mis2022-0.0.1-SNAPSHOT.jar mis2022-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","mis2022-0.0.1-SNAPSHOT.jar"]