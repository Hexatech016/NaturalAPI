package com.hexaTech.domain.entity;

import com.hexaTech.application.config.SpringConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=SpringConfig.class)
public class BALTest {
    BAL bal=null;

    @Before
    public void before(){
        bal=new BAL();
    }

    @Test
    public void defaultConstructorTest(){
        bal=new BAL("name");
        assertNotNull(bal.getMethods());
        assertNotNull(bal.getStructures());
    }

    @Test
    public void setMethodTest(){
        bal.setMethods(null);
        assertNull(bal.getMethods());
    }

    @Test
    public void addStructureTest(){
        bal.addStructure(new StructureBAL());
        assertEquals(1,bal.getStructures().size());
    }

    @Test
    public void getStructureTest(){
        assertNotNull(bal.getStructures());
    }

}//BALTest
