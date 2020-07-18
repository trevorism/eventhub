package com.trevorism.event.service

import org.junit.Test

class PublisherRegistryTest {

    @Test
    void testGetPublisher() {
        def publisher = new PublisherRegistry().getPublisher("test")
        assert publisher
        publisher.shutdown()
    }

    @Test
    void testRegisterPublisher() {
        def publisher =  new PublisherRegistry().registerPublisher("test")
        assert publisher
        publisher.shutdown()
    }
}
