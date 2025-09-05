Feature: Order Management

  Scenario: Successfully create a buy order
    When I place a "buy" order for product "6c39d0e9-e4c3-4e2d-b065-c53f73a9d9ff" with quantity 5
    Then the order should be created successfully

  Scenario: Successfully create a sell order
    When I place a "sell" order for product "6c39d0e9-e4c3-4e2d-b065-c53f73a9d9ff" with quantity 2
    Then the order should be created successfully

  Scenario: Fail to create a sell order due to insufficient stock
    When I place a "sell" order for product "6c39d0e9-e4c3-4e2d-b065-c53f73a9d9ff" with quantity 9999
    Then the order should fail due to insufficient stock

  Scenario: Fail to create an order without authentication
    When I try to place an unauthenticated "buy" order for product "6c39d0e9-e4c3-4e2d-b065-c53f73a9d9ff" with quantity 3
    Then the order request should be unauthorized

  Scenario: Fail to create an order for non-existent product
    When I place a "buy" order for product "nonexistent-product-id" with quantity 1
    Then the order should not be created due to product not found

  Scenario: Get current stock level for a valid product
    When I retrieve stock level for product "6c39d0e9-e4c3-4e2d-b065-c53f73a9d9ff"
    Then the stock information should be returned or not found if there are no transactions

  Scenario: Get stock level for product with no transactions
    When I retrieve stock level for product "no-orders-product-id"
    Then the stock information should not be found due to no transactions