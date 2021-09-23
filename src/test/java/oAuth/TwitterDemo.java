package oAuth;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class TwitterDemo {

    @Test
    public void postTweet() {

        Response resp = given().auth().oauth("EqWjHItmprE14cFSggLcq3qse",
                "3MZitJ2gGmGbZVKD2DHF2zX5H14MkB7k1jwB6BEUVt14krkJW2",
                "1348587930499993600-bob5fsd7Zrtmb6BOerwDjWhvmhz6P5",
                "cgbitI4kpMffyIOYkQP8FaOPXhPyUTJ5CE8WrIqsy7tqb")
                .post("https://api.twitter.com/1.1/statuses/update.json?status=This is my second tweet via api");

        System.out.println(resp.getStatusCode());

        System.out.println(resp.getBody().asString());
        System.out.println(resp.getBody().jsonPath().prettify());

        JsonPath json = resp.jsonPath();
        String tweetId = json.get("id_str");

        System.out.println("My tweet id " + tweetId);

        Response deleteResp = given().auth().oauth("EqWjHItmprE14cFSggLcq3qse",
                "3MZitJ2gGmGbZVKD2DHF2zX5H14MkB7k1jwB6BEUVt14krkJW2",
                "1348587930499993600-bob5fsd7Zrtmb6BOerwDjWhvmhz6P5",
                "cgbitI4kpMffyIOYkQP8FaOPXhPyUTJ5CE8WrIqsy7tqb")
                .post("https://api.twitter.com/1.1/statuses/destroy/tweetId.json?status=This is my second tweet via api");

        System.out.println(deleteResp.getStatusCode());

        System.out.println(deleteResp.getBody().asString());
    }
}
