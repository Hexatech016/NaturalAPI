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
public class BOTest {
    BO bo=null;

    @Before
    public void before(){bo=new BO();}

    @Test
    public void constructorTest(){
        assertNotNull(bo);
    }

    @Test
    public void defaultConstructorTest(){
        bo=new BO("name",null);
        assertEquals("name",bo.getOntologyName());
        assertNull(bo.getOntologyObjects());
    }

    @Test
    public void getBONameTest(){assertEquals("",bo.getOntologyName());}

    @Test
    public void setBONameTest(){
        bo.setOntologyName("name");
        assertEquals("name",bo.getOntologyName());
    }

    @Test
    public void getBOObjectsTest(){
        assertNotNull(bo.getOntologyObjects());
    }

    @Test
    public void setBOObjectsTest(){
        bo.setBOObjects(new StructureBAL());
        assertEquals(1,bo.getOntologyObjects().size());
    }

}//BOTest
