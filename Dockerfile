#FROM maven:3.6.3-openjdk-8 as build
#COPY src /project
#COPY pom.xml /project
#WORKDIR /project
#RUN mvn clean install

#FROM openjdk:8-jdk-alpine
#COPY --from=build /project/target/*.jar app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]

# Nie udalo mi sie ustalic bledu podczas wykonywania maven clean install. Lokalnie polecenie to wykonuje sie bez problemu.
# Dlatego w repozytorium znajduje sie zbudowany juz plik jar, na podstawie ktorego budowany jest obraz.

FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]