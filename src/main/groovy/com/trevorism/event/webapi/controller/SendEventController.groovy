package com.trevorism.event.webapi.controller

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.services.pubsub.Pubsub
import com.google.api.services.pubsub.model.PublishRequest
import com.google.api.services.pubsub.model.PubsubMessage
import com.google.api.services.pubsub.model.Topic
import com.trevorism.event.service.SubscriptionService
import com.trevorism.event.service.TopicService

import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.core.MediaType

/**
 * @author tbrooks
 */
@Path("/api")
class SendEventController {

    TopicService topicService = new TopicService()
    SubscriptionService subscriptionService = new SubscriptionService()



    @POST
    @Path("{name}")
    @Consumes(MediaType.APPLICATION_JSON)
    boolean sendEvent(@PathParam("name") String name, Map<String, Object> data ){


    }

    void publishMessage(String message, String outputTopic) {
        // Publish message to Pubsub.
        PubsubMessage pubsubMessage = new PubsubMessage()
        pubsubMessage.encodeData(message.getBytes())

        PublishRequest publishRequest = new PublishRequest()
        publishRequest.setTopic(outputTopic).setMessage(pubsubMessage)
        try {
            this.pubsub.topics().publish(publishRequest).execute()
        } catch (java.io.IOException e) {
        }
    }
}
