package com.trevorism.event.hook

import com.trevorism.event.model.ReceivedEvent
import com.trevorism.event.webapi.serialize.JacksonConfig
import org.junit.Test

/**
 * @author tbrooks
 */
class StoreEventHookTest {

    StoreEventHook hook = new StoreEventHook()

    @Test
    void testGetName() {
        assert "store" == hook.name
    }

    @Test
    void testPerformAction() {
        ReceivedEvent event = ReceivedEvent.create(message: new ReceivedEvent.Message(data: '{"name":"trevor"}'.bytes.encodeBase64().toString()))
        assert event

        println JacksonConfig.objectMapper.writeValueAsString(event)
        println '{"name":"trevor"}'.bytes.encodeBase64().toString()

       // hook.performAction(event)

    }
}
