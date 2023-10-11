FROM amazoncorretto:8
WORKDIR /app
COPY out/artifacts/test_demo_jar/demo.jar /app/demo.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/demo.jar"]
#FROM maven:3.8.2-jdk-8
#WORKDIR /app
#COPY . .
#RUN mvn clean install
#EXPOSE 8080
#CMD mvn spring-boot:run