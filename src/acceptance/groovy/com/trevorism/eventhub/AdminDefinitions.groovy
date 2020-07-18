package com.trevorism.eventhub

import com.google.gson.Gson
import com.trevorism.event.model.Subscriber
import com.trevorism.http.headers.HeadersHttpClient
import com.trevorism.http.headers.HeadersJsonHttpClient
import com.trevorism.http.util.ResponseUtils
import com.trevorism.secure.PasswordProvider

/**
 * @author tbrooks
 */

this.metaClass.mixin(cucumber.api.groovy.Hooks)
this.metaClass.mixin(cucumber.api.groovy.EN)

Gson gson = new Gson()
String baseUrl = "https://event.trevorism.com"
HeadersHttpClient jsonHttpClient = new HeadersJsonHttpClient()
String topicUnderTest
PasswordProvider passwordProvider = new PasswordProvider()

String topicCreationResponse
String subscriptionCreationResponse

Given(~/^the topic "([^"]*)" does not exist$/) { String topic ->
    topicUnderTest = topic
    ResponseUtils.closeSilently jsonHttpClient.delete("${baseUrl}/admin/topic/${topic}", ["Authorization":passwordProvider.password])
}

Given(~/^the topic "([^"]*)" already exists or is created$/) { String topic ->
    topicUnderTest = topic
    ResponseUtils.closeSilently jsonHttpClient.post("${baseUrl}/admin/topic", topic, ["Authorization":passwordProvider.password])
}

Given(~/^the subscription "([^"]*)"  already exists or is created$/) { String subscription ->
    Subscriber subscriber = new Subscriber(subscription, topicUnderTest, "http://event.trevorism.com/hook/store")
    ResponseUtils.closeSilently jsonHttpClient.post("${baseUrl}/admin/subscription", gson.toJson(subscriber), ["Authorization":passwordProvider.password])
}

When(~/^the topic "([^"]*)" is created$/) { String topic ->
    topicCreationResponse = ResponseUtils.getEntity(jsonHttpClient.post("${baseUrl}/admin/topic", topic, ["Authorization":passwordProvider.password]))
}

When(~/^the subscription "([^"]*)" is deleted$/) { String subscription ->
    ResponseUtils.closeSilently jsonHttpClient.delete("${baseUrl}/admin/subscription/${subscription}", ["Authorization":passwordProvider.password])
}

When(~/^the subscription "([^"]*)" is created$/) { String subscription ->
    Subscriber subscriber = new Subscriber(subscription, topicUnderTest, "https://trevorism-eventhub.appspot.com/hook/store")
    ResponseUtils.closeSilently jsonHttpClient.post("${baseUrl}/admin/subscription", gson.toJson(subscriber), ["Authorization":passwordProvider.password])
}

When(~/^the topic "([^"]*)" is deleted$/) { String topic ->
    ResponseUtils.closeSilently jsonHttpClient.delete("${baseUrl}/admin/topic/${topic}", ["Authorization":passwordProvider.password])
}

When(~/^a subscription with a malformed url is created$/) { ->
    Subscriber subscriber = new Subscriber("errorSubscription", topicUnderTest, "googlyboogly")
    subscriptionCreationResponse = ResponseUtils.getEntity(jsonHttpClient.post("${baseUrl}/admin/subscription", gson.toJson(subscriber), ["Authorization":passwordProvider.password]))
}

Then(~/^the topic "([^"]*)" cannot be found$/) { String topic ->
    Thread.sleep(2000)
    assert !ResponseUtils.getEntity(jsonHttpClient.get("${baseUrl}/admin/topic", ["Authorization":passwordProvider.password])).contains(topic)
}

Then(~/^the topic "([^"]*)" exists$/) { String topic ->
    Thread.sleep(2000)
    assert ResponseUtils.getEntity(jsonHttpClient.get("${baseUrl}/admin/topic/", ["Authorization":passwordProvider.password])).contains(topic)
}

Then(~/^the subscription "([^"]*)" exists$/) { String subscription ->
    Thread.sleep(2000)
    assert ResponseUtils.getEntity(jsonHttpClient.get("${baseUrl}/admin/subscription/${subscription}", ["Authorization":passwordProvider.password])).contains(subscription)
}

Then(~/^the subscription "([^"]*)" does not exist$/) { String subscription ->
    Thread.sleep(2000)
    assert !ResponseUtils.getEntity(jsonHttpClient.get("${baseUrl}/admin/subscription", ["Authorization":passwordProvider.password])).contains(subscription)
}

Then(~/^an error is returned, indicating the topic already exists$/) { ->
    Thread.sleep(2000)
    assert topicCreationResponse == "false"
}

Then(~/^an error is returned, indicating the subscription could not be created$/) { ->
    Thread.sleep(1000)
    assert subscriptionCreationResponse == "false"
}