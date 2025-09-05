package stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import utils.ApiClient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OrderSteps {
    private Response response;
    private final String basePath = "/orders";

    @When("I place a {string} order for product {string} with quantity {int}")
    public void i_place_order(String orderType, String productId, int quantity) {
        String body = String.format("{\"orderType\":\"%s\",\"productId\":\"%s\",\"quantity\":%d}", orderType, productId, quantity);
        response = ApiClient.getRequestWithAuth().body(body).post(basePath);
    }

    @Then("the order should be created successfully")
    public void the_order_should_be_created_successfully() {
        assertEquals(201, response.getStatusCode());
    }

    @Then("the order should fail due to insufficient stock")
    public void the_order_should_fail_due_to_insufficient_stock() {
        assertEquals(400, response.getStatusCode());
    }

    @When("I try to place an unauthenticated {string} order for product {string} with quantity {int}")
    public void i_try_to_place_unauthenticated_order(String orderType, String productId, int quantity) {
        String body = String.format("{\"orderType\":\"%s\",\"productId\":\"%s\",\"quantity\":%d}", orderType, productId, quantity);
        response = ApiClient.getRequestWithoutAuth().body(body).post(basePath);
    }

    @Then("the order request should be unauthorized")
    public void the_order_request_should_be_unauthorized() {
        assertEquals(401, response.getStatusCode());
    }

    @Then("the order should not be created due to product not found")
    public void the_order_should_not_be_created_due_to_product_not_found() {
        assertEquals(404, response.getStatusCode());
    }

    @When("I retrieve stock level for product {string}")
    public void i_retrieve_stock_level_for_product(String productId) {
        response = ApiClient.getRequestWithAuth().get(basePath + "/products/" + productId);
    }

    @Then("the stock information should be returned or not found if there are no transactions")
    public void the_stock_information_should_be_returned_or_not_found_if_there_are_no_transactions() {
        int statusCode = response.getStatusCode();
        assertTrue(statusCode == 200 || statusCode == 404);
    }

    @Then("the stock information should not be found due to no transactions")
    public void the_stock_information_should_not_be_found_due_to_no_transactions() {
        assertEquals(404, response.getStatusCode());
    }
}
