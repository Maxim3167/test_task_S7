FROM eclipse-temurin
LABEL authors="Maxim Kabanov"
COPY build/libs/test_task_S7-1.0-SNAPSHOT.jar test_task_S7-1.0-SNAPSHOT.jar
#CMD ["java", "-jar","/test_task_S7-1.0-SNAPSHOT-plain.jar"]
CMD ["java", "-jar", "test_task_S7-1.0-SNAPSHOT.jar"]