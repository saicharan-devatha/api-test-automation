package getRequest;

import org.junit.Test;

import static io.restassured.RestAssured.*;

public class authentication extends BaseClass {

    @Test
    public void test1() {
        int responseCode = given().when().get().getStatusCode();
        System.out.println("Response code is " + responseCode);
    }

}
