package com.trevorism.event.hook

import com.trevorism.event.model.ReceivedEvent
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder

/**
 * @author tbrooks
 */
class StoreEventHook implements Hook{

    private def http = new HTTPBuilder('http://datastore.trevorism.com/api/event/')

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


        http.post( path: '', body: dataToStore, requestContentType: ContentType.JSON )
    }
    
}
