package com.trevorism.event.webapi.controller

import com.trevorism.event.model.Subscriber
import com.trevorism.event.service.SubscriptionService
import com.trevorism.event.service.TopicService
import com.trevorism.secure.Roles
import com.trevorism.secure.Secure
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation

import javax.ws.rs.*
import javax.ws.rs.core.MediaType

/**
 * @author tbrooks
 */
@Api("Admin Operations")
@Path("/admin")
class AdminController {

    private TopicService topicService = new TopicService()
    private SubscriptionService subscriptionService = new SubscriptionService()

    @ApiOperation(value = "Gets all topics **Secure")
    @GET
    @Path("topic")
    @Produces(MediaType.APPLICATION_JSON)
    @Secure(Roles.SYSTEM)
    List<String> getAllTopics(){
        topicService.getAllTopics()
    }

    @ApiOperation(value = "Gets detailed topic information about {topicName} **Secure")
    @GET
    @Secure(Roles.SYSTEM)
    @Path("topic/{topicName}")
    @Produces(MediaType.APPLICATION_JSON)
    String getTopic(@PathParam("topicName") String topicName){
        topicService.getTopic(topicName)
    }

    @ApiOperation(value = "Create a new topic **Secure")
    @POST
    @Secure(Roles.SYSTEM)
    @Path("topic")
    @Consumes(MediaType.APPLICATION_JSON)
    boolean createTopic(String topic){
        topicService.createTopic(topic)
    }

    @ApiOperation(value = "Delete a topic by name **Secure")
    @DELETE
    @Secure(Roles.SYSTEM)
    @Path("topic/{topicName}")
    @Produces(MediaType.APPLICATION_JSON)
    boolean deleteTopic(@PathParam("topicName") String topicName){
        topicService.deleteTopic(topicName)
    }

    @ApiOperation(value = "Gets all subscriptions **Secure")
    @GET
    @Path("subscription")
    @Produces(MediaType.APPLICATION_JSON)
    @Secure(Roles.SYSTEM)
    List<Subscriber> getSubscriptions(){
        subscriptionService.getAllSubscriptions()
    }

    @ApiOperation(value = "Create a new subscription **Secure")
    @POST
    @Secure(Roles.SYSTEM)
    @Path("subscription")
    @Consumes(MediaType.APPLICATION_JSON)
    boolean createSubscription(Subscriber subscriber){
        subscriptionService.createSubscription(subscriber)
    }

    @ApiOperation(value = "Get detailed information about a subscription **Secure")
    @GET
    @Secure(Roles.SYSTEM)
    @Path("subscription/{subscriptionId}")
    @Produces(MediaType.APPLICATION_JSON)
    Subscriber getSubscription(@PathParam("subscriptionId") String subscriptionId){
        subscriptionService.getSubscription(subscriptionId)
    }

    @ApiOperation(value = "Update a subscription **Secure")
    @PUT
    @Secure(Roles.SYSTEM)
    @Path("subscription/{subscriptionId}")
    @Produces(MediaType.APPLICATION_JSON)
    Subscriber updateSubscription(@PathParam("subscriptionId") String subscriptionId, Subscriber subscriber){
        subscriptionService.updateSubscription(subscriptionId, subscriber)
    }

    @ApiOperation(value = "Delete a subscription **Secure")
    @DELETE
    @Secure(Roles.SYSTEM)
    @Path("subscription/{subscriptionId}")
    @Produces(MediaType.APPLICATION_JSON)
    boolean deleteSubscription(@PathParam("subscriptionId") String subscriptionId){
        subscriptionService.deleteSubscription(subscriptionId)
    }
}
