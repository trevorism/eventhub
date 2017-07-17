package com.trevorism.event.hook

/**
 * @author tbrooks
 */
interface Hook {

    String getName()
    void performAction(def data)
}