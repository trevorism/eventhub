package com.trevorism.event.service

import com.google.api.services.pubsub.Pubsub
import com.google.api.services.pubsub.model.PublishRequest
import com.google.api.services.pubsub.model.PubsubMessage
import com.google.appengine.repackaged.com.google.protobuf.ByteString
import com.trevorism.event.webapi.serialize.JacksonConfig

/**
 * @author tbrooks
 */
class EventService {

    private Pubsub pubsub = PubsubProvider.INSTANCE.get()

    String sendEvent(String topicName, Map<String, Object> data){
        PublishRequest publishRequest = createPublishRequest(topicName, data)
        def publish = pubsub.projects().topics().publish("projects/$PubsubProvider.PROJECT/topics/${topicName}", publishRequest)
        def response = publish.execute()
        return response["messageIds"][0]
    }


    private PublishRequest createPublishRequest(String topicName, Map<String, Object> data) {
        PubsubMessage pubsubMessage = new PubsubMessage()
        String json = JacksonConfig.objectMapper.writeValueAsString(data)
        pubsubMessage.encodeData(ByteString.copyFromUtf8(json).toByteArray())
        pubsubMessage.setAttributes(["topic":topicName])
        PublishRequest publishRequest = new PublishRequest()
        publishRequest.setMessages([pubsubMessage])
        publishRequest
    }
}
