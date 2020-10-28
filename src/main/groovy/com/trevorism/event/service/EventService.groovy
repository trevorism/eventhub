package com.trevorism.event.service

import com.google.api.core.ApiFuture
import com.google.cloud.pubsub.v1.Publisher
import com.google.protobuf.ByteString
import com.google.pubsub.v1.ProjectTopicName
import com.google.pubsub.v1.PubsubMessage
import com.trevorism.event.webapi.serialize.JacksonConfig
import com.trevorism.http.headers.HeadersHttpClient
import com.trevorism.https.SecureHttpClient

import javax.ws.rs.core.HttpHeaders
import java.util.logging.Logger

/**
 * @author tbrooks
 */
class EventService {

    static final String PROJECT_ID = "trevorism-eventhub"
    private static final Logger log = Logger.getLogger(EventService.class.name)

    String sendEvent(String topicName, Map<String, Object> data, HttpHeaders httpHeaders) {
        String json = JacksonConfig.objectMapper.writeValueAsString(data)
        PubsubMessage pubsubMessage = createPubsubMessage(json, topicName, httpHeaders)
        return sendMessage(topicName, pubsubMessage)
    }

    private String sendMessage(String topicName, PubsubMessage pubsubMessage) {
        ProjectTopicName topic = ProjectTopicName.of(EventService.PROJECT_ID, topicName)
        Publisher publisher = Publisher.newBuilder(topic).build()
        log.info("Sending message to topic ${topicName}")
        ApiFuture<String> future = publisher.publish(pubsubMessage)
        publisher.shutdown()
        return future.get()
    }

    private static PubsubMessage createPubsubMessage(String json, String topicName, HttpHeaders httpHeaders) {
        ByteString byteString = ByteString.copyFromUtf8(json)
        def attributesMap = ["topic": topicName]
        attributesMap = addCorrelationId(httpHeaders, attributesMap)
        attributesMap = addToken(httpHeaders, attributesMap)

        PubsubMessage pubsubMessage = PubsubMessage.newBuilder()
                .setData(byteString)
                .putAllAttributes(attributesMap)
                .build()
        return pubsubMessage
    }

    private static def addCorrelationId(HttpHeaders httpHeaders, Map attributesMap) {
        String correlationId = httpHeaders.getHeaderString(HeadersHttpClient.CORRELATION_ID_HEADER_KEY)
        if (correlationId)
            attributesMap.put("correlationId", correlationId)
        return attributesMap
    }

    private static def addToken(HttpHeaders httpHeaders, Map attributesMap) {
        String bearerToken = httpHeaders.getHeaderString(SecureHttpClient.AUTHORIZATION)
        if (bearerToken && bearerToken.toLowerCase().startsWith(SecureHttpClient.BEARER_))
            attributesMap.put("token", bearerToken.substring(SecureHttpClient.BEARER_.length()))
        return attributesMap
    }

}
