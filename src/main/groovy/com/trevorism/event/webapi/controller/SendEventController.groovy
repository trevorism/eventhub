package com.trevorism.event.webapi.controller

import com.trevorism.event.model.Subscriber
import com.trevorism.event.service.EventService
import com.trevorism.event.service.SubscriptionService
import com.trevorism.event.service.TopicService
import com.trevorism.http.headers.HeadersHttpClient
import com.trevorism.secure.Secure
import io.swagger.annotations.Api

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


    @POST
    @Secure
    @Path("{topic}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    boolean sendEvent(@Context HttpHeaders headers, @PathParam("topic") String topic, Map<String, Object> data){
        topicService.createTopic(topic)
        subscriptionService.createSubscription(new Subscriber("store-${topic}", topic, "https://listen-dot-trevorism-eventhub.appspot.com/_ah/push-handlers/store_${topic}"))
        subscriptionService.createSubscription(new Subscriber("handle-${topic}", topic, "https://listen-dot-trevorism-eventhub.appspot.com/_ah/push-handlers/handle_${topic}"))
        String response = eventService.sendEvent(topic, data, headers.getHeaderString(HeadersHttpClient.CORRELATION_ID_HEADER_KEY))
        log.info("Sent event: ${data} with a response of: ${response}")
        return response
    }

}
