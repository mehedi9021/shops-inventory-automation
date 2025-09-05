package stepdefinitions;

import config.ConfigReader;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.junit.Assert.assertEquals;

public class LoginSteps {
    private Response response;

    @When("I try to login with valid credentials")
    public void i_try_to_login_with_valid_credentials() {
        String baseUrl = ConfigReader.getProperty("baseUrl");
        String username = ConfigReader.getProperty("username");
        String password = ConfigReader.getProperty("password");

        response = RestAssured.given()
                .baseUri(baseUrl)
                .header("Content-Type", "application/json")
                .body("{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}")
                .post("/auth/login");
    }

    @When("I try to login with invalid password")
    public void i_try_to_login_with_invalid_password() {
        String baseUrl = ConfigReader.getProperty("baseUrl");
        String username = ConfigReader.getProperty("username");
        String invalidPassword = "wrongpassword";

        response = RestAssured.given()
                .baseUri(baseUrl)
                .header("Content-Type", "application/json")
                .body("{\"username\":\"" + username + "\",\"password\":\"" + invalidPassword + "\"}")
                .post("/auth/login");
    }

    @Then("login should be successful")
    public void login_should_be_successful() {
        assertEquals(200, response.getStatusCode());
    }

    @Then("login should fail with status code {int}")
    public void login_should_fail_with_status_code(Integer expectedStatusCode) {
        assertEquals(expectedStatusCode.intValue(), response.getStatusCode());
    }
}
