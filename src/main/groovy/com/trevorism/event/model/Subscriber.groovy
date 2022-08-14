package com.trevorism.event.model

import groovy.transform.Canonical
import io.swagger.annotations.ApiModelProperty

/**
 * @author tbrooks
 */
@Canonical
class Subscriber {

    @ApiModelProperty(value = "An unique name for the subscriber", dataType = "string")
    String name
    @ApiModelProperty(value = "The topic on which to subscribe", dataType = "string")
    String topic
    @ApiModelProperty(value = "The url to route the event. Must be HTTPS", dataType = "string")
    String url
    @ApiModelProperty(value = "String value for acknowledgement deadline", dataType = "string")
    String ackDeadlineSeconds = "90"

}
