package com.trevorism.event.webapi.controller

import com.trevorism.event.model.Subscriber
import com.trevorism.event.service.SubscriptionService
import com.trevorism.event.service.TopicService

import javax.ws.rs.*
import javax.ws.rs.core.MediaType

/**
 * @author tbrooks
 */
@Path("/admin")
class AdminController {

    TopicService topicService = new TopicService()
    SubscriptionService subscriptionService = new SubscriptionService()

    @GET
    @Path("topic")
    @Produces(MediaType.APPLICATION_JSON)
    List<String> getAllTopics(){
        topicService.getAllTopics()
    }

    @GET
    @Path("topic/{topicName}")
    @Produces(MediaType.APPLICATION_JSON)
    String getTopic(@PathParam("topicName") String topicName){
        topicService.getTopic(topicName)
    }

    @POST
    @Path("topic")
    @Consumes(MediaType.APPLICATION_JSON)
    void createTopic(String topic){
        topicService.createTopic(topic)
    }

    @DELETE
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
    @Path("subscription")
    @Consumes(MediaType.APPLICATION_JSON)
    void createSubscription(Subscriber subscriber){
        subscriptionService.createSubscription(subscriber)
    }

    @GET
    @Path("subscription/{subscriptionId}")
    @Produces(MediaType.APPLICATION_JSON)
    String getSubscription(@PathParam("subscriptionId") String subscriptionId){
        subscriptionService.getSubscription(subscriptionId)
    }

    @DELETE
    @Path("subscription/{subscriptionId}")
    @Produces(MediaType.APPLICATION_JSON)
    String deleteSubscription(@PathParam("subscriptionId") String subscriptionId){
        subscriptionService.deleteSubscription(subscriptionId)
    }
}
