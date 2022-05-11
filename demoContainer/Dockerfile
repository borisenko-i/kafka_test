FROM ubuntu:rolling@sha256:259c73507796f1175b4796cec9e1c783f2951af76ce7baeaec984892f5eee9e3
RUN apt-get update && apt-get install -y bash && apt-get -y install sudo && apt install -y iproute2 && apt-get install -y iputils-ping
ENTRYPOINT ["tail", "-f", "/dev/null"]