package com.trevorism.event.hook

import com.trevorism.event.model.ReceivedEvent
import org.junit.Test

/**
 * @author tbrooks
 */
class TestResultEmailHookTest {

    @Test
    void testCreateEmailJson(){
        ReceivedEvent event = new ReceivedEvent()
        event.message = new ReceivedEvent.Message()
        event.message.data = [feature:"my feature"]

        Map emailMap = TestResultEmailHook.buildEmail(event)
        assert emailMap.subject == "Test for my feature failed"
    }
}
