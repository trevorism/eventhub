package com.trevorism.event.service

import com.google.api.services.pubsub.model.PushConfig
import com.google.api.services.pubsub.model.Subscription
import com.trevorism.event.model.Subscriber
import com.trevorism.event.pubsub.GaePubsubFacade
import com.trevorism.event.pubsub.PubsubFacade

/**
 * @author tbrooks
 */
class SubscriptionService {

    private PubsubFacade facade = new GaePubsubFacade()

    boolean createSubscription(Subscriber subscriber){
        try{
            Subscription subscription = createSubscriptionConfig(subscriber)
            def response = facade.createSubscription("projects/$PubsubProvider.PROJECT/subscriptions/${subscriber.name}", subscription)
            response.execute()
        }catch (Exception ignored){
            return false
        }
        return true
    }

    List<Subscriber> getAllSubscriptions(){
        def response = facade.listSubscriptions("projects/$PubsubProvider.PROJECT").execute()
        response.getSubscriptions().collect { def subscription ->
            Subscriber subscriber = createSubscriber(subscription)
            return subscriber
        }
    }

    Subscriber getSubscription(String subscriptionId) {
        Subscription subscription = facade.getSubscription("projects/$PubsubProvider.PROJECT/subscriptions/${subscriptionId}").execute()
        return createSubscriber(subscription)
    }

    boolean deleteSubscription(String subscription){
        try{
            def response = facade.deleteSubscription("projects/$PubsubProvider.PROJECT/subscriptions/${subscription}")
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

    private static Subscriber createSubscriber(Subscription subscription) {
        if(!subscription)
            return null

        Subscriber subscriber = new Subscriber()
        subscriber.ackDeadlineSeconds = subscription.getAckDeadlineSeconds()
        subscriber.name = subscription.getName().substring("projects/$PubsubProvider.PROJECT/subscriptions/".length())
        subscriber.url = subscription.getPushConfig().getPushEndpoint()
        subscriber.topic = subscription.getTopic()
        subscriber
    }


}
