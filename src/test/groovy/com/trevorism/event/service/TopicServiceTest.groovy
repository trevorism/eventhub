package com.trevorism.event.service

import com.trevorism.event.pubsub.PubsubFacade
import com.trevorism.event.pubsub.TestPubsubFacade
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * @author tbrooks
 */
class TopicServiceTest {

    public static final String UNIT_TEST_TOPIC_NAME = "unittest"
    private final TopicService topicService

    TopicServiceTest(){
        topicService = new TopicService()

        PubsubFacade facade = new TestPubsubFacade()
        topicService.facade = facade
    }

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
