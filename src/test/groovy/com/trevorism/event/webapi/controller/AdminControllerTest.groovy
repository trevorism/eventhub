package com.trevorism.event.webapi.controller

import com.trevorism.event.model.Subscriber
import com.trevorism.event.pubsub.TestPubsubFacade
import com.trevorism.event.service.SubscriptionService
import com.trevorism.event.service.TopicService
import org.junit.Test

/**
 * @author tbrooks
 */
class AdminControllerTest {

    AdminController adminController

    AdminControllerTest(){
        adminController = new AdminController()
        def topicService = new TopicService()
        topicService.facade = new TestPubsubFacade()
        def subscriptionService = new SubscriptionService()
        subscriptionService.facade = topicService.facade

        adminController.topicService = topicService
        adminController.subscriptionService = subscriptionService
    }

    @Test
    void testGetAllTopics() {
        assert !adminController.getAllTopics()
    }

    @Test
    void testCreateGetDeleteTopic() {
        assert !adminController.getTopic("myTopic")
        adminController.createTopic("myTopic")
        assert adminController.getTopic("myTopic")
        assert adminController.getAllTopics()
        adminController.deleteTopic("myTopic")
        assert !adminController.getTopic("myTopic")
    }

    @Test
    void testGetSubscriptions() {
        assert !adminController.getSubscriptions()
    }

    @Test
    void testCreateGetDeleteSubscription() {
        assert !adminController.getSubscription("mySubscription")
        adminController.createSubscription(new Subscriber(name: "mySubscription"))
        assert adminController.getSubscription("mySubscription")
        assert adminController.getSubscriptions()
        adminController.deleteSubscription("mySubscription")
        assert !adminController.getSubscription("mySubscription")

    }

}
