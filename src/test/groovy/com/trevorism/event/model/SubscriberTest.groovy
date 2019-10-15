package com.trevorism.event.model

import org.junit.Test

/**
 * @author tbrooks
 */
class SubscriberTest {

    @Test
    void testEqualsAndHashcode(){
        Subscriber subscriber1 = new Subscriber("red")
        Subscriber subscriber2 = new Subscriber("red")
        Subscriber subscriber3 = new Subscriber("blue")

        assert subscriber1.equals(subscriber2)
        assert subscriber2.hashCode() == subscriber1.hashCode()
        assert subscriber1.toString() == subscriber2.toString()

        assert !subscriber1.equals(subscriber3)
        assert subscriber3.hashCode() != subscriber1.hashCode()
        assert subscriber3.toString() != subscriber1.toString()

    }
}
