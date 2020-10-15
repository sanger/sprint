FROM maven:3.6-adoptopenjdk-8

WORKDIR /api/

COPY pom.xml /api/pom.xml
COPY src /api/src/
COPY printers /api/printers/

RUN mvn package

EXPOSE 8080

CMD ["mvn", "spring-boot:run"]