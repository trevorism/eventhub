package com.trevorism.event.pubsub

import com.google.api.services.pubsub.Pubsub
import com.google.api.services.pubsub.model.PublishRequest
import com.google.api.services.pubsub.model.Subscription
import com.google.api.services.pubsub.model.Topic
import com.trevorism.event.service.PubsubProvider

/**
 * @author tbrooks
 */
class GaePubsubFacade implements PubsubFacade {


    @Override
    def publish(String topic, PublishRequest content) {
        Pubsub pubsub = PubsubProvider.INSTANCE.get()
        pubsub.projects().topics().publish(topic, content)
    }

    @Override
    def createSubscription(String name, Subscription content) {
        Pubsub pubsub = PubsubProvider.INSTANCE.get()
        pubsub.projects().subscriptions().create(name, content)
    }

    @Override
    def listSubscriptions(String project) {
        Pubsub pubsub = PubsubProvider.INSTANCE.get()
        pubsub.projects().subscriptions().list(project)
    }

    @Override
    def getSubscription(String subscription) {
        Pubsub pubsub = PubsubProvider.INSTANCE.get()
        pubsub.projects().subscriptions().get(subscription)
    }

    @Override
    def deleteSubscription(String subscription) {
        Pubsub pubsub = PubsubProvider.INSTANCE.get()
        pubsub.projects().subscriptions().delete(subscription)
    }

    @Override
    def createTopic(String name, Topic content) {
        Pubsub pubsub = PubsubProvider.INSTANCE.get()
        pubsub.projects().topics().create(name, content)
    }

    @Override
    def listTopics(String project) {
        Pubsub pubsub = PubsubProvider.INSTANCE.get()
        pubsub.projects().topics().list(project)
    }

    @Override
    def getTopic(String topic) {
        Pubsub pubsub = PubsubProvider.INSTANCE.get()
        pubsub.projects().topics().get(topic)
    }

    @Override
    def deleteTopic(String topic) {
        Pubsub pubsub = PubsubProvider.INSTANCE.get()
        pubsub.projects().topics().delete(topic)
    }
}
