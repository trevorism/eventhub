package com.trevorism.event.webapi.controller

import com.trevorism.event.pubsub.TestPubsubFacade
import org.junit.Test

import javax.ws.rs.core.HttpHeaders

/**
 * @author tbrooks
 */
class SendEventControllerTest {

    @Test
    void testSendEvent() {
        SendEventController controller = new SendEventController()

        controller.eventService.facade = new TestPubsubFacade()

        assert controller.sendEvent([getHeaderString:{}] as HttpHeaders, "testTopic", ["trevor":"whateverdata"])

    }
}
