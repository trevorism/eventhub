Feature: Context Root of Eventhub
  In order to use the eventhub API, it must be available

  Scenario: ContextRoot HTTPS
    Given the eventhub application is alive
    When I navigate to "https://event.trevorism.com"
    Then then a link to the help page is displayed

  Scenario: ContextRoot on app engine
    Given the eventhub application is alive
    When I navigate to "https://trevorism-eventhub.appspot.com"
    Then then a link to the help page is displayed

  Scenario: Ping HTTPS
    Given the eventhub application is alive
    When I ping the application deployed to "https://event.trevorism.com"
    Then pong is returned, to indicate the service is alive

  Scenario: Ping on app engine
    Given the eventhub application is alive
    When I ping the application deployed to "https://trevorism-eventhub.appspot.com"
    Then pong is returned, to indicate the service is alive
