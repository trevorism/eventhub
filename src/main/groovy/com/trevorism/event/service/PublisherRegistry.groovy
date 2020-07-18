package com.trevorism.event.service

import com.google.cloud.pubsub.v1.Publisher
import com.google.pubsub.v1.ProjectTopicName

class PublisherRegistry {

    Map registry = [:]

    Publisher getPublisher(String topicName) {
        def publisher = registry.get(topicName)
        if (!publisher) {
            return registerPublisher(topicName)
        }
        return publisher
    }

    Publisher registerPublisher(String topicName) {
        ProjectTopicName topic = ProjectTopicName.of(EventService.PROJECT_ID, topicName)
        def publisher = Publisher.newBuilder(topic).build()
        registry.put(topicName, publisher)
        return publisher
    }

    void shutdown() {
        registry.each { k, v ->
            v.shutdown()
        }
    }

}
