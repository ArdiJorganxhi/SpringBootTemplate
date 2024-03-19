FROM debian:stable-slim
RUN apt update && apt -y upgrade
RUN apt install -y openjdk-17-jdk maven
RUN apt clean
RUN rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests

RUN ls ./target
#EXPOSE 4001
ENTRYPOINT ["java","-jar","./target/app.jar"]