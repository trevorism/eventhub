package com.trevorism.event.service

import com.trevorism.event.pubsub.TestPubsubFacade
import org.junit.Test

/**
 * @author tbrooks
 */
class EventServiceTest {

    EventService service
    EventServiceTest(){
        service = new EventService()
        service.facade = new TestPubsubFacade()
    }

    @Test
    void testSendEvent() {
        assert "0" == service.sendEvent("unittest", null, null)
    }
}
