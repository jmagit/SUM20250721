# Eclipse Starter for Jakarta EE
This is a sample application generated by the Eclipse Foundation starter for Jakarta EE.

You can run the application by executing the following command from the directory where this file resides. 
Please ensure you have installed a [Java SE implementation](https://adoptium.net) appropriate for your 
Jakarta EE version and runtime choice (this sample assumes Java SE 17). Note, 
the [Maven Wrapper](https://maven.apache.org/wrapper/) is already included in the project, so a Maven install 
is not actually needed. You may first need to execute `chmod +x mvnw`.

```
./mvnw clean package cargo:run
```

Once the runtime starts, you can access the project at [http://localhost:8080/demo-jee-maven](http://localhost:8080/demo-jee-maven).

