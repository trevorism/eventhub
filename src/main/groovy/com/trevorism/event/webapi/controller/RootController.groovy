package com.trevorism.event.webapi.controller

import com.google.appengine.api.datastore.DatastoreService
import com.google.appengine.api.datastore.DatastoreServiceFactory

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/")
class RootController {

    private final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService()

    @GET
    @Path("ping")
    @Produces(MediaType.APPLICATION_JSON)
    String ping(){
        return "pong"
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<String> getEndpoints(){
        return ["ping", "help"]
    }

    @GET
    @Path("help")
    String help(){
        return """
GET /ping -- Return pong if alive, gnop otherwise<br/>
GET /hook -- Return all registered hooks<br/>
POST /hook/{hookName} -- Invoke Hook<br/>
POST /api/{topic} -- Send event to topic<br/>
GET /admin/topic -- Get all Topics<br/>
GET /admin/topic/{topicName} -- Get FQDN of a topic<br/>
POST /admin/topic/ -- Create a topic<br/>
DELETE /admin/topic/{topicName} -- Delete a topic and all its subscriptions<br/>
GET /admin/subscriptions -- Get all subscriptions<br/>
GET /admin/subscriptions/{subscriptionName} -- Get subscription info<br/>
POST /admin/subscription -- Create a subscription on a topic<br/>
DELETE /admin/subscriptions/{subscriptionName} -- Delete a subscription<br/>

"""
    }
}
