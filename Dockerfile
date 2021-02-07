FROM openjdk:11

VOLUME /tmp

EXPOSE 8082

ADD target/springboot-bookstore.jar springboot-bookstore.jar

ENTRYPOINT ["java", "-jar", "springboot-bookstore.jar"]