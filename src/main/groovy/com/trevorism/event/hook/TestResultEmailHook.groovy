package com.trevorism.event.hook

import com.trevorism.event.model.ReceivedEvent
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder

/**
 * @author tbrooks
 */
class TestResultEmailHook implements Hook{

    private def http = new HTTPBuilder('https://email-dot-trevorism-gcloud.appspot.com/mail/')

    @Override
    String getName() {
        return "testresultemail"
    }

    @Override
    void performAction(ReceivedEvent event) {
        //Only email if the test failed
        if(event.message.data.passing)
            return

        ping()
        def email = buildEmail(event)
        http.post( path: '', body: email, requestContentType: ContentType.JSON )
    }

    private static def buildEmail(ReceivedEvent event) {
        def email = [:]
        email["subject"] = "Test for ${event.message.data.feature} failed"
        email["recipients"] = ["alerts@trevorism.com"]
        email["body"] = buildMessage(event.message.data)
        email
    }

    private static String buildMessage(def data) {
        """Test failed for scenario: ${data.name}

${data?.given}
${data?.when}
${data?.then}

${data?.errorMessage}"""

    }

    private void ping() {
        try {
            new URL("https://email-dot-trevorism-gcloud.appspot.com/ping").text
        } catch (Exception ignored) {
            Thread.sleep(10000)
            new URL("https://email-dot-trevorism-gcloud.appspot.com/ping").text
        }
    }
}
