package getRequest;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BaseClass {

    @BeforeClass
    public void setup() {
        RestAssured.authentication = RestAssured.preemptive().basic("", "");
        RestAssured.baseURI = "";
    }
}
