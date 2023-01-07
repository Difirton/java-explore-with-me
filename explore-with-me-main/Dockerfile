FROM amazoncorretto:11-alpine-jdk
COPY target/*with-dependencies.jar explore-with-me-main.jar
ENTRYPOINT ["java","-jar","/explore-with-me-main.jar"]