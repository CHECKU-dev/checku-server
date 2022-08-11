FROM openjdk:11-jre

#ENV JASPYT_PASSWORD=$JASPYT_PASSWORD

COPY build/libs/checku-server-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "-Djasypt.encryptor.password=dudwls143", "app.jar"]

