package com.trevorism.event.service

import com.google.api.services.pubsub.Pubsub
import com.google.api.services.pubsub.model.PublishRequest
import com.google.api.services.pubsub.model.PubsubMessage

/**
 * @author tbrooks
 */
class EventService {

    private Pubsub pubsub = PubsubProvider.INSTANCE.get()


    void sendEvent(String topicName, Map<String, Object> data){
        PubsubMessage pubsubMessage = new PubsubMessage()
        data.each {k,v ->
            pubsubMessage.set(k, v)
        }
        PublishRequest publishRequest = new PublishRequest()
        publishRequest.setMessages([pubsubMessage])

        pubsub.projects().topics().publish("projects/$PubsubProvider.PROJECT/topics/${topicName}", publishRequest)

    }
}
