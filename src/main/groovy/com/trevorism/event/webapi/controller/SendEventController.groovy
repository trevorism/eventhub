package com.trevorism.event.webapi.controller

import com.google.api.services.pubsub.Pubsub
import com.google.api.services.pubsub.model.PublishRequest
import com.google.api.services.pubsub.model.PubsubMessage
import com.google.appengine.repackaged.com.google.protobuf.ByteString
import com.trevorism.event.service.EventService
import com.trevorism.event.service.PubsubProvider
import com.trevorism.event.service.SubscriptionService
import com.trevorism.event.service.TopicService
import com.trevorism.event.webapi.serialize.JacksonConfig

import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.core.MediaType

/**
 * @author tbrooks
 */
@Path("/api")
class SendEventController{

    TopicService topicService = new TopicService()
    SubscriptionService subscriptionService = new SubscriptionService()
    EventService eventService = new EventService()


    @POST
    @Path("{topic}")
    @Consumes(MediaType.APPLICATION_JSON)
    boolean sendEvent(@PathParam("topic") String topic, Map<String, Object> data){
        topicService.createTopic(topic)
        subscriptionService.createSubscription(topic, "_store", "https://trevorism-eventhub.appspot.com/hook/_store")
        eventService.sendEvent(topic, data)
    }

}
