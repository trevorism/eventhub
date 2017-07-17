package com.trevorism.event.hook

import org.junit.Test

/**
 * @author tbrooks
 */
class StoreEventHookTest {

    @Test
    void testGetName() {
        StoreEventHook hook = new StoreEventHook()
        assert "storeevent" == hook.name
    }

    @Test
    void testPerformAction() {
        StoreEventHook hook = new StoreEventHook()
        def expectedRequest
        hook.http = ["post":{def data -> expectedRequest = data}]
        def data = ["date": new Date(), "trevor":"brooks"]
        hook.performAction(data)

        assert expectedRequest
        assert expectedRequest["path"] == "/"
        assert expectedRequest["body"] == data

    }
}
