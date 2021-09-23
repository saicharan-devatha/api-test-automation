package com.qa.tests;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;
import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

public class GetApiTest extends TestBase {
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

    @Test(priority = 1)
    public void getAPITest() throws ClientProtocolException, IOException {
        restClient = new RestClient();
        closeableHttpResponse = restClient.get(actualUrl);

        // getting status code
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        System.out.println("Status code----" + statusCode);

        Assert.assertEquals(statusCode, Response_status_code_200);

        // getting json string
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
        JSONObject responseJson = new JSONObject(responseString);
        System.out.println("Response Json--------" + responseJson);

        String perPageValue = TestUtil.getValueByJPath(responseJson, "/per_page");
        System.out.println("Value per page ----- " + perPageValue);

        Assert.assertEquals(perPageValue, "6");

        String totalValue = TestUtil.getValueByJPath(responseJson, "/total");
        System.out.println("Value per page ----- " + totalValue);

        Assert.assertEquals(Integer.parseInt(totalValue), 12);

        // fetching the JSON array

        String lastname = TestUtil.getValueByJPath(responseJson, "/data[0]/last_name");
        String id = TestUtil.getValueByJPath(responseJson, "/data[0]/id");
        String avatar = TestUtil.getValueByJPath(responseJson, "/data[0]/avatar");
        String firstname = TestUtil.getValueByJPath(responseJson, "/data[0]/first_name");

        Assert.assertEquals(lastname, "Bluth");
        Assert.assertEquals(Integer.parseInt(id), 1);
        Assert.assertEquals(avatar, "https://reqres.in/img/faces/1-image.jpg");
        Assert.assertEquals(firstname, "George");

        // getting all headers
        Header[] headersArray = closeableHttpResponse.getAllHeaders();
        HashMap<String, String> headerMap = new HashMap<String, String>();
        for (Header header : headersArray) {
            headerMap.put(header.getName(), header.getValue());
        }
        System.out.println("Header array--------- " + headerMap);
    }

    @Test(priority = 2)
    public void getAPITestWithHeaders() throws ClientProtocolException, IOException {
        restClient = new RestClient();

        HashMap<String, String> headermap = new HashMap<String, String>();
        headermap.put("Content-Type", "application/json");
        closeableHttpResponse = restClient.get(actualUrl);

        // getting status code
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        System.out.println("Status code----" + statusCode);

        Assert.assertEquals(statusCode, Response_status_code_200);

        // getting json string
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
        JSONObject responseJson = new JSONObject(responseString);
        System.out.println("Response Json--------" + responseJson);

        String perPageValue = TestUtil.getValueByJPath(responseJson, "/per_page");
        System.out.println("Value per page ----- " + perPageValue);

        Assert.assertEquals(perPageValue, "6");

        String totalValue = TestUtil.getValueByJPath(responseJson, "/total");
        System.out.println("Value per page ----- " + totalValue);

        Assert.assertEquals(Integer.parseInt(totalValue), 12);

        // fetching the JSON array

        String lastname = TestUtil.getValueByJPath(responseJson, "/data[0]/last_name");
        String id = TestUtil.getValueByJPath(responseJson, "/data[0]/id");
        String avatar = TestUtil.getValueByJPath(responseJson, "/data[0]/avatar");
        String firstname = TestUtil.getValueByJPath(responseJson, "/data[0]/first_name");

        Assert.assertEquals(lastname, "Bluth");
        Assert.assertEquals(Integer.parseInt(id), 1);
        Assert.assertEquals(avatar, "https://reqres.in/img/faces/1-image.jpg");
        Assert.assertEquals(firstname, "George");

        // getting all headers
        Header[] headersArray = closeableHttpResponse.getAllHeaders();
        HashMap<String, String> headerMap = new HashMap<String, String>();
        for (Header header : headersArray) {
            headerMap.put(header.getName(), header.getValue());
        }
        System.out.println("Header array--------- " + headerMap);

    }
}
