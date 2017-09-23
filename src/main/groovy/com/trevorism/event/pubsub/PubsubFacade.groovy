package com.trevorism.event.pubsub

import com.google.api.services.pubsub.model.PublishRequest
import com.google.api.services.pubsub.model.Subscription
import com.google.api.services.pubsub.model.Topic

/**
 * @author tbrooks
 */
interface PubsubFacade {

    def publish(String topic, PublishRequest content)

    def createSubscription(String name, Subscription content)
    def listSubscriptions(String project)
    def getSubscription(String subscription)
    def deleteSubscription(String subscription)

    def createTopic(String name, Topic content)
    def listTopics(String project)
    def getTopic(String topic)
    def deleteTopic(String topic)
}
