package com.trevorism.event.pubsub

import com.google.api.services.pubsub.model.PublishRequest
import com.google.api.services.pubsub.model.Subscription
import com.google.api.services.pubsub.model.Topic

/**
 * @author tbrooks
 */
class TestPubsubFacade implements PubsubFacade{

    static def allSubscriptions = [:]
    static def allTopics = [:]

    @Override
    def publish(String topic, PublishRequest content) {
        return [execute:{[messageIds:["0"]]}]
    }

    @Override
    def createSubscription(String name, Subscription content) {
        return [execute:{
            if(allSubscriptions.containsKey(name))
                throw new RuntimeException("Unable to store this topic")
            else {
                content.setName("$name")
                content.setAckDeadlineSeconds(10)
                allSubscriptions[name] = content
            }
        }]
    }

    @Override
    def listSubscriptions(String project) {
        return [execute:{
            [getSubscriptions:{
                allSubscriptions.collect {k,v -> v}
            }]}]
    }

    @Override
    def getSubscription(String subscription) {
        [execute:{allSubscriptions[subscription]}]
    }

    @Override
    def deleteSubscription(String subscription) {
        return [execute:{
            if(!allSubscriptions.containsKey(subscription)){
                throw new RuntimeException("Unable to delete key")
            }
            else{
                allSubscriptions.remove(subscription)
            }
        }]
    }

    @Override
    def createTopic(String name, Topic content) {
        return [execute:{
            if(allTopics.containsKey(name))
                throw new RuntimeException("Unable to store this topic")
            else {
                content.setName("$name")
                allTopics[name] = content
            }
        }]
    }

    @Override
    def listTopics(String project) {
        return [execute:{
            [getTopics:{
                    allTopics.collect {k,v -> v}
                }]}]
    }

    @Override
    def getTopic(String topic) {
        [execute:{allTopics[topic]}]
    }

    @Override
    def deleteTopic(String topic) {
        return [execute:{
            if(!allTopics.containsKey(topic)){
                throw new RuntimeException("Unable to delete key")
            }
            else{
                allTopics.remove(topic)
            }
        }]
    }
}
