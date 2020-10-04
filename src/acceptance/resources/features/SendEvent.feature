Feature: Sending events
  Posting json is how to create an event

  Scenario: Sending an event HTTPS
    Given the eventhub application is alive with a base URL of "https://event.trevorism.com"
    And the datastore application is alive
    When I post an event to "testTopic"
    Then then the event is saved to the datastore within 10 seconds

  Scenario: Sending an correlated event HTTPS
    Given the eventhub application is alive with a base URL of "https://event.trevorism.com"
    And the datastore application is alive
    When I post an event to "testTopic"
    Then then the event is saved to the datastore within 10 seconds
    And the same correlationId is returned in the HTTP header