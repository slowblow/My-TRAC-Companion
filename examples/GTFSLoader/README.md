
## Summary

This application is an example of a Source component where data is loaded into a datastore (MySQL in this case), and kafka-connect is in charge of automatically tracking the changes to the datastore and pushing them to the CIGO platform. This application consists of a mysql database and the gtfsloader, which is a webapp that loads a gtfsfile and inserts it into the mysql data store. Such app shows a button

The app is already build and pushed to docker hub, so it does not need to be compiled nor built.

## Running the example app

To run the example application (the GTFSLoader), run the following command from one of the deployment folders (deployments/compose or deployments/kubernetes) depending on your prefered deployment type:

```
>$ ./start.sh
```
## Getting the ip of the app on the docker compose deployment

On a linux machine, the app will be accessible from 

```
http://localhost:4567
```
From OSX, instead of localhost, the ip of the app can be retrieved with the following command.

```
eval $(docker-machine env gtfsloader)
```

## Getting the ip of the app on the Kubernetes deployment
To get the ip of the example app, run the following command:

```
minikube service gtfsloader-svc --url
```

so you should see something like this

```
>$ http://192.168.99.100:31018/
```

Accessing the app url, will show the following screen:

<kbd><img src="images/app.png" /></kbd>

And after uploading the gtfs file, you should see something similar to the following screen:

<kbd><img src="images/app2.png" /></kbd>

## Stopping the app
Finally, you can stop the app with the provided script:

```
cd example
>$ ./stop.sh
```
