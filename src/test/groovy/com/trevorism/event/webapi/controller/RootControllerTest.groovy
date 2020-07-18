package com.trevorism.event.webapi.controller


import org.junit.Test

/**
 * @author tbrooks
 */
class RootControllerTest {

    @Test
    void testRootControllerDefault(){
        RootController rootController = new RootController()
        assert rootController.displayHelpLink().contains("/help")
    }

    @Test
    void testRootControllerPing(){
        RootController rootController = new RootController()
        assert "pong" == rootController.ping()
    }
}
