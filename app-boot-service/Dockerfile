FROM amazoncorretto:11-alpine-jdk
VOLUME /tmp
COPY build/libs/app-boot-service-1.0.0.jar strattan-oakmant.jar
EXPOSE 8081
ENTRYPOINT java -jar $JVM_OPTS /strattan-oakmant.jar