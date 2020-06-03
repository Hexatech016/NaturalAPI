package com.hexaTech.frameworks;

import com.hexaTech.interactor.frameworksInterface.JsonParsingInterface;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

}//OpenAPITest
