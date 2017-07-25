package com.trevorism.event.hook

import com.trevorism.event.model.ReceivedEvent
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder

/**
 * @author tbrooks
 */
class StoreEventHook implements Hook{

    private def http = new HTTPBuilder('https://events.walletinsights.com/trevor/')

    @Override
    String getName() {
        return "store"
    }

    @Override
    void performAction(ReceivedEvent data) {
        http.post( path: '', body: data.message.data, requestContentType: ContentType.JSON )
    }
    
}
