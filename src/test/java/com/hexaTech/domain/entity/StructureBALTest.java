package com.hexaTech.domain.entity;

import com.hexaTech.application.config.SpringConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=SpringConfig.class)
public class StructureBALTest {
    StructureBAL structureBAL=null;

    @Before
    public void before(){
        structureBAL=new StructureBAL();
    }

    @Test
    public void defaultConstructorTest(){
        structureBAL=new StructureBAL("name",null);
        assertEquals("name",structureBAL.getName());
        assertNull(structureBAL.getParameters());
    }

    @Test
    public void setNameTest(){
        structureBAL.setName("name");
        assertEquals("name",structureBAL.getName());
    }

    @Test
    public void setParameterTest(){
        structureBAL.setParameters(new Parameter());
        assertEquals(1,structureBAL.getParameters().size());
    }

    @Test
    public void setParametersTest(){
        structureBAL.setParameters((List<Parameter>) null);
        assertNull(structureBAL.getParameters());
    }

}//StructureBALTest
