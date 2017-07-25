package com.trevorism.event.webapi.controller

import com.trevorism.event.hook.Hook
import com.trevorism.event.hook.HookRegistry
import com.trevorism.event.model.ReceivedEvent

import javax.ws.rs.*
import javax.ws.rs.core.MediaType

/**
 * @author tbrooks
 */
@Path("/hook")
class HookController {

    @POST
    @Path("{name}")
    @Consumes(MediaType.APPLICATION_JSON)
    void invokeHook(@PathParam("name") String name, Map<String, Object> data) {
        Hook hook = HookRegistry.INSTANCE.getHook(name)
        ReceivedEvent event = ReceivedEvent.create(data)
        hook.performAction(event)
    }

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    Set<String> getAllHooks() {
        HookRegistry.INSTANCE.getAllHooks()
    }

}
