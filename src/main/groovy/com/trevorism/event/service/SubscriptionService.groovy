package com.trevorism.event.service

import com.google.api.services.pubsub.Pubsub
import com.google.api.services.pubsub.model.PushConfig
import com.google.api.services.pubsub.model.Subscription

/**
 * @author tbrooks
 */
class SubscriptionService {

    private Pubsub pubsub = PubsubProvider.INSTANCE.get()

    boolean createSubscription(String topic, String subscriptionName, String pushEndpoint){
        try{
            Subscription subscription = createSubscriptionConfig(topic, subscriptionName, pushEndpoint)

            def response = pubsub.projects().subscriptions().create("projects/$PubsubProvider.PROJECT/topics/${topic}", subscription)
            response.execute()
        }catch (Exception ignored){
            return false
        }
        return true
    }

    List<String> getAllSubscriptions(String topic){
        def response = pubsub.projects().subscriptions().list("projects/$PubsubProvider.PROJECT/topics/${topic}").execute()
        response.getSubscriptions().collect { def subscription ->
            subscription.substring("projects/trevorism-eventhub/subscriptions/".length())
        }
    }

    String getSubscription(String subscriptionId) {
        def response = pubsub.projects().subscriptions().get("projects/$PubsubProvider.PROJECT/subscriptions/${subscriptionId}").execute()
        return response
    }

    boolean deleteSubscription(String subscription){
        try{
            def response = pubsub.projects().subscriptions().delete("projects/$PubsubProvider.PROJECT/subscriptions/${subscription}")
            response.execute()
        }catch (Exception ignored){
            return false
        }
        return true

    }

    private static Subscription createSubscriptionConfig(String topic, String subscriptionName, String pushEndpoint) {
        PushConfig pushConfig = new PushConfig()
        pushConfig.setPushEndpoint(pushEndpoint)

        Subscription subscription = new Subscription()
        subscription.setName(subscriptionName)
        subscription.setTopic(topic)
        subscription.setPushConfig(pushConfig)
        return subscription
    }


}
