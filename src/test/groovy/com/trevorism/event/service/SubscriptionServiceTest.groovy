package com.trevorism.event.service

import com.trevorism.event.model.Subscriber
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * @author tbrooks
 */
class SubscriptionServiceTest {

    private static final String UNIT_TEST_SUBSCRIPTION_NAME = "test1"
    private static final String UNIT_TEST_TOPIC_NAME = "unittest"

    private final SubscriptionService subscriptionService
    private boolean alreadyCreated = false

    SubscriptionServiceTest() {
        subscriptionService = new SubscriptionService()
        mockActualCalls()
    }

    private void mockActualCalls() {
        subscriptionService.subscriptionAdminClient.shutdown()
        subscriptionService.subscriptionAdminClient = [
                createSubscription: { n, t, p, a ->
                    if (!alreadyCreated) {
                        alreadyCreated = !alreadyCreated
                    } else {
                        throw new Exception()
                    }

                },
                deleteSubscription: { def name ->
                    if (name.toString().contains(UNIT_TEST_SUBSCRIPTION_NAME)) {
                        alreadyCreated = false
                        return true
                    }
                    return false
                },
                listSubscriptions : { request -> new MockIterateAll() },
                getSubscription   : { name -> new MockSubscription(name) }]
    }


    @Before
    void setUp() {
        Subscriber subscriber = new Subscriber(UNIT_TEST_SUBSCRIPTION_NAME, UNIT_TEST_TOPIC_NAME, "https://trevorism-eventhub.appspot.com/hook/test")
        subscriptionService.createSubscription(subscriber)
    }

    @After
    void tearDown() {
        subscriptionService.deleteSubscription(UNIT_TEST_SUBSCRIPTION_NAME)
    }

    @Test
    void testCreateSubscription() {
        Subscriber subscriber = new Subscriber(UNIT_TEST_SUBSCRIPTION_NAME, UNIT_TEST_TOPIC_NAME, "https://trevorism-eventhub.appspot.com/hook/test")
        assert !subscriptionService.createSubscription(subscriber)
    }

    @Test
    void testGetAllSubscriptions() {
        def subscribers = subscriptionService.getAllSubscriptions()
        assert subscribers
    }

    @Test
    void testGetSubscription() {
        def subscriber = subscriptionService.getSubscription(UNIT_TEST_SUBSCRIPTION_NAME)
        assert subscriber
        assert subscriber.name == UNIT_TEST_SUBSCRIPTION_NAME
        assert subscriber.topic == UNIT_TEST_TOPIC_NAME
        assert subscriber.url == "https://trevorism-eventhub.appspot.com/hook/test"
        assert subscriber.ackDeadlineSeconds == "10"
    }

    @Test
    void testDeleteSubscription() {
        assert subscriptionService.deleteSubscription(UNIT_TEST_SUBSCRIPTION_NAME)
        assert !subscriptionService.deleteSubscription("asdfdsaf")
    }

    @Test
    void testUpdateSubscription() {
        Subscriber subscriber = new Subscriber(UNIT_TEST_SUBSCRIPTION_NAME, UNIT_TEST_TOPIC_NAME, "https://trevorism-eventhub.appspot.com/hook/test", "60")

        def result = subscriptionService.updateSubscription(UNIT_TEST_SUBSCRIPTION_NAME, subscriber)
        assert result.ackDeadlineSeconds == "60"
    }

    class MockIterateAll {
        def iterateAll() {
            return [[name_: "projects/${EventService.PROJECT_ID}/subscriptions/${UNIT_TEST_SUBSCRIPTION_NAME}".toString(),
                    topic_: "projects/${EventService.PROJECT_ID}/topics/${UNIT_TEST_TOPIC_NAME}".toString()
                    ]]
        }
    }

    class MockSubscription {

        def ackDeadlineSeconds
        def name
        def pushEndpoint
        def topic

        MockSubscription(def subName){
            if(!subName.toString().contains(UNIT_TEST_SUBSCRIPTION_NAME))
                throw new Exception()
            name = subName.toString()
            ackDeadlineSeconds = 10
            pushEndpoint = "https://trevorism-eventhub.appspot.com/hook/test"
            topic = "projects/${EventService.PROJECT_ID}/topics/${UNIT_TEST_TOPIC_NAME}"
        }

        def getPushConfig(){
            return [pushEndpoint: pushEndpoint]
        }
    }

    /*
            subscriber.ackDeadlineSeconds = subscription.getAckDeadlineSeconds()
        subscriber.name = subscription.name.substring("projects/${EventService.PROJECT_ID}/subscriptions/".length())
        subscriber.url = subscription.getPushConfig().pushEndpoint
        subscriber.topic = subscription.topic.substring("projects/${EventService.PROJECT_ID}/topics/".length())
     */

}
