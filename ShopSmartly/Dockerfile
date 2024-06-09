FROM openjdk:17-jdk
COPY target/ShopSmartly.jar .
EXPOSE 8181
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "ShopSmartly1.jar"]