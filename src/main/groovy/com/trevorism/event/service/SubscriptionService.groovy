package com.trevorism.event.service


import com.google.cloud.pubsub.v1.SubscriptionAdminClient
import com.google.pubsub.v1.*
import com.trevorism.event.model.Subscriber

import java.util.logging.Logger

/**
 * @author tbrooks
 */
class SubscriptionService {

    private def subscriptionAdminClient = SubscriptionAdminClient.create()
    private static final Logger log = Logger.getLogger(SubscriptionService.class.name)

    boolean createSubscription(Subscriber subscriber) {
        try {
            ProjectTopicName topicName = ProjectTopicName.of(EventService.PROJECT_ID, subscriber.topic)
            ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(EventService.PROJECT_ID, subscriber.name)
            PushConfig pushConfig = PushConfig.newBuilder().setPushEndpoint(subscriber.url).build()
            return subscriptionAdminClient.createSubscription(subscriptionName, topicName, pushConfig, Integer.valueOf(subscriber.ackDeadlineSeconds))
        } catch (Exception e) {
            log.warning("Failed to create subscription: ${e.message}")
            return false
        }
    }

    List<Subscriber> getAllSubscriptions() {
        ListSubscriptionsRequest listSubscriptionsRequest = ListSubscriptionsRequest.newBuilder().setProject(ProjectName.format(EventService.PROJECT_ID)).build()
        List<Subscriber> subscribers = []
        for (Subscription subscription : subscriptionAdminClient.listSubscriptions(listSubscriptionsRequest).iterateAll()) {
            subscribers << createSubscriber(subscription)
        }
        return subscribers
    }

    Subscriber getSubscription(String subscriptionId) {
        try {
            ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(EventService.PROJECT_ID, subscriptionId)
            def subscription = subscriptionAdminClient.getSubscription(subscriptionName)
            return createSubscriber(subscription)
        } catch (Exception e) {
            log.warning("Unable to retrieve subscriptionId: ${subscriptionId} because ${e.message}")
            return null
        }
    }

    boolean deleteSubscription(String subscription) {
        if (!getSubscription(subscription)) {
            return false
        }
        ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(EventService.PROJECT_ID, subscription)
        subscriptionAdminClient.deleteSubscription(subscriptionName)
        return true
    }

    private static Subscriber createSubscriber(def subscription) {
        if (!subscription)
            return null

        Subscriber subscriber = new Subscriber()
        subscriber.ackDeadlineSeconds = subscription.getAckDeadlineSeconds()
        subscriber.name = subscription.name.substring("projects/${EventService.PROJECT_ID}/subscriptions/".length())
        subscriber.url = subscription.getPushConfig().pushEndpoint
        subscriber.topic = subscription.topic.substring("projects/${EventService.PROJECT_ID}/topics/".length())
        subscriber
    }


}
