package com.example.fraudservice;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FraudServiceApplication.class)
public abstract class BaseClass {
    @Autowired
    FraudController fraudController;

    @Before
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(fraudController);
    }
}
