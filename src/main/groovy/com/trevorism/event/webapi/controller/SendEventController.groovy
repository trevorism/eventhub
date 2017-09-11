package com.trevorism.event.webapi.controller

import com.trevorism.event.model.Subscriber
import com.trevorism.event.service.EventService
import com.trevorism.event.service.SubscriptionService
import com.trevorism.event.service.TopicService
import com.trevorism.http.headers.HeadersHttpClient
import com.trevorism.secure.Secure

import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.MediaType

/**
 * @author tbrooks
 */
@Path("/api")
class SendEventController{

    private final TopicService topicService = new TopicService()
    private final SubscriptionService subscriptionService = new SubscriptionService()
    private final EventService eventService = new EventService()


    @POST
    @Secure
    @Path("{topic}")
    @Consumes(MediaType.APPLICATION_JSON)
    boolean sendEvent(@Context HttpHeaders headers, @PathParam("topic") String topic, Map<String, Object> data){
        topicService.createTopic(topic)
        Subscriber subscriber = new Subscriber("store-${topic}", topic, "https://trevorism-eventhub.appspot.com/hook/store")
        subscriptionService.createSubscription(subscriber)
        eventService.sendEvent(topic, data, headers.getHeaderString(HeadersHttpClient.CORRELATION_ID_HEADER_KEY))
    }

}
