package com.trevorism.event.service

import com.google.cloud.pubsub.v1.TopicAdminClient
import com.google.pubsub.v1.ListTopicsRequest
import com.google.pubsub.v1.ProjectName
import com.google.pubsub.v1.ProjectTopicName
import com.google.pubsub.v1.Topic

import java.util.logging.Logger

/**
 * @author tbrooks
 */
class TopicService {

    private def topicAdminClient = TopicAdminClient.create()
    private static final Logger log = Logger.getLogger(TopicService.class.name)

    boolean createTopic(String topicId) {
        try {
            ProjectTopicName topicName = ProjectTopicName.of(EventService.PROJECT_ID, topicId)
            Topic topic = topicAdminClient.createTopic(topicName)
            return topic
        } catch (Exception e) {
            log.warning("Failed to create topic: ${e.message}")
            return false
        }
    }

    List<String> getAllTopics() {
        ListTopicsRequest listTopicsRequest = ListTopicsRequest.newBuilder().setProject(ProjectName.format(EventService.PROJECT_ID)).build()
        List<String> topics = []
        for (Topic topic : topicAdminClient.listTopics(listTopicsRequest).iterateAll()) {
            topics << topic.name.substring("projects/${EventService.PROJECT_ID}/topics/".length())
        }
        return topics
    }

    String getTopic(String topic) {
        ProjectTopicName topicName = ProjectTopicName.of(EventService.PROJECT_ID, topic)
        return topicAdminClient.getTopic(topicName)?.name
    }

    boolean deleteTopic(String topic) {
        try {
            ProjectTopicName topicName = ProjectTopicName.of(EventService.PROJECT_ID, topic)
            topicAdminClient.deleteTopic(topicName)
            return true
        } catch (Exception e) {
            log.warning("Failed to delete topic: ${e.message}")
            return false
        }
    }

}
