package com.trevorism.event.service

import org.junit.Test

class PublisherRegistryTest {

    @Test
    void testGetPublisher() {
        assert new PublisherRegistry().getPublisher("test")
    }

    @Test
    void testRegisterPublisher() {
        assert new PublisherRegistry().registerPublisher("test")
    }
}
