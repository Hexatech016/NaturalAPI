package com.hexaTech.entities;

import com.hexaTech.interactor.entities.PLA;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=com.hexaTech.SpringConfig.class)
public class PLATest{

    PLA pla=null;

    @Before
    public void before(){
        pla=new PLA();
    }

    @Test
    public void constructorTest(){
        assertNotNull(pla);
    }

    @Test
    public void getPathTest(){
        assertEquals("",pla.getPath());
    }

    @Test
    public void getExtensionTest(){
        assertEquals("",pla.getExtension());
    }

    @Test
    public void setPathTest(){
        pla.setPath("path");
        assertEquals("path",pla.getPath());
    }

    @Test
    public void setExtensionTest(){
        pla.setExtension("ext");
        assertEquals("ext",pla.getExtension());
    }

}//PLATest
