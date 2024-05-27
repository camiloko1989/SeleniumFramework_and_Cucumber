
@tag
Feature: Error Validations
  I want to use this template for my feature file

  @ErrorValidation
  Scenario Outline: Validate login error message
    Given I landed on Ecommerce page
    When I am logged in with valid username <username> and password <password>
    Then "Incorrect email or password." is displayed

    Examples: 
      | username               | password | 
      | camilojimen@mail.com | Abc1234#   | 
      | camilogarc@mail.com  | Abc12347#  | 
