package com.trevorism.event.hook

import com.google.gson.Gson
import com.trevorism.event.model.ReceivedEvent
import com.trevorism.http.HttpClient
import com.trevorism.http.JsonHttpClient

/**
 * @author tbrooks
 */
class TestResultEmailHook implements Hook{

    HttpClient client = new JsonHttpClient()

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
        String json = createEmailJson(event)
        client.post('https://email-dot-trevorism-gcloud.appspot.com/mail/', json)
    }

    private String createEmailJson(ReceivedEvent event) {
        def email = buildEmail(event)
        Gson gson = new Gson()
        String json = gson.toJson(email)
        json
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
