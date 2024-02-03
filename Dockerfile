FROM eclipse-temurin
LABEL authors="Maxim Kabanov"
COPY test_task_S7-1.0-SNAPSHOT-plain.jar /test_task_S7-1.0-SNAPSHOT-plain.jar
#CMD ["java", "-jar","/test_task_S7-1.0-SNAPSHOT-plain.jar"]
CMD ["java", "-jar", "/app/test_task_S7-1.0-SNAPSHOT-plain.jar"]