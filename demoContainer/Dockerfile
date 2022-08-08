FROM ubuntu:latest@sha256:42ba2dfce475de1113d55602d40af18415897167d47c2045ec7b6d9746ff148f
RUN apt-get update && apt-get install -y apt-transport-https && apt-get install -y bash && apt-get -y install sudo && apt install -y iproute2 && apt-get install -y iputils-ping && apt-get install -y kafkacat && apt-get -y install curl && apt-get install -y netcat
ENTRYPOINT ["tail", "-f", "/dev/null"]