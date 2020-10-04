package com.trevorism.event.service

import com.google.pubsub.v1.PubsubMessage
import org.junit.Test

import javax.ws.rs.core.HttpHeaders

/**
 * @author tbrooks
 */
class EventServiceTest {

    @Test
    void testCreatePubsubMessage() {
        PublisherRegistry publisherRegistry = new PublisherRegistry()
        EventService eventService = new EventService(publisherRegistry);

        PubsubMessage message = eventService.createPubsubMessage("{}","unitTestTopic", [getHeaderString: {input ->
            if(input == "X-Correlation-ID"){
                return "3"
            }
            if(input == "Authorization"){
                return "bearer eyaskdfjl.eyasdfr.eysdfsg"
            }
            return null
        }] as HttpHeaders)
        assert message.getAttributesMap()["topic"] == "unitTestTopic"
        assert message.getAttributesMap()["correlationId"] == "3"
        assert message.getAttributesMap()["token"] == "eyaskdfjl.eyasdfr.eysdfsg"

        publisherRegistry.shutdown()
    }
}
