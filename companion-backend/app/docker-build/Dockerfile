FROM golang:1.8 as builder

RUN curl https://glide.sh/get | sh

RUN apt-get install git openssh-client  


FROM debian:jessie as final
RUN printf "deb http://archive.debian.org/debian/ jessie main\ndeb-src http://archive.debian.org/debian/ jessie main\ndeb http://security.debian.org jessie/updates main\ndeb-src http://security.debian.org jessie/updates main" > /etc/apt/sources.list
RUN apt-get update
RUN apt-get install -y mysql-client
RUN apt-get install -y git
RUN apt-get install -y curl


WORKDIR /root/
COPY ./scripts ./scripts
COPY ./run.sh ./

EXPOSE 8080
ENTRYPOINT ["/root/run.sh"]
CMD [""]






