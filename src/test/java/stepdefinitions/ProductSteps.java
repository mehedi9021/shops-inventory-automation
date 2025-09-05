package stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import utils.ApiClient;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class ProductSteps {
    private Response response;
    private String createdProductId;
    private final String duplicateName = "TestPad";
    private final String productType = "games";

    @When("I create a new unique product")
    public void i_create_a_new_unique_product() {
        String name = "Test Product " + UUID.randomUUID();
        String body = String.format("{\"name\":\"%s\",\"price\":19.99,\"productType\":\"%s\",\"quantity\":10}", name, productType);
        response = ApiClient.getRequestWithAuth().body(body).post("/products");
        createdProductId = response.jsonPath().getString("productId");
    }

    @Then("the product should be created successfully")
    public void the_product_should_be_created_successfully() {
        assertEquals(201, response.getStatusCode());
    }

    @When("I successfully update the existing product")
    public void i_successfully_update_the_existing_product() {
        String body = "{\"name\":\"UpdatedName\",\"price\":25.0,\"productType\":\"games\",\"quantity\":5}";
        response = ApiClient.getRequestWithAuth().body(body).put("/products/" + createdProductId);
    }

    @Then("the update should be successful")
    public void the_update_should_be_successful() {
        assertEquals(200, response.getStatusCode());
    }

    @When("I get all products")
    public void i_get_all_products() {
        response = ApiClient.getRequestWithAuth().get("/products");
    }

    @Then("the product list should be returned")
    public void the_product_list_should_be_returned() {
        assertEquals(200, response.getStatusCode());
    }

    @When("I get a product by its ID")
    public void i_get_the_same_product_by_id() {
        response = ApiClient.getRequestWithAuth().get("/products/" + createdProductId);
    }

    @Then("the product details should be returned")
    public void the_product_details_should_be_returned() {
        assertEquals(200, response.getStatusCode());
    }

    @When("I delete an existing product")
    public void i_delete_an_existing_product() {
        response = ApiClient.getRequestWithAuth().delete("/products/" + createdProductId);
    }

    @Then("the product should be deleted successfully")
    public void the_product_should_be_deleted_successfully() {
        assertEquals(200, response.getStatusCode());
    }

    @When("I create a product to test duplication with name \"TestPad\" and type \"games\"")
    public void i_create_a_product_to_test_duplication_with_name_and_type() {
        String body = String.format("{\"name\":\"%s\",\"price\":19.99,\"productType\":\"%s\",\"quantity\":10}", duplicateName, productType);
        response = ApiClient.getRequestWithAuth().body(body).post("/products");
    }

    @When("I try to create the same product again")
    public void i_try_to_create_the_same_product_again() {
        String body = String.format("{\"name\":\"%s\",\"price\":19.99,\"productType\":\"%s\",\"quantity\":10}", duplicateName, productType);
        response = ApiClient.getRequestWithAuth().body(body).post("/products");
    }

    @Then("the product creation should fail due to duplication")
    public void the_product_creation_should_fail_due_to_duplication() {
        assertEquals(400, response.getStatusCode());
    }

    @When("I try to create a product without authentication")
    public void i_try_to_create_a_product_without_authentication() {
        String body = "{\"name\":\"UnauthorizedProduct\",\"price\":10.0,\"productType\":\"games\",\"quantity\":5}";
        response = ApiClient.getRequestWithoutAuth().body(body).post("/products");
    }

    @Then("the request should be unauthorized")
    public void the_request_should_be_unauthorized() {
        assertEquals(401, response.getStatusCode());
    }

    @When("I try to update a non-existent product with ID {string}")
    public void i_try_to_update_a_non_existent_product_with_id(String productId) {
        String body = "{\"name\":\"UpdatedName\",\"price\":25.0,\"productType\":\"games\",\"quantity\":5}";
        response = ApiClient.getRequestWithAuth().body(body).put("/products/" + productId);
    }

    @Then("the update should return not found")
    public void the_update_should_return_not_found() {
        assertEquals(404, response.getStatusCode());
    }

    @When("I try to get a product with ID \"invalid-id-123\"")
    public void i_try_to_get_a_product_with_invalid_id() {
        response = ApiClient.getRequestWithAuth().get("/products/invalid-id-123");
    }

    @Then("the product should not be found")
    public void the_product_should_not_be_found() {
        assertEquals(404, response.getStatusCode());
    }

    @When("I try to delete a product with ID \"invalid-id-123\"")
    public void i_try_to_delete_a_product_with_invalid_id() {
        response = ApiClient.getRequestWithAuth().delete("/products/invalid-id-123");
    }

    @Then("the delete should return not found")
    public void the_delete_should_return_not_found() {
        assertEquals(404, response.getStatusCode());
    }
}
