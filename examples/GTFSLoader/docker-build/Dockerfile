FROM java:8-jre

#COPY scripts  /usr/bin

RUN apt-get update && \
    apt-get install -y mysql-client

COPY GTFSLoader-1.0-SNAPSHOT-jar-with-dependencies.jar /opt/GTFSLoader/GTFSLoader-1.0-SNAPSHOT-jar-with-dependencies.jar
COPY run.sh /opt/GTFSLoader/run.sh
COPY scripts /opt/GTFSLoader/scripts
RUN chmod -R +x /opt/GTFSLoader/scripts
RUN chmod +x /opt/GTFSLoader/run.sh
WORKDIR /opt/GTFSLoader


EXPOSE 4567

#ENTRYPOINT ["java","-jar","GTFSLoader-1.0-SNAPSHOT-jar-with-dependencies.jar"]
ENTRYPOINT ["/opt/GTFSLoader/run.sh"]
CMD [""]

