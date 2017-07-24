package com.trevorism.event.hook

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder

/**
 * @author tbrooks
 */
class StoreEventHook implements Hook{

    private def http = new HTTPBuilder('https://events.walletinsights.com/trevor/')

    @Override
    String getName() {
        return "_store"
    }

    @Override
    void performAction(def data) {
        http.post( path: '', body: data, requestContentType: ContentType.JSON )
    }
    
}
