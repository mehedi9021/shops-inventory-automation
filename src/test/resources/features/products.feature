Feature: Product Management

  Scenario: Successfully create a product
    When I create a new unique product
    Then the product should be created successfully

  Scenario: Successfully update an existing product
    When I create a new unique product
    And I successfully update the existing product
    Then the update should be successful

  Scenario: Get all products
    When I get all products
    Then the product list should be returned

  Scenario: Get product by valid ID
    When I create a new unique product
    And I get a product by its ID
    Then the product details should be returned

  Scenario: Successfully delete a product
    When I create a new unique product
    And I delete an existing product
    Then the product should be deleted successfully

  Scenario: Fail to create a duplicate product
    When I create a product to test duplication with name "TestPad" and type "games"
    And I try to create the same product again
    Then the product creation should fail due to duplication

  Scenario: Unauthorized product creation attempt
    When I try to create a product without authentication
    Then the request should be unauthorized

  Scenario: Attempt to update a non-existent product
    When I try to update a non-existent product with ID "nonexistent-id-123"
    Then the update should return not found

  Scenario: Get product by invalid ID
    When I try to get a product with ID "invalid-id-123"
    Then the product should not be found

  Scenario: Fail to delete a product with invalid ID
    When I try to delete a product with ID "invalid-id-123"
    Then the delete should return not found