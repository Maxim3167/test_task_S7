FROM eclipse-temurin
LABEL authors="Maxim Kabanov"
ADD build/libs/test_task_S7-1.0-SNAPSHOT.jar /app/test_task_S7-1.0-SNAPSHOT.jar
RUN chmod +x /app/test_task_S7-1.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/app/test_task_S7-1.0-SNAPSHOT.jar"] CMD["-start"]