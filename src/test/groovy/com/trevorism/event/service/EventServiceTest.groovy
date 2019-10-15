package com.trevorism.event.service

import com.google.pubsub.v1.PubsubMessage
import org.junit.Test

/**
 * @author tbrooks
 */
class EventServiceTest {

    private EventService service

    EventServiceTest(){
        service = new EventService()
    }

    @Test
    void testCreatePubsubMessage() {
        PubsubMessage message = service.createPubsubMessage("{}","unitTestTopic","3")
        assert message.getAttributesMap()["topic"] == "unitTestTopic"
        assert message.getAttributesMap()["correlationId"] == "3"


    }
}
