# eventhub
![Jenkins](https://img.shields.io/jenkins/build/http/trevorism-build.eastus.cloudapp.azure.com/eventhub)
![Jenkins Coverage](https://img.shields.io/jenkins/coverage/jacoco/http/trevorism-build.eastus.cloudapp.azure.com/eventhub)
![GitHub last commit](https://img.shields.io/github/last-commit/trevorism/eventhub)
![GitHub language count](https://img.shields.io/github/languages/count/trevorism/eventhub)
![GitHub top language](https://img.shields.io/github/languages/top/trevorism/eventhub)

Latest Version: 1.3.0

A web api which wraps google pub/sub. Administer topics and subscriptions, send events.

This API is deployed here: [https://event.trevorism.com](https://event.trevorism.com)


##How to build

Create GOOGLE_APPLICATION_CREDENTIALS by following https://developers.google.com/accounts/docs/application-default-credentials

Then: `gradle clean build`


