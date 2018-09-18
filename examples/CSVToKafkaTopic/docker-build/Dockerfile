FROM java:8-jre

#COPY scripts  /usr/bin

RUN apt-get update && \
    apt-get install -y mysql-client


COPY  CSVToKafkaTopic-1.0-SNAPSHOT-jar-with-dependencies.jar /opt/CSVToKafkaTopic/CSVToKafkaTopic-1.0-SNAPSHOT-jar-with-dependencies.jar
COPY run.sh /opt/CSVToKafkaTopic/run.sh
COPY scripts /opt/CSVToKafkaTopic/scripts
RUN chmod -R +x /opt/CSVToKafkaTopic/scripts
RUN chmod +x /opt/CSVToKafkaTopic/run.sh
WORKDIR /opt/CSVToKafkaTopic


EXPOSE 4568

ENTRYPOINT ["/opt/CSVToKafkaTopic/run.sh"]
CMD [""]

