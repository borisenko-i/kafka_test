FROM ubuntu:latest
RUN apt-get update && apt-get install -y apt-transport-https && apt-get install -y bash && apt-get -y install sudo && apt install -y iproute2 && apt-get install -y iputils-ping && apt-get install -y kafkacat && apt-get -y install curl && apt-get install -y netcat
ENTRYPOINT ["tail", "-f", "/dev/null"]