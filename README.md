# Overview
This project provides a webapp for reading and creating new cakes from a simple in memory database.
It runs on Spring boot (webflux stack) and has an Angular front end.

# Running the project
To run this project, first install and setup any missing dependencies:
- Your favourite 8+ JDK e.g. [Liberica](https://bell-sw.com/pages/downloads/#mn)
- [Maven](https://maven.apache.org/install.html)
- [Node.js](https://nodejs.org/en/download/)
- Angular CLI: `npm install -g @angular/cli`
- Checkout the and navigate to the source directory and run `run.bat` from a console
- The cake UI should now be running on `localhost:4200`
- The cakes API is running on `localhost:8080` with swagger available at `http://localhost:8080/webjars/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/`

This project uses lombok, so if running in your IDE you may need to add support for this. IntelliJ comes with bundles support.

*note that some of the example images are no longer available, so a resource file has been included: `init.json` where initialisation data can be added.

# Logging in
The app will let you view the cakes unauthenticated, but to add a new one you'll need to log in.

The app uses OAuth2 with [Auth0](https://auth0.com/) to enable authentication.
You can login with your google account, or there is also a local test user set up:

`username: testuser@test.com`

`password: A-Value-To-Login`

* Note that the initial load of the keys by spring can be pretty slow
* Subsequent adds should be quick