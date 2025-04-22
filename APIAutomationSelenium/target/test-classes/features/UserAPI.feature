@APITest
Feature: User API Tests

  @GetRequest @Positive
  Scenario: Get list of users
    Given I send the GET request to end-point "api/users?page=2"
    Then the http status code should be 200
    And the response should contain a list of "users"
    And the first "email" should be "michael.lawson@reqres.in"

  @GetRequest @Positive
  Scenario: Get single user
    Given I send the GET request to end-point "api/users/2"
    Then the http status code should be 200
    And the response should contain "addressed_user_data"
    And the "first_name" of single "user" data should be "Janet"

  @GetRequest @Negative
  Scenario: Get single user (not found)
    Given I send the GET request to end-point "api/users/23"
    Then the http status code should be 404
    And the response should contain empty data

  @GetRequest @Positive
  Scenario: Get list of resources
    Given I send the GET request to end-point "api/unknown"
    Then the http status code should be 200
    And the response should contain a list of "resources"
    And the first "name" should be "cerulean"

  @GetRequest @Positive
  Scenario: Get single resource
    Given I send the GET request to end-point "api/unknown/2"
    Then the http status code should be 200
    And the response should contain "addressed_resource_data"
    And the "name" of single "resource" data should be "fuchsia rose"

  @GetRequest @Negative
  Scenario: Get single resource (not found)
    Given I send the GET request to end-point "api/unknown/23"
    Then the http status code should be 404
    And the response should contain empty data

  @PostRequest @Positive
  Scenario: Create a new user
    Given I input data that contains "name" with "Khansa" and "job" with "Junior SQA"
    When I send the POST request to end-point "api/users"
    Then the http status code should be 201
    And the response should contain "new_user_data"

  @PostRequest @Negative
  Scenario: Create a new user with missing fields
    Given I input data that contains "name" with "Khansa" and "job" with ""
    When I send the POST request to end-point "api/users"
    Then the http status code should be 400
    And the response should be contain an error message "Missing job"

  @PutRequest @Positive
  Scenario: Update entire user data by id
    Given I input data that contains "name" with "Khansa" and "job" with "Lead QA"
    When I send the PUT request to end-point "api/users/2"
    Then the http status code should be 200
    And the response should contain "updated_user_data"

  @PatchRequest @Positive
  Scenario: Update partially user data by id
    Given I input data that contains "name" with "" and "job" with "Lead QA"
    When I send the PATCH request to end-point "api/users/2"
    Then the http status code should be 200
    And the response should contain "updated_user_data"
    And the field name should still exist

  @DeleteRequest @Positive
  Scenario: Delete selected user
    Given I send the DELETE request to end-point "api/users/2"
    Then the http status code should be 204
    And the response should contain empty content

  @PostRequest @Positive
  Scenario: Register with valid email and password
    Given I input data that contains "email" with "eve.holt@reqres.in" and "password" with "pistol"
    When I send the POST request to end-point "api/register"
    Then the http status code should be 200
    And the response should contain "registered_data"

  @PostRequest @Negative
  Scenario: Register with empty password field
    Given I input data that contains "email" with "eve.holt@reqres.in" and "password" with ""
    When I send the POST request to end-point "api/register"
    Then the http status code should be 400
    And the response should be contain an error message "Missing password"

  @PostRequest @Negative
  Scenario: Register with empty email field
    Given I input data that contains "email" with "" and "password" with "pistol"
    When I send the POST request to end-point "api/register"
    Then the http status code should be 400
    And the response should be contain an error message "Missing email or username"

  @PostRequest @Negative
  Scenario: Register by leaving email and password empty
    Given I input data that contains "email" with "" and "password" with ""
    When I send the POST request to end-point "api/register"
    Then the http status code should be 400
    And the response should be contain an error message "Missing email or username"

  @PostRequest @Positive
  Scenario: Login with valid email and password
    Given I input data that contains "email" with "eve.holt@reqres.in" and "password" with "pistol"
    When I send the POST request to end-point "api/login"
    Then the http status code should be 200
    And the response should contain "registered_data"

  @PostRequest @Negative
  Scenario: Login with valid email and invalid password
    Given I input data that contains "email" with "eve.holt@reqres.in" and "password" with "waduh"
    When I send the POST request to end-point "api/login"
    Then the http status code should be 400
    And the response should be contain an error message "user not found"

  @PostRequest @Negative
  Scenario: Login with invalid email and valid password
    Given I input data that contains "email" with "??" and "password" with "pistol"
    When I send the POST request to end-point "api/login"
    Then the http status code should be 400
    And the response should be contain an error message "user not found"

  @PostRequest @Negative
  Scenario: Login with empty password field
    Given I input data that contains "email" with "eve.holt@reqres.in" and "password" with ""
    When I send the POST request to end-point "api/login"
    Then the http status code should be 400
    And the response should be contain an error message "Missing password"

  @PostRequest @Negative
  Scenario: Login with empty email field
    Given I input data that contains "email" with "" and "password" with "pistol"
    When I send the POST request to end-point "api/login"
    Then the http status code should be 400
    And the response should be contain an error message "Missing email or username"

  @PostRequest @Negative
  Scenario: Login by leaving email and password empty
    Given I input data that contains "email" with "" and "password" with ""
    When I send the POST request to end-point "api/login"
    Then the http status code should be 400
    And the response should be contain an error message "Missing email or username"

  @GetRequest @Positive
  Scenario: Get users with delay of three seconds
    Given I send the GET request to end-point "api/users?delay=3"
    Then the response time should be approximately 3 seconds
    And the http status code should be 200
    And the response should contain a list of "users"
    And the first "email" should be "george.bluth@reqres.in"
