package com.trevorism.event.service

import com.google.api.services.pubsub.model.PublishRequest
import com.google.api.services.pubsub.model.PubsubMessage
import com.trevorism.event.pubsub.GaePubsubFacade
import com.trevorism.event.pubsub.PubsubFacade
import com.trevorism.event.webapi.serialize.JacksonConfig

/**
 * @author tbrooks
 */
class EventService {

    private PubsubFacade facade = new GaePubsubFacade()

    String sendEvent(String topicName, Map<String, Object> data, String correlationId){
        PublishRequest publishRequest = createPublishRequest(topicName, data, correlationId)
        def publish = facade.publish("projects/$PubsubProvider.PROJECT/topics/${topicName}", publishRequest)
        def response = publish.execute()
        return response["messageIds"][0]
    }


    private PublishRequest createPublishRequest(String topicName, Map<String, Object> data, String correlationId) {
        PubsubMessage pubsubMessage = new PubsubMessage()
        String json = JacksonConfig.objectMapper.writeValueAsString(data)
        pubsubMessage.setData(json.bytes.encodeBase64().toString())
        pubsubMessage.setAttributes(["topic":topicName,"correlationId":correlationId])
        PublishRequest publishRequest = new PublishRequest()
        publishRequest.setMessages([pubsubMessage])
        publishRequest
    }
}
