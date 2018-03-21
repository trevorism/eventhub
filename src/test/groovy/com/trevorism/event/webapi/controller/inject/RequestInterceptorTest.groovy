package com.trevorism.event.webapi.controller.inject

import com.trevorism.event.webapi.controller.AdminController
import org.junit.Test

import javax.ws.rs.container.ResourceInfo
import javax.ws.rs.core.FeatureContext

/**
 * @author tbrooks
 */
class RequestInterceptorTest {

    @Test
    void testConfigure() {
        RequestInterceptor interceptor = new RequestInterceptor()
        boolean registerCalled = false

        ResourceInfo info = [getResourceClass : {return AdminController}] as ResourceInfo
        FeatureContext context = [register : { Class clazz -> registerCalled = true; return null }] as FeatureContext

        interceptor.configure(info, context)
        assert registerCalled

    }
}
