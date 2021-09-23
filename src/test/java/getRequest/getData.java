package getRequest;

import bsh.XThis;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import org.testng.Assert;

import static io.restassured.RestAssured.*;

import io.restassured.response.Response;

public class getData {
    //@Test
    public void testResponseCode() {
        int code = get("https://www.google.com/").getStatusCode();
        System.out.println("Status code is " + code);
        Assert.assertEquals(code, 200);
    }

    //@Test
    public void testBody() {
        String data = get("https://www.google.com/").asString();
        long time = get("https://www.google.com/").getTime();
        System.out.println("SData is" + data);
        System.out.println("Response time" + time);
    }

    @Test()
    public void createId() {
        RequestSpecification request = given().contentType(ContentType.JSON);
        JSONObject json = new JSONObject();
        json.put("id", "124");
        json.put("title", "Testing124");
        json.put("author", "author124");

        request.body(json.toJSONString());

        int responseCode = request.post("http://localhost:3000/posts").getStatusCode();
        System.out.println("Status code is " + responseCode);
        Assert.assertEquals(responseCode, 201);
    }

    @Test(dependsOnMethods = {"createId"})
    public void getId() {
        int responseCode = get("http://localhost:3000/posts/124").getStatusCode();
        String data = get("http://localhost:3000/posts/124").asString();
        System.out.println("Get status code is " + responseCode);
        System.out.println("Data is " + data);
        Assert.assertEquals(responseCode, 200);
    }

    @Test(dependsOnMethods = {"getId"})
    public void updateAuthor() {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        JSONObject json = new JSONObject();
        json.put("id", "124");
        json.put("title", "Testing124");
        json.put("author", "Updating author");

        request.body(json.toJSONString());

        int responseCode = request.put("http://localhost:3000/posts/124").getStatusCode();
        String updatedData = request.put("http://localhost:3000/posts/124").asString();
        System.out.println("Update status code is " + responseCode);
        System.out.println("Updated data is " + updatedData);
        Assert.assertEquals(responseCode, 200);
    }

    @Test(dependsOnMethods = {"updateAuthor"})
    public void deleteId() {
        int responseCode = delete("http://localhost:3000/posts/124").getStatusCode();
        String data = get("http://localhost:3000/posts/124").asString();
        System.out.println(" Delete status code is " + responseCode);
        System.out.println("Data is " + data);
        Assert.assertEquals(responseCode, 200);
    }
}
