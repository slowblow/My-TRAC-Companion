# Run GTFSLOADER
` java -jar GTFSLoader-1.0-SNAPSHOT-jar-with-dependencies.jar -f <path_to_file> -db connect_test -ip $CONNECT_HOST`

it also accepts `-F <path_to_folder>` to handle several gtfs files. 

If everything went well the command 
```
docker run --net=host --rm confluentinc/cp-kafka:4.0.0 kafka-topics --describe --zookeeper localhost:32181|tail -n2
``` 
should return:

```
Topic:quickstart-jdbc-routes	PartitionCount:1	ReplicationFactor:1	Configs:
	Topic: quickstart-jdbc-routes	Partition: 0	Leader: 1001	Replicas: 1001	Isr: 1001
 ```
 
 Also, the command 
 ```
 docker run  --net=host  --rm  confluentinc/cp-schema-registry:4.0.0  kafka-avro-console-consumer --bootstrap-server localhost:29092 --topic quickstart-jdbc-routes --new-consumer --from-beginning
 ```
 
 should return:
 
 ``` 
 {"id":1,"modified":1522766686000,"agency_id":{"string":"TMB"},"route_id":{"string":"1.1"},"route_short_name":{"string":"L1"},"route_long_name":{"string":"Hospital de Bellvitge - Fondo"},"route_type":{"string":"1"},"route_url":{"string":"http://www.tmb.cat/ca/detall-linia-metro/-/linia/L1"},"route_color":{"string":"CE1126"},"route_text_color":{"string":"FFFFFF"}}
{"id":2,"modified":1522766686000,"agency_id":{"string":"TMB"},"route_id":{"string":"1.10"},"route_short_name":{"string":"L10"},"route_long_name":{"string":"Bon Pastor - Gorg"},"route_type":{"string":"1"},"route_url":{"string":"http://www.tmb.cat/ca/detall-linia-metro/-/linia/L10"},"route_color":{"string":"00A6D6"},"route_text_color":{"string":"FFFFFF"}}
{"id":3,"modified":1522766686000,"agency_id":{"string":"TMB"},"route_id":{"string":"1.11"},"route_short_name":{"string":"L11"},"route_long_name":{"string":"Trinitat Nova - Can Cuiàs"},"route_type":{"string":"1"},"route_url":{"string":"http://www.tmb.cat/ca/detall-linia-metro/-/linia/L11"},"route_color":{"string":"89B94C"},"route_text_color":{"string":"FFFFFF"}}
{"id":4,"modified":1522766686000,"agency_id":{"string":"TMB"},"route_id":{"string":"1.2"},"route_short_name":{"string":"L2"},"route_long_name":{"string":"Paral·lel - Badalona Pompeu Fabra"},"route_type":{"string":"1"},"route_url":{"string":"http://www.tmb.cat/ca/detall-linia-metro/-/linia/L2"},"route_color":{"string":"93248F"},"route_text_color":{"string":"FFFFFF"}}
{"id":5,"modified":1522766686000,"agency_id":{"string":"TMB"},"route_id":{"string":"1.3"},"route_short_name":{"string":"L3"},"route_long_name":{"string":"Zona Universitària - Trinitat Nova"},"route_type":{"string":"1"},"route_url":{"string":"http://www.tmb.cat/ca/detall-linia-metro/-/linia/L3"},"route_color":{"string":"1EB53A"},"route_text_color":{"string":"FFFFFF"}}
[...]
{"id":100,"modified":1522766686000,"agency_id":{"string":"TMB"},"route_id":{"string":"2.78"},"route_short_name":{"string":"78"},"route_long_name":{"string":"Estació de Sants / Sant Joan Despí"},"route_type":{"string":"3"},"route_url":{"string":"http://www.tmb.cat/ca/linia-de-bus/-/bus/78"},"route_color":{"string":"DC241F"},"route_text_color":{"string":"FFFFFF"}}
{"id":101,"modified":1522766686000,"agency_id":{"string":"TMB"},"route_id":{"string":"2.79"},"route_short_name":{"string":"79"},"route_long_name":{"string":"Pl. Espanya / <M> Av. Carrilet"},"route_type":{"string":"3"},"route_url":{"string":"http://www.tmb.cat/ca/linia-de-bus/-/bus/79"},"route_color":{"string":"DC241F"},"route_text_color":{"string":"FFFFFF"}}
{"id":102,"modified":1522766686000,"agency_id":{"string":"TMB"},"route_id":{"string":"2.91"},"route_short_name":{"string":"91"},"route_long_name":{"string":"Rambla / Bordeta"},"route_type":{"string":"3"},"route_url":{"string":"http://www.tmb.cat/ca/linia-de-bus/-/bus/91"},"route_color":{"string":"DC241F"},"route_text_color":{"string":"FFFFFF"}}
{"id":103,"modified":1522766686000,"agency_id":{"string":"TMB"},"route_id":{"string":"2.92"},"route_short_name":{"string":"92"},"route_long_name":{"string":"Pg. Marítim / Av. Tibidabo"},"route_type":{"string":"3"},"route_url":{"string":"http://www.tmb.cat/ca/linia-de-bus/-/bus/92"},"route_color":{"string":"DC241F"},"route_text_color":{"string":"FFFFFF"}}
{"id":104,"modified":1522766686000,"agency_id":{"string":"TMB"},"route_id":{"string":"2.94"},"route_short_name":{"string":"94"},"route_long_name":{"string":"Barri Almeda / Fontsanta"},"route_type":{"string":"3"},"route_url":{"string":"http://www.tmb.cat/ca/linia-de-bus/-/bus/94"},"route_color":{"string":"DC241F"},"route_text_color":{"string":"FFFFFF"}}
{"id":105,"modified":1522766686000,"agency_id":{"string":"TMB"},"route_id":{"string":"2.95"},"route_short_name":{"string":"95"},"route_long_name":{"string":"Barri Almeda / Pl. Fontsanta"},"route_type":{"string":"3"},"route_url":{"string":"http://www.tmb.cat/ca/linia-de-bus/-/bus/95"},"route_color":{"string":"DC241F"},"route_text_color":{"string":"FFFFFF"}}
{"id":106,"modified":1522766686000,"agency_id":{"string":"TMB"},"route_id":{"string":"2.96"},"route_short_name":{"string":"96"},"route_long_name":{"string":"<M> La Sagrera / Montcada i Reixac"},"route_type":{"string":"3"},"route_url":{"string":"http://www.tmb.cat/ca/linia-de-bus/-/bus/96"},"route_color":{"string":"DC241F"},"route_text_color":{"string":"FFFFFF"}}
{"id":107,"modified":1522766686000,"agency_id":{"string":"TMB"},"route_id":{"string":"2.97"},"route_short_name":{"string":"97"},"route_long_name":{"string":"Pg. Fabra i Puig / Vallbona"},"route_type":{"string":"3"},"route_url":{"string":"http://www.tmb.cat/ca/linia-de-bus/-/bus/97"},"route_color":{"string":"DC241F"},"route_text_color":{"string":"FFFFFF"}}
Processed a total of 107 messages
```
which is the result of the updates in the MySQL table, which has been done automatically by parsing the GTFS file. 
