package com.trevorism.event.service

import com.google.pubsub.v1.PubsubMessage
import org.junit.Test

/**
 * @author tbrooks
 */
class EventServiceTest {

    @Test
    void testCreatePubsubMessage() {
        PublisherRegistry publisherRegistry = new PublisherRegistry()
        EventService eventService = new EventService(publisherRegistry);

        PubsubMessage message = eventService.createPubsubMessage("{}","unitTestTopic","3")
        assert message.getAttributesMap()["topic"] == "unitTestTopic"
        assert message.getAttributesMap()["correlationId"] == "3"

        publisherRegistry.shutdown()
    }
}
