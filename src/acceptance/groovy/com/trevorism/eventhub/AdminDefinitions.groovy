package com.trevorism.eventhub

import com.google.gson.Gson
import com.trevorism.event.model.Subscriber
import com.trevorism.http.BlankHttpClient
import com.trevorism.http.HttpClient
import com.trevorism.http.JsonHttpClient

/**
 * @author tbrooks
 */

this.metaClass.mixin(cucumber.api.groovy.Hooks)
this.metaClass.mixin(cucumber.api.groovy.EN)

Gson gson = new Gson()
String baseUrl = "http://event.trevorism.com"
HttpClient blankHttpClient = new BlankHttpClient()
HttpClient jsonHttpClient = new JsonHttpClient()
String topicUnderTest

Given(~/^the topic "([^"]*)" does not exist$/) { String topic ->
    topicUnderTest = topic
    jsonHttpClient.delete("${baseUrl}/admin/topic/${topic}")
}

Given(~/^the topic "([^"]*)" already exists or is created$/) { String topic ->
    topicUnderTest = topic
    blankHttpClient.post("${baseUrl}/admin/topic", topic)
}

Given(~/^the subscription "([^"]*)"  already exists or is created$/) { String subscription ->
    Subscriber subscriber = new Subscriber(subscription, topicUnderTest, "http://event.trevorism.com/hook/store")
    jsonHttpClient.post("${baseUrl}/admin/subscription", gson.toJson(subscriber))
}

When(~/^the topic "([^"]*)" is created$/) { String topic ->
    blankHttpClient.post("${baseUrl}/admin/topic", topic)
}

When(~/^the subscription "([^"]*)" is deleted$/) { String subscription ->
    jsonHttpClient.delete("${baseUrl}/admin/subscription/${subscription}")
}

When(~/^the subscription "([^"]*)" is created$/) { String subscription ->
    Subscriber subscriber = new Subscriber(subscription, topicUnderTest, "http://event.trevorism.com/hook/store")
    jsonHttpClient.post("${baseUrl}/admin/subscription", gson.toJson(subscriber))
}

When(~/^the topic "([^"]*)" is deleted$/) { String topic ->
    jsonHttpClient.delete("${baseUrl}/admin/topic/${topic}")
}

When(~/^a subscription with a malformed url is created$/) { ->
    Subscriber subscriber = new Subscriber("errorSubscription", topicUnderTest, "googlyboogly")
    jsonHttpClient.post("${baseUrl}/admin/subscription", gson.toJson(subscriber))
}

Then(~/^the topic "([^"]*)" cannot be found$/) { String topic ->
    assert !jsonHttpClient.get("${baseUrl}/admin/topic").contains(topic)
}

Then(~/^the topic "([^"]*)" exists$/) { String topic ->
    assert jsonHttpClient.get("${baseUrl}/admin/topic/").contains(topic)
}

Then(~/^the subscription "([^"]*)" exists$/) { String subscription ->
    assert jsonHttpClient.get("${baseUrl}/admin/subscription/${subscription}").contains(subscription)
}

Then(~/^the subscription "([^"]*)" does not exist$/) { String subscription ->
    assert !jsonHttpClient.get("${baseUrl}/admin/subscription").contains(subscription)
}

Then(~/^an error is returned, indicating the topic already exists$/) { ->

}

Then(~/^an error is returned, indicating the subscription could not be created$/) { ->

}