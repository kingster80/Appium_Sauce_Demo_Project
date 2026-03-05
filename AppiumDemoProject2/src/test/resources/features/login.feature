# src/test/resources/features/login.feature
Feature: Sauce Demo Login

  Scenario: Successful login with valid credentials
    Given I am on the Sauce Demo login page
    When I enter username "standard_user"
    And I enter password "secret_sauce"
    And I click the login button
    Then I should see the inventory page

  Scenario: Login with invalid credentials
    Given I am on the Sauce Demo login page
    When I enter username "wrong_user"
    And I enter password "wrong_pass"
    And I click the login button
    Then I should see an error message "Epic sadface: Username and password do not match any user in this service"