FROM eclipse-temurin
LABEL authors="Maxim Kabanov"
COPY build/libs/test_task_S7-1.0-SNAPSHOT.jar /app/test_task_S7-1.0-SNAPSHOT.jar
CMD ["java", "-jar", "/app/test_task_S7-1.0-SNAPSHOT.jar"]