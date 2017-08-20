package com.trevorism.event.hook

import com.google.gson.Gson
import com.trevorism.event.model.ReceivedEvent
import com.trevorism.http.HttpClient
import com.trevorism.http.JsonHttpClient

/**
 * @author tbrooks
 */
class StoreEventHook implements Hook{

    HttpClient client = new JsonHttpClient()

    @Override
    String getName() {
        return "store"
    }

    @Override
    void performAction(ReceivedEvent event) {
        String topic = event.message.attributes["topic"]
        def dataToStore = createDataForStorage(topic, event)

        Gson gson = new Gson()
        String json = gson.toJson(dataToStore)

        client.post("http://datastore.trevorism.com/api/${topic}/",json)

    }

    private def createDataForStorage(String topic, ReceivedEvent event) {
        def dataToStore = [:]
        dataToStore["topic"] = topic
        dataToStore["date"] = event.message.publishTime
        dataToStore["subscription"] = event.subscription.subscription
        dataToStore.putAll(event.message.data)
        dataToStore
    }

}
