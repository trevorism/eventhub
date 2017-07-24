package com.trevorism.event.webapi.controller

import com.trevorism.event.hook.Hook
import com.trevorism.event.hook.HookRegistry

import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.core.MediaType

/**
 * @author tbrooks
 */
@Path("/api/hook")
class HookController {

    @POST
    @Path("{name}")
    @Consumes(MediaType.APPLICATION_JSON)
    void invokeHook(@PathParam("name") String name, Map<String, Object> data ){
        Hook hook = HookRegistry.INSTANCE.getHook(name)
        hook.performAction(data)
    }

}
