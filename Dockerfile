FROM openjdk:11
COPY out/artifacts/EstanteVirtual_jar /tmp
WORKDIR /tmp
EXPOSE 8080
ENTRYPOINT ["java","-jar","EstanteVirtual.jar"]