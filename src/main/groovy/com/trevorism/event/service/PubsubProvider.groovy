package com.trevorism.event.service

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.util.Utils
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.services.pubsub.Pubsub
import com.google.api.services.pubsub.PubsubScopes

/**
 * @author tbrooks
 */
class PubsubProvider {

    static final String PROJECT = "trevorism-eventhub"
    static final PubsubProvider INSTANCE = new PubsubProvider()

    private Pubsub pubsub
    private PubsubProvider(){}

    Pubsub get(){
        if(!pubsub)
            pubsub = create()

        return pubsub
    }

    private static Pubsub create() {
        return getClient(Utils.getDefaultTransport(), Utils.getDefaultJsonFactory())
    }

    private static Pubsub getClient(final HttpTransport httpTransport, final JsonFactory jsonFactory) {
        GoogleCredential credential = GoogleCredential.getApplicationDefault()
        if (credential.createScopedRequired()) {
            credential = credential.createScoped(PubsubScopes.all())
        }
        return new Pubsub.Builder(httpTransport, jsonFactory, credential).setApplicationName(PROJECT).build()
    }
}
