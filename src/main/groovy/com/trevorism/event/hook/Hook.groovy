package com.trevorism.event.hook

import com.trevorism.event.model.ReceivedEvent

/**
 * @author tbrooks
 */
interface Hook {

    String getName()
    void performAction(ReceivedEvent event)
}