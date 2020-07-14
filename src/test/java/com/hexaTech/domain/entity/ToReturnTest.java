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
public class ToReturnTest {
    ToReturn toReturn=null;

    @Before
    public void before(){
        toReturn=new ToReturn();
    }

    @Test
    public void defaultConstructorTest(){
        toReturn=new ToReturn("type");
        assertEquals("type",toReturn.getType());
    }

    @Test
    public void setTypeTest(){
        toReturn.setType("type");
        assertEquals("type",toReturn.getType());
    }

}//ToReturnTest
