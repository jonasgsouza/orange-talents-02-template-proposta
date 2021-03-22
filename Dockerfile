FROM openjdk:12-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENV ANALISE_HOST="http://analise:9999"
ENV CARTOES_HOST="http://contas:8888/api/cartoes"
ENV JAEGER_ENDPOINT="http://jaeger:14268/api/traces"
ENV KEYCLOAK_ISSUER_URI="http://keycloak:18080/auth/realms/proposta"
ENV KEYCLOAK_JWKS_URI="http://keycloak:18080/auth/realms/proposta/protocol/openid-connect/certs"
ENV LOG_LEVEL="DEBUG"
ENTRYPOINT ["java","-jar","/app.jar"]