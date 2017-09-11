package com.trevorism.event.hook

import com.google.gson.Gson
import com.trevorism.event.model.ReceivedEvent
import com.trevorism.http.headers.HeadersHttpClient
import com.trevorism.http.headers.HeadersJsonHttpClient
import com.trevorism.secure.PasswordProvider

/**
 * @author tbrooks
 */
class StoreEventHook implements Hook{

    HeadersHttpClient client = new HeadersJsonHttpClient()

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

        client.post("http://datastore.trevorism.com/api/${topic}/",json, [Authorization:PasswordProvider.AUTHORIZATION_HEADER])
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
