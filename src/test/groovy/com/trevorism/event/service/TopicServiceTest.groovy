package com.trevorism.event.service

import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * @author tbrooks
 */
class TopicServiceTest {

    public static final String UNIT_TEST_TOPIC_NAME = "unittest"
    TopicService topicService = new TopicService()

    @Before
    void setUp() {
        topicService.createTopic(UNIT_TEST_TOPIC_NAME)
    }

    @After
    void tearDown() {
        topicService.deleteTopic(UNIT_TEST_TOPIC_NAME)
    }

    @Test
    void testCreateTopic() {
        assert !topicService.createTopic(UNIT_TEST_TOPIC_NAME)
    }

    @Test
    void testGetAllTopics() {
        Thread.sleep(1000)
        assert topicService.getAllTopics()
    }

    @Test
    void testGetTopic() {
        assert "projects/trevorism-eventhub/topics/unittest" == topicService.getTopic(UNIT_TEST_TOPIC_NAME)
    }

    @Test
    void testDeleteTopic() {
        assert !topicService.deleteTopic("fffzzz")
    }
}
