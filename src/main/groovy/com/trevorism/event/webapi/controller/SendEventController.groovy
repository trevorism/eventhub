package com.trevorism.event.webapi.controller

import com.trevorism.event.model.Subscriber
import com.trevorism.event.service.EventService

import com.trevorism.event.service.SubscriptionService
import com.trevorism.event.service.TopicService
import com.trevorism.secure.Roles
import com.trevorism.secure.Secure
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation

import javax.annotation.PreDestroy
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.MediaType
import java.util.logging.Logger

/**
 * @author tbrooks
 */
@Api("Event Operations")
@Path("/api")
class SendEventController{

    private static final Logger log = Logger.getLogger(SendEventController.class.name)

    private final TopicService topicService = new TopicService()
    private final SubscriptionService subscriptionService = new SubscriptionService()
    private final EventService eventService = new EventService()

    @ApiOperation(value = "Sends an event on the given topic **Secure")
    @POST
    @Secure(Roles.USER)
    @Path("{topic}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    boolean sendEvent(@Context HttpHeaders headers, @PathParam("topic") String topic, Map<String, Object> data){
        topicService.createTopic(topic)
        subscriptionService.createSubscription(new Subscriber("store-${topic}", topic, "https://listen-dot-trevorism-eventhub.appspot.com/_ah/push-handlers/store_${topic}"))
        subscriptionService.createSubscription(new Subscriber("handle-${topic}", topic, "https://listen-dot-trevorism-eventhub.appspot.com/_ah/push-handlers/handle_${topic}"))
        String response = eventService.sendEvent(topic, data, headers)
        log.info("Sent event: ${data}")
        return response
    }

}
