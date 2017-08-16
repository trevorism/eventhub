Feature: Administration of topics and subscriptions
  As an administrative user, I can create and delete topics and subscriptions

  Scenario: Create a topic
    Given the eventhub application is alive
    And the topic "testTopic01" does not exist
    When the topic "testTopic01" is created
    Then the topic "testTopic01" exists

  Scenario: Delete a topic
    Given the eventhub application is alive
    And the topic "testTopic01" already exists or is created
    When the topic "testTopic01" is deleted
    Then the topic "testTopic01" cannot be found

  Scenario: Create a subscription
    Given the eventhub application is alive
    And the topic "testTopic01" already exists or is created
    When the subscription "testTopicSubscription01" is created
    Then the subscription "testTopicSubscription01" exists

  Scenario: Delete a subscription
    Given the eventhub application is alive
    And the topic "testTopic01" already exists or is created
    And the subscription "testTopicSubscription01"  already exists or is created
    When the subscription "testTopicSubscription01" is deleted
    Then the subscription "testTopicSubscription01" does not exist

  Scenario: Add duplicate topic results in an error
    Given the eventhub application is alive
    And the topic "testTopic01" already exists or is created
    When the topic "testTopic01" is created
    Then an error is returned, indicating the topic already exists

  Scenario: Add subscription with a bad url results in an error
    Given the eventhub application is alive
    And the topic "testTopic01" already exists or is created
    When a subscription with a malformed url is created
    Then an error is returned, indicating the subscription could not be created