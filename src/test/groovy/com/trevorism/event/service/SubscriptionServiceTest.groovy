package com.trevorism.event.service

import com.trevorism.event.model.Subscriber
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * @author tbrooks
 */
class SubscriptionServiceTest {

    private static final String UNIT_TEST_TOPIC_NAME = "unittest"
    TopicService topicService = new TopicService()
    SubscriptionService subscriptionService = new SubscriptionService()

    @Before
    void setUp() {
        topicService.createTopic(UNIT_TEST_TOPIC_NAME)
        Subscriber subscriber = new Subscriber("test1", UNIT_TEST_TOPIC_NAME, "https://trevorism-eventhub.appspot.com/hook/test")
        subscriptionService.createSubscription(subscriber)
    }

    @After
    void tearDown() {
        topicService.deleteTopic(UNIT_TEST_TOPIC_NAME)
    }

    @Test
    void testCreateSubscription() {
        Subscriber subscriber = new Subscriber("test1", UNIT_TEST_TOPIC_NAME, "https://trevorism-eventhub.appspot.com/hook/test")
        assert !subscriptionService.createSubscription(subscriber)

    }

    @Test
    void testGetAllSubscriptions() {
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
