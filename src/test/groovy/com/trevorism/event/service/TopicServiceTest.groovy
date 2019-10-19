package com.trevorism.event.service

import com.google.pubsub.v1.ProjectTopicName
import com.google.pubsub.v1.Topic
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * @author tbrooks
 */
class TopicServiceTest {

    public static final String UNIT_TEST_TOPIC_NAME = "unittest"
    private final TopicService topicService
    boolean alreadyCreated = false

    TopicServiceTest() {
        topicService = new TopicService()
        mockActualCalls()
    }

    private void mockActualCalls() {
        topicService.topicAdminClient = [
                createTopic: { name ->
                    if (!alreadyCreated) {
                        alreadyCreated = !alreadyCreated
                    } else {
                        throw new Exception()
                    }

                },
                deleteTopic: { name ->
                    if (name.toString().contains(UNIT_TEST_TOPIC_NAME)) {
                        return true
                    }
                    throw new Exception()
                },
                listTopics : { request -> new MockIterateAll() },
                getTopic   : { name -> new MockName(name) }]
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
        assert topicService.getAllTopics()
    }

    @Test
    void testGetTopic() {
        assert "projects/trevorism-eventhub/topics/unittest" == topicService.getTopic(UNIT_TEST_TOPIC_NAME)
    }

    @Test
    void testDeleteTopic() {
        assert topicService.deleteTopic(UNIT_TEST_TOPIC_NAME)
        assert !topicService.deleteTopic("fffzzz")
    }

    class MockName {
        def name

        MockName(ProjectTopicName name) {
            this.name = name.toString()
        }
    }

    class MockIterateAll {
        def iterateAll() {
            return [new Topic()]
        }
    }
}

