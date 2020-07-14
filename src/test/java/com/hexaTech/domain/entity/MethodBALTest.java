package com.hexaTech.domain.entity;

import com.hexaTech.application.config.SpringConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=SpringConfig.class)
public class MethodBALTest {
    MethodBAL methodBAL=null;

    @Before
    public void before(){
        methodBAL=new MethodBAL();
    }

    @Test
    public void setNameTest(){
        methodBAL.setName("name");
        assertEquals("name",methodBAL.getName());
    }

    @Test
    public void setToReturn(){
        methodBAL.setToRet("toReturn");
        assertEquals("toReturn",methodBAL.getToReturn().getType());
    }

    @Test
    public void setTagTest(){
        methodBAL.setTag(null);
        assertNull(methodBAL.getTag());
    }

}//MethodBALTest
