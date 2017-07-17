package com.trevorism.event.hook

import org.junit.Test

/**
 * @author tbrooks
 */


class HookRegistryTest {

    @Test
    void testRegistry(){
        def registry = HookRegistry.INSTANCE.registry

        assert registry
        assert registry["storeevent"] instanceof StoreEventHook
        assert registry["test"] instanceof TestHook
    }
}
