FROM amazoncorretto:11

RUN rm /etc/localtime
RUN ln -s /usr/share/zoneinfo/PST8PDT /etc/localtime

ENV PORT=8099
LABEL app.name="spring-boot-admin"

EXPOSE $PORT

COPY target/spring-boot-admin-*.jar /usr/src/spring-boot-admin.jar
WORKDIR                /usr/src/

ENV _JAVA_OPTIONS="-XX:+UseShenandoahGC \
-Xmx256m \
-XX:+UnlockExperimentalVMOptions \
-XX:MetaspaceSize=25m \
-XX:MinMetaspaceFreeRatio=10 \
-XX:ShenandoahUncommitDelay=1000 \
-XX:ShenandoahGuaranteedGCInterval=10000"

CMD ["java", "-jar", "spring-boot-admin.jar" ]

HEALTHCHECK --interval=15s --timeout=3s \
  CMD curl -f http://localhost:$PORT/actuator/health | grep UP || exit 1


