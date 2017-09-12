package com.trevorism.event.webapi.controller

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/")
class RootController {

    @GET
    @Path("ping")
    @Produces(MediaType.APPLICATION_JSON)
    String ping(){
        return "pong"
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<String> getEndpoints(){
        return ["ping", "help", "admin"]
    }

    @GET
    @Path("help")
    String help(){
        return """
GET /ping -- Return pong if alive, gnop otherwise<br/>
POST /api/{topic} -- Send event to topic<br/>
GET /admin/topic -- Get all Topics<br/>
GET /admin/topic/{topicName} -- Get FQDN of a topic<br/>
POST /admin/topic/ -- Create a topic<br/>
DELETE /admin/topic/{topicName} -- Delete a topic and all its subscriptions<br/>
GET /admin/subscription -- Get all subscriptions<br/>
GET /admin/subscription/{subscriptionName} -- Get subscription info<br/>
POST /admin/subscription -- Create a subscription on a topic<br/>
DELETE /admin/subscription/{subscriptionName} -- Delete a subscription<br/>
"""
    }
}
