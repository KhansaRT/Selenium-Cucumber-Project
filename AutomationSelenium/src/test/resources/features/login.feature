@Login
Feature: Login to SwagLabs
  Feature to test login funcionality

  @Smoke @PositiveTest
  Scenario Outline: check login is successful with valid credentials
    Given user is on login page
    When user enters "<username>" and "<password>"
    And user clicks on login button
    Then user is navigated to the inventory page

    Examples: 
      | username      | password     |
      | standard_user | secret_sauce |

  @NegativeTest
  Scenario Outline: check login is un-successful with invalid credentials
    Given user is on login page
    When user enters "<username>" and "<password>"
    And user clicks on login button
    Then user got error message "<error_message>"

    Examples: 
      | username      | password     | error_message                                                             |
      | standard      | secret_sauce | Epic sadface: Username and password do not match any user in this service |
      | standard_user | secret       | Epic sadface: Username and password do not match any user in this service |
      
  @BlankTest
  Scenario Outline: check login is un-successful with username or password unfilled
    Given user is on login page
    When user enters "<username>" and "<password>"
    And user clicks on login button
    Then user got error message "<error_message>"

    Examples: 
      | username      | password     | error_message                       |
      |               | secret_sauce | Epic sadface: Username is required  |
      | standard_user |              | Epic sadface: Password is required  |
