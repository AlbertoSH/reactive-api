# Reactive API with Spring + RxJava2

Basic example of how to setup a reactive REST API

# Steps

- Launch the server with 


    $ ./gradlew api:bootRun
    
    
- Launch the client


    $ ./gradlew client:run
    
The first call will be a *regular* call. It will query the server of a list of Items.
The second call will also query the server for the same list of items but it will serve
them reactively. You can check the response times logged inside the app
