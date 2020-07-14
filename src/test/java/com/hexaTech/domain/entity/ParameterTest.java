package com.hexaTech.domain.entity;

import com.hexaTech.application.config.SpringConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=SpringConfig.class)
public class ParameterTest {
    Parameter parameter=null;

    @Before
    public void before(){
        parameter=new Parameter();
    }

    @Test
    public void defaultConstructorTest(){
        parameter=new Parameter("description","name","type");
        assertEquals("description",parameter.getDescription());
        assertEquals("name",parameter.getName());
        assertEquals("type",parameter.getType());
    }

    @Test
    public void setDescriptionTest(){
        parameter.setDescription("description");
        assertEquals("description",parameter.getDescription());
    }

    @Test
    public void setNameTest(){
        parameter.setName("name");
        assertEquals("name",parameter.getName());
    }

    @Test
    public void setTypeTest(){
        parameter.setType("type");
        assertEquals("type",parameter.getType());
    }

}//ParameterTest
