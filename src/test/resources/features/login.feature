Feature: Login Functionality

  Scenario: Valid login
    When I try to login with valid credentials
    Then login should be successful

  Scenario: Invalid login
    When I try to login with invalid password
    Then login should fail with status code 400