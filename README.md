# eventhub
Send events, and register subscribers

* gradle appengineRun -- Starts up a local instance

## Google app engine setup
Download the GCloud SDK

How to create in gcloud:
* gcloud projects create trevorism-eventhub
* gcloud config set project trevorism-eventhub
* gcloud app create
* gradle clean build appengineDeploy

In the portal
* Set up a service account through API Manager