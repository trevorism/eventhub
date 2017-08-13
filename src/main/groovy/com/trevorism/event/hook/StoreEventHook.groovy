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
        def dataToStore = [:]
        dataToStore["topic"] = event.message.attributes["topic"]
        dataToStore["date"] = event.message.publishTime
        dataToStore["subscription"] = event.subscription.subscription
        dataToStore.putAll(event.message.data)

        Gson gson = new Gson()
        String json = gson.toJson(dataToStore)

        client.post("http://datastore.trevorism.com/api/event/",json)

    }
    
}
