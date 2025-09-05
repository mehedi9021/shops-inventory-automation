package utils;

import config.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ApiClient {
    private static String token;

    public static void authenticate(String username, String password) {
        String baseUrl = ConfigReader.getProperty("baseUrl");

        Response response = RestAssured.given()
                .baseUri(baseUrl)
                .header("Content-Type", "application/json")
                .body("{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}")
                .post("/auth/login");

        response.then().statusCode(200);
        token = response.jsonPath().getString("token");
    }

    public static RequestSpecification getRequestWithAuth() {
        String baseUrl = ConfigReader.getProperty("baseUrl");

        return RestAssured.given()
                .baseUri(baseUrl)
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");
    }

    public static RequestSpecification getRequestWithoutAuth() {
        String baseUrl = ConfigReader.getProperty("baseUrl");

        return RestAssured.given()
                .baseUri(baseUrl)
                .header("Content-Type", "application/json");
    }
}
