package com.trevorism.event.webapi.controller

import com.trevorism.event.model.Subscriber
import com.trevorism.event.service.SubscriptionService
import com.trevorism.event.service.TopicService
import com.trevorism.secure.Secure

import javax.ws.rs.*
import javax.ws.rs.core.MediaType

/**
 * @author tbrooks
 */
@Path("/admin")
class AdminController {

    private TopicService topicService = new TopicService()
    private SubscriptionService subscriptionService = new SubscriptionService()

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<String> getAdminEndpoints(){
        ["topic","subscription"]
    }

    @GET
    @Path("topic")
    @Produces(MediaType.APPLICATION_JSON)
    List<String> getAllTopics(){
        topicService.getAllTopics()
    }

    @GET
    @Secure
    @Path("topic/{topicName}")
    @Produces(MediaType.APPLICATION_JSON)
    String getTopic(@PathParam("topicName") String topicName){
        topicService.getTopic(topicName)
    }

    @POST
    @Secure
    @Path("topic")
    @Consumes(MediaType.APPLICATION_JSON)
    boolean createTopic(String topic){
        topicService.createTopic(topic)
    }

    @DELETE
    @Secure
    @Path("topic/{topicName}")
    @Produces(MediaType.APPLICATION_JSON)
    String deleteTopic(@PathParam("topicName") String topicName){
        topicService.deleteTopic(topicName)
    }

    @GET
    @Path("subscription")
    @Produces(MediaType.APPLICATION_JSON)
    List<Subscriber> getSubscriptions(){
        subscriptionService.getAllSubscriptions()
    }

    @POST
    @Secure
    @Path("subscription")
    @Consumes(MediaType.APPLICATION_JSON)
    boolean createSubscription(Subscriber subscriber){
        subscriptionService.createSubscription(subscriber)
    }

    @GET
    @Secure
    @Path("subscription/{subscriptionId}")
    @Produces(MediaType.APPLICATION_JSON)
    String getSubscription(@PathParam("subscriptionId") String subscriptionId){
        subscriptionService.getSubscription(subscriptionId)
    }

    @DELETE
    @Secure
    @Path("subscription/{subscriptionId}")
    @Produces(MediaType.APPLICATION_JSON)
    String deleteSubscription(@PathParam("subscriptionId") String subscriptionId){
        subscriptionService.deleteSubscription(subscriptionId)
    }
}
