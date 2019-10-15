package com.trevorism.event.webapi.controller

import org.junit.Test

import javax.ws.rs.core.HttpHeaders

/**
 * @author tbrooks
 */
class SendEventControllerTest {

    @Test
    void testSendEvent() {
        SendEventController controller = new SendEventController()
        assert controller.sendEvent([getHeaderString:{}] as HttpHeaders, "testTopic", ["trevor":"whateverdata"])
    }
}
