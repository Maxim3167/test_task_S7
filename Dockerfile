FROM eclipse-temurin
LABEL authors="Maxim Kabanov"
ADD build/libs/test_task_S7-1.0-SNAPSHOT.jar /app/test_task_S7-1.0-SNAPSHOT.jar
RUN chmod +x /app/test_task_S7-1.0-SNAPSHOT.jar
RUN apt-get update && apt-get install -y openjdk-11-jdk
ENV JAVA_HOME /usr/lib/jvm/java-11-openjdk-amd64
ENTRYPOINT ["java", "-jar", "/app/test_task_S7-1.0-SNAPSHOT.jar"] CMD["-start"]