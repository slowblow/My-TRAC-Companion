#FROM golang:1.8 as builder

#RUN curl https://glide.sh/get | sh

#RUN apt-get install git openssh-client  


FROM re6exp/debian-jessie-oracle-jdk-8 as final
RUN printf "deb http://archive.debian.org/debian/ jessie main\ndeb-src http://archive.debian.org/debian/ jessie main\ndeb http://security.debian.org jessie/updates main\ndeb-src http://security.debian.org jessie/updates main" > /etc/apt/sources.list
RUN apt-get update
RUN apt-get install -y mysql-client
RUN apt-get install -y git
RUN apt-get install -y curl
RUN apt-get install -y maven

WORKDIR /root/
COPY ./scripts ./scripts
#COPY ./MYSQLInitDataModel-1.0-SNAPSHOT-jar-with-dependencies.jar ./
COPY ./run.sh ./

EXPOSE 8080
ENTRYPOINT ["/root/run.sh"]
CMD [""]






