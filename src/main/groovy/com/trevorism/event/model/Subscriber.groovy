package com.trevorism.event.model

import groovy.transform.Canonical

/**
 * @author tbrooks
 */
@Canonical
class Subscriber {
    String name
    String topic
    String url
    String ackDeadlineSeconds = "10"

}
