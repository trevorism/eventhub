package com.trevorism.event.service

import com.google.api.core.ApiFuture
import com.google.cloud.pubsub.v1.Publisher
import com.google.protobuf.ByteString
import com.google.pubsub.v1.ProjectTopicName
import com.google.pubsub.v1.PubsubMessage
import com.trevorism.event.webapi.serialize.JacksonConfig

/**
 * @author tbrooks
 */
class EventService {

    static final String PROJECT_ID = "trevorism-eventhub"

    String sendEvent(String topicName, Map<String, Object> data, String correlationId) {
        String json = JacksonConfig.objectMapper.writeValueAsString(data)
        PubsubMessage pubsubMessage = createPubsubMessage(json, topicName, correlationId)
        return sendMessage(topicName, pubsubMessage)
    }

    private static String sendMessage(String topicName, PubsubMessage pubsubMessage) {
        ProjectTopicName topic = ProjectTopicName.of(PROJECT_ID, topicName)
        Publisher publisher = Publisher.newBuilder(topic).build()
        ApiFuture<String> future = publisher.publish(pubsubMessage)
        return future.get()
    }

    private static PubsubMessage createPubsubMessage(String json, String topicName, String correlationId) {
        ByteString byteString = ByteString.copyFromUtf8(json)
        def attributesMap = ["topic": topicName]
        if(correlationId)
            attributesMap.put("correlationId", correlationId)

        PubsubMessage pubsubMessage = PubsubMessage.newBuilder()
                .setData(byteString)
                .putAllAttributes(attributesMap)
                .build()
        return pubsubMessage
    }

}
