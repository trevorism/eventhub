package com.trevorism.event.hook

/**
 * @author tbrooks
 */
class TestHook implements Hook{

    def testData

    @Override
    String getName() {
        "test"
    }

    @Override
    void performAction(Object data) {
        this.testData = data
    }
}
