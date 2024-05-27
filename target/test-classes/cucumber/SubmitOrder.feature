
@tag
Feature: Purchase the order from Ecommerse website
  I want to use this template for my feature file

	Background:
		Given I landed on Ecommerce page

  @Regression
  Scenario Outline: Positive test of submitting an order
    Given I am logged in with valid username <username> and password <password>
    When I add the product <productName>
    And I checkout <productName> and submit order
    Then verify the "Thankyou for the order." message displayed in confirmation page

    Examples: 
      | username               | password    | productName     |
      | camilojimenez@mail.com | Abc123456#  | ZARA COAT 3     |
      | camilogarcia@mail.com  | Abc1234567# | ADIDAS ORIGINAL |
