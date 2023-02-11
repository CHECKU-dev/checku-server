FROM openjdk:11-jre

ARG JASYPT_PASSWORD=""
ARG JAR_FILE=build/libs/checku-server-0.0.1-SNAPSHOT.jar

ENV JASYPT_PASSWORD $JASYPT_PASSWORD

COPY ${JAR_FILE} app.jar

ENTRYPOINT [ \
            "java", \
            "-Dserver.port=8080", \
            "-Dspring.profiles.active=prod", \
            "-Djasypt.encryptor.password=${JASYPT_PASSWORD}", \
            "-jar", \
            "app.jar" \
]
