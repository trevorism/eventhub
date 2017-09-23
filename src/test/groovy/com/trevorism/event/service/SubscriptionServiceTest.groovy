package com.trevorism.event.service

import com.trevorism.event.model.Subscriber
import com.trevorism.event.pubsub.PubsubFacade
import com.trevorism.event.pubsub.TestPubsubFacade
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * @author tbrooks
 */
class SubscriptionServiceTest {

    private static final String UNIT_TEST_TOPIC_NAME = "unittest"

    private final TopicService topicService
    private final SubscriptionService subscriptionService

    SubscriptionServiceTest(){
        topicService = new TopicService()
        subscriptionService = new SubscriptionService()

        PubsubFacade facade = new TestPubsubFacade()
        topicService.facade = facade
        subscriptionService.facade = facade
    }



    @Before
    void setUp() {
        topicService.createTopic(UNIT_TEST_TOPIC_NAME)
        Subscriber subscriber = new Subscriber("test1", UNIT_TEST_TOPIC_NAME, "https://trevorism-eventhub.appspot.com/hook/test")
        subscriptionService.createSubscription(subscriber)
    }

    @After
    void tearDown() {
        subscriptionService.deleteSubscription("test1")
        topicService.deleteTopic(UNIT_TEST_TOPIC_NAME)
    }

    @Test
    void testCreateSubscription() {
        Subscriber subscriber = new Subscriber("test1", UNIT_TEST_TOPIC_NAME, "https://trevorism-eventhub.appspot.com/hook/test")
        assert !subscriptionService.createSubscription(subscriber)

    }

    @Test
    void testGetAllSubscriptions() {
        Thread.sleep(1000)
        def subscribers = subscriptionService.getAllSubscriptions()
        assert subscribers
    }

    @Test
    void testGetSubscription() {
        def subscriber = subscriptionService.getSubscription("test1")
        assert subscriber
        assert subscriber.name == "test1"
        assert subscriber.url == "https://trevorism-eventhub.appspot.com/hook/test"
        assert subscriber.ackDeadlineSeconds == "10"
    }

    @Test
    void testDeleteSubscription() {
        assert subscriptionService.deleteSubscription("test1")
    }
}
