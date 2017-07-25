package com.trevorism.event.service

import org.junit.Test

/**
 * @author tbrooks
 */
class PubsubProviderTest {

    @Test
    void testGet() {
        assert PubsubProvider.INSTANCE.get()
    }
}
