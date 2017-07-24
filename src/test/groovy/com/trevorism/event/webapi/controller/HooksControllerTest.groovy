package com.trevorism.event.webapi.controller

import com.trevorism.event.hook.Hook
import com.trevorism.event.hook.HookRegistry
import org.junit.Test

/**
 * @author tbrooks
 */
class HooksControllerTest {
    @Test
    void testInvokeHook() {
        HookController hooksController = new HookController()
        def data = ["trevor":"brooks", "age":33]
        hooksController.invokeHook("test", data)

        Hook testHook = HookRegistry.INSTANCE.getHook("test")

        assert testHook.testData == data


    }
}
