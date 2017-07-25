package com.trevorism.event.service

import com.google.api.services.pubsub.Pubsub
import com.google.api.services.pubsub.model.Topic

/**
 * @author tbrooks
 */
class TopicService {

    private Pubsub pubsub = PubsubProvider.INSTANCE.get()

    boolean createTopic(String topic){
        try{
            def response = pubsub.projects().topics().create("projects/$PubsubProvider.PROJECT/topics/${topic}", new Topic())
            response.execute()
        }catch (Exception ignored){
            return false
        }
        return true
    }

    List<String> getAllTopics(){
        def response = pubsub.projects().topics().list("projects/$PubsubProvider.PROJECT").execute()
        return response.getTopics().collect{ def topic ->
            topic.get("name").substring("projects/$PubsubProvider.PROJECT/topics/".length())
        }
    }

    String getTopic(String topic){
        def response = pubsub.projects().topics().get("projects/$PubsubProvider.PROJECT/topics/$topic").execute()
        return response.get("name")
    }

    boolean deleteTopic(String topic){
        try{
            def response = pubsub.projects().topics().delete("projects/$PubsubProvider.PROJECT/topics/${topic}")
            response.execute()
        }catch (Exception ignored){
            return false
        }
        return true

    }

}
