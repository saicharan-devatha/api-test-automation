package com.qa.tests;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.Users;
//import io.restassured.mapper.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class PostApiTest extends TestBase {
    TestBase testBase;
    String url;
    String apiUrl;
    String actualUrl;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;

    @BeforeMethod
    public void SetUp() throws ClientProtocolException, IOException {
        testBase = new TestBase();
        url = prop.getProperty("URL");
        apiUrl = prop.getProperty("SERVICEURL");

        actualUrl = url + apiUrl;
    }

    @Test
    public void postApiTest() throws JsonMappingException, JsonGenerationException, IOException {
        restClient = new RestClient();
        HashMap<String, String> headermap = new HashMap<String, String>();
        headermap.put("Content-Type", "application/json");

        //jackson API to do Marshalling and viceversa
        ObjectMapper mapper = new ObjectMapper();
        Users users = new Users("morpheus", "leader"); //expected user object

        // object to Json file conversion
        mapper.writeValue(new File("src/main/java/com/qa/data/users.json"), users);

        // object to json as string
        String usersJsonString = mapper.writeValueAsString(users);
        System.out.println("Printing string-----" + usersJsonString);

        closeableHttpResponse = restClient.post(actualUrl, usersJsonString, headermap);

        //1. status code
        int postStatusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(postStatusCode, testBase.Response_status_code_201);
        //2. JSON String
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");

        JSONObject responseJson = new JSONObject(responseString);
        System.out.println("Response JSON string-----" + responseJson);

        // json to java object
        Users obj = mapper.readValue(responseString, Users.class); // actual user object
        System.out.println("Users obj -----  " + obj);

        System.out.println(users.getName().equals(obj.getName()));

        System.out.println(users.getJob().equals(obj.getJob()));
    }
}
