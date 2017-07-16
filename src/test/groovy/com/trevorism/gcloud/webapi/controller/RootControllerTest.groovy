package com.trevorism.gcloud.webapi.controller

import com.trevorism.gcloud.LocalAppEngineTestBase
import org.junit.Test

/**
 * @author tbrooks
 */
class RootControllerTest extends LocalAppEngineTestBase{

    @Test
    void testRootControllerDefault(){
        RootController rootController = new RootController()
        assert rootController.endpoints.contains("ping")
    }

    @Test
    void testRootControllerPing(){
        RootController rootController = new RootController()
        assert "pong" == rootController.ping()
    }
}
