package com.trevorism.eventhub

import com.google.gson.Gson
import com.trevorism.event.model.Subscriber
import com.trevorism.http.HttpClient
import com.trevorism.http.JsonHttpClient

/**
 * @author tbrooks
 */

this.metaClass.mixin(cucumber.api.groovy.Hooks)
this.metaClass.mixin(cucumber.api.groovy.EN)

Gson gson = new Gson()
String baseUrl = "http://event.trevorism.com"
HttpClient jsonHttpClient = new JsonHttpClient()
String topicUnderTest

String topicCreationResponse
String subscriptionCreationResponse

Given(~/^the topic "([^"]*)" does not exist$/) { String topic ->
    topicUnderTest = topic
    jsonHttpClient.delete("${baseUrl}/admin/topic/${topic}")
}

Given(~/^the topic "([^"]*)" already exists or is created$/) { String topic ->
    topicUnderTest = topic
    jsonHttpClient.post("${baseUrl}/admin/topic", topic)
}

Given(~/^the subscription "([^"]*)"  already exists or is created$/) { String subscription ->
    Subscriber subscriber = new Subscriber(subscription, topicUnderTest, "http://event.trevorism.com/hook/store")
    jsonHttpClient.post("${baseUrl}/admin/subscription", gson.toJson(subscriber))
}

When(~/^the topic "([^"]*)" is created$/) { String topic ->
    topicCreationResponse = jsonHttpClient.post("${baseUrl}/admin/topic", topic)
}

When(~/^the subscription "([^"]*)" is deleted$/) { String subscription ->
    jsonHttpClient.delete("${baseUrl}/admin/subscription/${subscription}")
}

When(~/^the subscription "([^"]*)" is created$/) { String subscription ->
    Subscriber subscriber = new Subscriber(subscription, topicUnderTest, "https://trevorism-eventhub.appspot.com/hook/store")
    jsonHttpClient.post("${baseUrl}/admin/subscription", gson.toJson(subscriber))
}

When(~/^the topic "([^"]*)" is deleted$/) { String topic ->
    jsonHttpClient.delete("${baseUrl}/admin/topic/${topic}")
}

When(~/^a subscription with a malformed url is created$/) { ->
    Subscriber subscriber = new Subscriber("errorSubscription", topicUnderTest, "googlyboogly")
    subscriptionCreationResponse = jsonHttpClient.post("${baseUrl}/admin/subscription", gson.toJson(subscriber))
}

Then(~/^the topic "([^"]*)" cannot be found$/) { String topic ->
    Thread.sleep(2000)
    assert !jsonHttpClient.get("${baseUrl}/admin/topic").contains(topic)
}

Then(~/^the topic "([^"]*)" exists$/) { String topic ->
    Thread.sleep(2000)
    assert jsonHttpClient.get("${baseUrl}/admin/topic/").contains(topic)
}

Then(~/^the subscription "([^"]*)" exists$/) { String subscription ->
    Thread.sleep(2000)
    assert jsonHttpClient.get("${baseUrl}/admin/subscription/${subscription}").contains(subscription)
}

Then(~/^the subscription "([^"]*)" does not exist$/) { String subscription ->
    Thread.sleep(2000)
    assert !jsonHttpClient.get("${baseUrl}/admin/subscription").contains(subscription)
}

Then(~/^an error is returned, indicating the topic already exists$/) { ->
    Thread.sleep(2000)
    assert topicCreationResponse == "false"
}

Then(~/^an error is returned, indicating the subscription could not be created$/) { ->
    Thread.sleep(1000)
    assert subscriptionCreationResponse == "false"
}