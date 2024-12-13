Feature: SauceDemo Automation Assessment
  Scenario: Sauce demo automation framework
    Given User navigates to the homepage
    When User enters <username> and <password>
    Then User adds items to the cart
    Then User verifies number of items in the cart
    Then User view and verify items in the cart
    Then User checkout the cart
    Then User enters checkout information
    Then User verifies items are correct
    Then User verifies that the total price is correct
    Then User clicks on finish
    Then User verifies the order is complete and logout
    Then User logout after completing order
