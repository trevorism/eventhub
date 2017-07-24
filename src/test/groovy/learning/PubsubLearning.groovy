package learning

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.api.services.pubsub.Pubsub
import com.google.api.services.pubsub.model.PublishRequest
import com.google.api.services.pubsub.model.PubsubMessage
import com.google.api.services.pubsub.model.Subscription
import com.google.appengine.repackaged.com.google.protobuf.ByteString
import com.trevorism.event.service.PubsubProvider
import com.trevorism.event.service.TopicService
import org.junit.Test

/**
 * @author tbrooks
 */
class PubsubLearning {

    static String PROJECT = "trevorism-eventhub"

    TopicService topicService = new TopicService()
    private Pubsub pubsub = PubsubProvider.create()

    @Test
    void listTopics() {
        def data = ["name":"Sean", "age":19, "address":["street":"Miners","city":"dover"]]
        ObjectMapper objectMapper = new ObjectMapper()


        PubsubMessage pubsubMessage = new PubsubMessage()
        String json = objectMapper.writeValueAsString(data)
        pubsubMessage.encodeData(ByteString.copyFromUtf8(json).toByteArray())

        PublishRequest publishRequest = new PublishRequest()
        publishRequest.setMessages([pubsubMessage])

        Pubsub.Projects.Topics.Publish publish = pubsub.projects().topics().publish("projects/$PubsubProvider.PROJECT/topics/event", publishRequest)
        println publish

        println publish.execute()


    }



}
