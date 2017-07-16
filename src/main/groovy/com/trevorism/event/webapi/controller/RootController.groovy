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
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    List<String> getEndpoints(){
        return ["ping", "help"]
    }

    @GET
    @Path("help")
    String help(){
        return """
GET /ping -- Return pong if alive, gnop otherwise<br/>
"""
    }
}
