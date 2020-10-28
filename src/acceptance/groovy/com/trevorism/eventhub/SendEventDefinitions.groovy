package com.trevorism.eventhub

import com.google.gson.Gson
import com.trevorism.data.FastDatastoreRepository
import com.trevorism.data.Repository
import com.trevorism.eventhub.model.TestTopic
import com.trevorism.https.DefaultSecureHttpClient
import com.trevorism.https.SecureHttpClient

/**
 * @author tbrooks
 */

this.metaClass.mixin(cucumber.api.groovy.Hooks)
this.metaClass.mixin(cucumber.api.groovy.EN)

Gson gson = new Gson()
String baseUrl
String correlationId = "123456"
SecureHttpClient secureHttpClient = new DefaultSecureHttpClient()
TestTopic testObject = new TestTopic(52, "testTopic", "testDescription")
def response

Given(~/^the eventhub application is alive with a base URL of "([^"]*)"$/) { String url ->
    baseUrl = url
    String pong = ""
    try{
        pong = new URL("${url}/ping").text
    }
    catch (Exception ignored){
        Thread.sleep(10000)
        pong = new URL("${url}/ping").text
    }
    assert pong == "pong"
}

Given(~/^the datastore application is alive$/) { ->
    String pong = ""
    try{
        pong = new URL("https://datastore.trevorism.com/ping").text
    }
    catch (Exception ignored){
        Thread.sleep(10000)
        pong = new URL("https://datastore.trevorism.com/ping").text
    }
    assert pong == "pong"
}

When(~/^I post an event to "([^"]*)"$/) { String topic ->
    String json = gson.toJson(testObject)
    String url = "${baseUrl}/api/${topic}"
    secureHttpClient.post(url, json)
}

Then(~/^then the event is saved to the datastore within (\d+) seconds$/) { int delay ->
    Thread.sleep(delay * 1000)
    Repository<TestTopic> repository = new FastDatastoreRepository<>(TestTopic)
    TestTopic topic = repository.get("52")
    assert topic.id == 52
    assert topic.name == "testTopic"
    assert topic.description == "testDescription"
    repository.delete("52")
}

When(~/^I post an event to "([^"]*)" with a correlationId$/) { String topic ->
    String json = gson.toJson(testObject)
    String url = "${baseUrl}/api/${topic}"
    response = secureHttpClient.post(url, json, correlationId)
}