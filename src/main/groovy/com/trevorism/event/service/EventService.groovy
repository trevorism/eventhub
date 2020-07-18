package com.trevorism.event.service

import com.google.api.core.ApiFuture
import com.google.cloud.pubsub.v1.Publisher
import com.google.protobuf.ByteString
import com.google.pubsub.v1.ProjectTopicName
import com.google.pubsub.v1.PubsubMessage
import com.trevorism.event.webapi.serialize.JacksonConfig

import java.util.concurrent.TimeUnit
import java.util.logging.Logger

/**
 * @author tbrooks
 */
class EventService {

    static final String PROJECT_ID = "trevorism-eventhub"
    private static final Logger log = Logger.getLogger(EventService.class.name)

    static String sendEvent(String topicName, Map<String, Object> data, String correlationId) {
        String json = JacksonConfig.objectMapper.writeValueAsString(data)
        PubsubMessage pubsubMessage = createPubsubMessage(json, topicName, correlationId)
        return sendMessage(topicName, pubsubMessage)
    }

    private static String sendMessage(String topicName, PubsubMessage pubsubMessage) {
        Publisher publisher = null
        try {
            ProjectTopicName topic = ProjectTopicName.of(PROJECT_ID, topicName)
            publisher = Publisher.newBuilder(topic).build()
            log.info("Sending message to topic ${topicName}")
            ApiFuture<String> future = publisher.publish(pubsubMessage)
            return future.get()
        } finally {
            publisher?.shutdown()
            publisher?.awaitTermination(1, TimeUnit.MINUTES)
        }
    }

    private static PubsubMessage createPubsubMessage(String json, String topicName, String correlationId) {
        ByteString byteString = ByteString.copyFromUtf8(json)
        def attributesMap = ["topic": topicName]
        if (correlationId)
            attributesMap.put("correlationId", correlationId)

        PubsubMessage pubsubMessage = PubsubMessage.newBuilder()
                .setData(byteString)
                .putAllAttributes(attributesMap)
                .build()
        return pubsubMessage
    }

}
