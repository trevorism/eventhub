package com.trevorism.event.webapi.controller

import com.trevorism.event.model.Subscriber
import com.trevorism.event.service.EventService
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
class SendEventController{

    TopicService topicService = new TopicService()
    SubscriptionService subscriptionService = new SubscriptionService()
    EventService eventService = new EventService()


    @POST
    @Path("{topic}")
    @Consumes(MediaType.APPLICATION_JSON)
    boolean sendEvent(@PathParam("topic") String topic, Map<String, Object> data){
        topicService.createTopic(topic)
        Subscriber subscriber = new Subscriber("_store", topic, "https://trevorism-eventhub.appspot.com/hook/store")
        subscriptionService.createSubscription(subscriber)
        eventService.sendEvent(topic, data)
    }

}
