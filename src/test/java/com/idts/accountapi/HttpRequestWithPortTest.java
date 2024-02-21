package com.idts.accountapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.util.Assert;

import java.util.LinkedHashMap;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpRequestWithPortTest {
    private static final String USERNAME = "username";
    private static final String HOST = "http://localhost:";
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void returnTheFirstPlayerInTheDatabaseTest() throws Exception {
        LinkedHashMap firstUserResponseObject = this.testRestTemplate.getForObject( HOST + port +
                "/api/users/getById/1", LinkedHashMap.class);

        Assert.isTrue(firstUserResponseObject.containsKey(USERNAME),
                "The response object does not contain the name field");
        Assert.isTrue(firstUserResponseObject.get(USERNAME).equals("Mathias"),
                "The name of the first player is unexpected");
    }
}
