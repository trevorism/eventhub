package com.trevorism.event.model

import org.junit.Test

/**
 * @author tbrooks
 */
class ReceivedEventTest {

    @Test
    void testCreate() {
        def map = ["message"     : ["publishTime" : "2017-07-24T13:16:46.554Z",
                                    "publish_time": "2017-07-24T13:16:46.554Z",
                                    "data"        : "eyJuYW1lIjoiU2VhbiIsImFnZSI6MTksImFkZHJlc3MiOnsic3RyZWV0IjoiTWluZXJzIiwiY2l0eSI6ImRvdmVyIn19",
                                    "message_id"  : "73421535714865",
                                    "messageId"   : "73421535714865",
                                    "attributes"  : [:]],
                   "subscription": "projects\\/trevorism-eventhub\\/subscriptions\\/store"]

        ReceivedEvent event = ReceivedEvent.create(map)

        println event.message.data
        println event.message.publishTime
        println event.message.messageId
        println event.message.attributes
        println event.subscription.subscription

    }
}
