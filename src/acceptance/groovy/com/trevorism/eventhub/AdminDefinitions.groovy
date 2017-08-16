package com.trevorism.eventhub
/**
 * @author tbrooks
 */

this.metaClass.mixin(cucumber.api.groovy.Hooks)
this.metaClass.mixin(cucumber.api.groovy.EN)

Given(~/^the topic "([^"]*)" does not exist$/) { String topic ->

}

Given(~/^the topic "([^"]*)" already exists or is created$/) { String topic ->

}

Given(~/^the subscription "([^"]*)"  already exists or is created$/) { String subscription ->

}

When(~/^the topic "([^"]*)" is created$/) { String topic ->

}

When(~/^the subscription "([^"]*)" is deleted$/) { String subscription ->

}

When(~/^the subscription "([^"]*)" is created$/) { String subscription ->

}

When(~/^the topic "([^"]*)" is deleted$/) { String topic ->

}

When(~/^a subscription with a malformed url is created$/) { ->

}

Then(~/^the topic "([^"]*)" cannot be found$/) { String topic ->

}

Then(~/^the topic "([^"]*)" exists$/) { String topic ->

}

Then(~/^the subscription "([^"]*)" exists$/) { String subscription ->

}

Then(~/^the subscription "([^"]*)" does not exist$/) { String subscription ->

}

Then(~/^an error is returned, indicating the topic already exists$/) { ->

}

Then(~/^an error is returned, indicating the subscription could not be created$/) { ->

}