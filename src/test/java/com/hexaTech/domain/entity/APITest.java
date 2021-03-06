package com.hexaTech.domain.entity;

import com.hexaTech.application.config.SpringConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= SpringConfig.class)
public class APITest {

    API api=null;

    @Before
    public void before(){
        api=new API();
    }

    @Test
    public void constructorTest(){
        assertNotNull(api);
    }

    @Test
    public void defaultConstructorTest(){
        api=new API("name","comment",null,null,null);
        assertEquals("Name",api.getAPIName());
        assertEquals("comment",api.getAPIComment());
        assertNull(api.getAPIMethods());
        assertNull(api.getAPITests());
        assertNull(api.getAPIStructures());
    }

    @Test
    public void getAPINameTest(){
        assertEquals("",api.getAPIName());
    }

    @Test
    public void getAPICommentTest(){
        assertEquals("",api.getAPIComment());
    }

    @Test
    public void getAPIMethodsTest(){
        assertEquals(0,api.getAPIMethods().size());
    }

    @Test
    public void getAPIStructuresTest(){
        assertEquals(0,api.getAPIStructures().size());
    }

    @Test
    public void setAPINameTest(){
        api.setAPIName("name");
        assertEquals("Name",api.getAPIName());
    }

    @Test
    public void setAPICommentTest(){
        api.setAPIComment("comment");
        assertEquals("comment",api.getAPIComment());
    }

    @Test
    public void setAPIMethodsTest(){
        List<Method> methodList=new ArrayList<>();
        methodList.add(new Method());
        api.setAPIMethods(methodList);
        assertEquals(1,api.getAPIMethods().size());
    }

    @Test
    public void setAPIStructuresTest(){
        List<Structure> structureList=new ArrayList<>();
        structureList.add(new Structure());
        api.setAPIStructures(structureList);
        assertEquals(1,api.getAPIStructures().size());
    }

    @Test(expected=NullPointerException.class)
    public void createAPINullPointerTest(){
        api.setAPIName("name");
        assertEquals("Name",api.createAPI("<--className-->"));
    }

    @Test(expected=ArrayIndexOutOfBoundsException.class)
    public void createTestsArrayIndexOutOfBoundsExceptionTest(){
        api.setAPIName("name");
        assertEquals("NameTest",api.createTests("<--className-->"));
    }

    @Test
    public void createAPITest(){
        api.setAPIName("name");
        api.setAPIComment("comment");
        String pla="<--classComment-->\n"+
                "    <--classExtension: java\n"+
                "    <--integer: int\n"+
                "    <--float: float\n"+
                "    <--string: String\n"+
                "    <--boolean: boolean\n"+
                "<--struct.start-->\n"+
                "<--struct.end-->\n"+
                "<--method.start-->\n"+
                "<--method.end-->\n"+
                "class <--className-->\n"+
                "<--struct.here-->\n"+
                "<--method.here-->";
        assertEquals("comment\n"+
                "    <--classExtension: java\n"+
                "    <--integer: int\n"+
                "    <--float: float\n"+
                "    <--string: String\n"+
                "class Name\n\n",api.createAPI(pla));
    }

    @Test
    public void createTestsTest(){
        api.setAPIName("name");
        api.setAPIComment("comment");
        String pla="<--classComment-->\n"+
                "    <--classExtension: java\n"+
                "    <--integer: int\n"+
                "    <--float: float\n"+
                "    <--string: String\n"+
                "    <--boolean: boolean\n"+
                "<--struct.start-->\n"+
                "<--struct.end-->\n"+
                "<--method.start-->\n"+
                "<--method.end-->\n"+
                "class <--className-->\n"+
                "<--struct.here-->\n"+
                "<--method.here-->";
        assertEquals("class NameTest\n\n",api.createTests(pla));
    }

}//APITest
