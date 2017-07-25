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
        assert registry["_store"] instanceof StoreEventHook
    }
}
