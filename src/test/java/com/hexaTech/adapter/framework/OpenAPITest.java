package com.hexaTech.adapter.framework;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import java.io.File;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class OpenAPITest {

    @InjectMocks
    OpenAPI openAPI;

    @Test
    public void extractAPIOpenAPITest(){
        File test=new File(".\\src\\main\\resources\\testFiles\\balOA.json");
        assertNotNull(openAPI.extractAPI(test.getPath()));
    }

    @Test
    public void extractAPISwaggerTest(){
        File test=new File(".\\src\\main\\resources\\testFiles\\bal.json");
        assertNotNull(openAPI.extractAPI(test.getPath()));
    }

    @Test
    public void extractAPINullTest(){
        assertNull(openAPI.extractAPI(""));
    }

    @Test
    public void extractAPIBadSyntaxTest(){
        File test=new File(".\\src\\main\\resources\\testFiles\\pla.pla");
        assertNull(openAPI.extractAPI(test.getPath()));
    }

}//OpenAPITest
