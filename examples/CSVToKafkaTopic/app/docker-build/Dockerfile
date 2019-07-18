#FROM java:8-jre
#FROM sdorra/oracle-java-8
#FROM williamyeh/java8
#FROM java8ocr/java8-ocr
FROM openjdk:8


RUN apt-get update && \
    apt-get install -y mysql-client git

#RUN apk update && apk add mysql-client git

#run mkdir /opt


COPY  CSVToKafkaTopic-1.0-SNAPSHOT-jar-with-dependencies.jar /opt/CSVToKafkaTopic/CSVToKafkaTopic-1.0-SNAPSHOT-jar-with-dependencies.jar
COPY run.sh /opt/CSVToKafkaTopic/run.sh
RUN chmod +x /opt/CSVToKafkaTopic/run.sh
WORKDIR /opt/CSVToKafkaTopic


EXPOSE 4568

ENTRYPOINT ["/opt/CSVToKafkaTopic/run.sh"]
CMD [""]

