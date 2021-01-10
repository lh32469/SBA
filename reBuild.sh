mvn clean package
docker stop spring-boot-admin
docker rm spring-boot-admin
docker build -t spring-boot-admin .
docker run -d --restart=always --name spring-boot-admin --dns=172.17.0.1 -p 8099:8099 spring-boot-admin
