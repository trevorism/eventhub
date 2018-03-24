package com.trevorism.eventhub

import com.google.gson.Gson
import com.trevorism.data.FastDatastoreRepository
import com.trevorism.data.Repository
import com.trevorism.http.headers.HeadersHttpClient
import com.trevorism.http.headers.HeadersJsonHttpClient
import com.trevorism.http.util.ResponseUtils
import com.trevorism.secure.PasswordProvider
import org.apache.http.Header
import org.apache.http.client.methods.CloseableHttpResponse

/**
 * @author tbrooks
 */

this.metaClass.mixin(cucumber.api.groovy.Hooks)
this.metaClass.mixin(cucumber.api.groovy.EN)

Gson gson = new Gson()
String baseUrl
String correlationId = "123456"
HeadersHttpClient jsonHttpClient = new HeadersJsonHttpClient()
TestTopic testObject = new TestTopic(52, "testTopic", "testDescription")
PasswordProvider passwordProvider = new PasswordProvider()
CloseableHttpResponse response

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
        pong = new URL("http://datastore.trevorism.com/ping").text
    }
    catch (Exception ignored){
        Thread.sleep(10000)
        pong = new URL("http://datastore.trevorism.com/ping").text
    }
    assert pong == "pong"
}

When(~/^I post an event to "([^"]*)"$/) { String topic ->
    String json = gson.toJson(testObject)
    String url = "${baseUrl}/api/${topic}"
    ResponseUtils.closeSilently jsonHttpClient.post(url, json, ["Authorization":passwordProvider.password])

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
    response = jsonHttpClient.post(url, json, ["Authorization":passwordProvider.password,"X-Correlation-ID":correlationId])
}

Then(~/^the same correlationId is returned in the HTTP header$/) { ->
    Header header = response.getFirstHeader("X-Correlation-ID")
    assert header.value == correlationId
    ResponseUtils.closeSilently response
}