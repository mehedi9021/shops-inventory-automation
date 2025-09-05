package hooks;

import config.ConfigReader;
import io.cucumber.java.Before;
import utils.ApiClient;

public class Hooks {

    @Before(order = 1)
    public void loginBeforeTests() {
        String username = ConfigReader.getProperty("username");
        String password = ConfigReader.getProperty("password");
        ApiClient.authenticate(username, password);
    }
}
