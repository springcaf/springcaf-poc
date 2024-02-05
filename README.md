# Springcaf POC Java Project

This is a POC project with core components of the springcaf library and a starter application. The overall library design took the model-driven aproach. The library contains two major components

1. An alternative to Hibernate. Hibernate is very heavy, and it is not well suited to the cloud era for things like micro-services and AWS lambdas. The data side of the library provides a light-weight O/R mapping, and automatic generation of data services and rest services.
2. UI side has library for server-side rendering of the UI components. Unlike JS-based framework such as react.js, this library is a model-driven framework. It not only renders the UI component, but also binds the UI components to a model. It can be used for "rendering as a service" to complement API services. 

The design concept goes one-step beyond the micro-services architecture. It allows micro-ui components to be bundled with the micro-services, to provide a single complete feature out of the box. The UI components are served out of the same URI as the micro-services, and injectable into a page node. 

## Project structure

This project contains the following major components:

1. src/main/java - Core library code and a starter application. This is used to build the web application and related database functionalities. Library code is under com.springcaf.core and starter code is under com.springcaf.starter. 
2. src/main/resources - Thymeleaf, jsp, js, css, and other client-side artifacts.
3. src/test/java - Unit test code. Not much here because the POC design is constantly changing, hard to keep up with unit tests.

