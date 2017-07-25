package com.trevorism.event.service

import com.google.api.services.pubsub.Pubsub
import com.google.api.services.pubsub.model.PushConfig
import com.google.api.services.pubsub.model.Subscription
import com.trevorism.event.model.Subscriber

/**
 * @author tbrooks
 */
class SubscriptionService {

    private Pubsub pubsub = PubsubProvider.INSTANCE.get()

    boolean createSubscription(Subscriber subscriber){
        try{
            Subscription subscription = createSubscriptionConfig(subscriber)
            def response = pubsub.projects().subscriptions().create("projects/$PubsubProvider.PROJECT/subscriptions/${subscriber.name}", subscription)
            response.execute()
        }catch (Exception ignored){
            return false
        }
        return true
    }

    List<Subscriber> getAllSubscriptions(){
        def response = pubsub.projects().subscriptions().list("projects/$PubsubProvider.PROJECT").execute()
        response.getSubscriptions().collect { def subscription ->
            Subscriber subscriber = createSubscriber(subscription)
            return subscriber
        }
    }

    Subscriber getSubscription(String subscriptionId) {
        def subscription = pubsub.projects().subscriptions().get("projects/$PubsubProvider.PROJECT/subscriptions/${subscriptionId}").execute()
        return createSubscriber(subscription)
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

    private static Subscription createSubscriptionConfig(Subscriber subscriber) {
        PushConfig pushConfig = new PushConfig()
        pushConfig.setPushEndpoint(subscriber.url)

        Subscription subscription = new Subscription()
        subscription.setName(subscriber.name)
        subscription.setTopic("projects/trevorism-eventhub/topics/$subscriber.topic")
        subscription.setPushConfig(pushConfig)
        return subscription
    }

    private Subscriber createSubscriber(Subscription subscription) {
        Subscriber subscriber = new Subscriber()
        subscriber.ackDeadlineSeconds = subscription.getAckDeadlineSeconds()
        subscriber.name = subscription.getName().substring("projects/trevorism-eventhub/subscriptions/".length())
        subscriber.url = subscription.getPushConfig().getPushEndpoint()
        subscriber.topic = subscription.getTopic()
        subscriber
    }


}
