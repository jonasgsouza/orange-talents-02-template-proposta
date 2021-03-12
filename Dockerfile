FROM openjdk:12-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENV ANALISE_URL="http://analise:9999"
ENV CARTOES_URL="http://contas:8888"
ENTRYPOINT ["java","-jar","/app.jar"]