package com.trevorism.event.webapi.controller

import org.junit.Test

/**
 * @author tbrooks
 */
class AdminControllerTest {

    @Test
    void testGetAdminEndpoints() {
        AdminController adminController = new AdminController()
        def endpoints = adminController.getAdminEndpoints()
        assert endpoints
        assert endpoints.contains("topic")
        assert endpoints.contains("subscription")

    }
}
