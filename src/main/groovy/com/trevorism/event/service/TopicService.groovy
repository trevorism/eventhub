package com.trevorism.event.service

import com.google.api.services.pubsub.model.Topic
import com.trevorism.event.pubsub.GaePubsubFacade
import com.trevorism.event.pubsub.PubsubFacade

/**
 * @author tbrooks
 */
class TopicService {

    private PubsubFacade facade = new GaePubsubFacade()

    boolean createTopic(String topic){
        try{
            facade.createTopic("projects/$PubsubProvider.PROJECT/topics/${topic}", new Topic()).execute()
        }catch (Exception ignored){
            return false
        }
        return true
    }

    List<String> getAllTopics(){
        def response = facade.listTopics("projects/$PubsubProvider.PROJECT").execute()
        return response.getTopics().collect{ def topic ->
            topic.get("name").substring("projects/$PubsubProvider.PROJECT/topics/".length())
        }
    }

    String getTopic(String topic){
        def response = facade.getTopic("projects/$PubsubProvider.PROJECT/topics/$topic").execute()
        return response?.get("name")
    }

    boolean deleteTopic(String topic){
        try{
            facade.deleteTopic("projects/$PubsubProvider.PROJECT/topics/${topic}").execute()
        }catch (Exception ignored){
            return false
        }
        return true

    }

}
