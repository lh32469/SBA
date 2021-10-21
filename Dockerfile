FROM openjdk:11

ENV PORT=8099
LABEL app.name="spring-boot-admin"

EXPOSE $PORT

COPY target/spring-boot-admin-*.jar /usr/src/spring-boot-admin.jar
WORKDIR                /usr/src/

ENV _JAVA_OPTIONS="-Xmx256m"

CMD ["java", "-jar", "spring-boot-admin.jar" ]

HEALTHCHECK --interval=15s --timeout=3s \
  CMD curl -f http://localhost:$PORT/actuator/health | grep UP || exit 1


