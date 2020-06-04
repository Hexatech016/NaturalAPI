package com.hexaTech.domain.entity;

import com.hexaTech.application.cli.config.SpringConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= SpringConfig.class)
public class StructureTest{

    Structure structure=null;

    @Before
    public void before(){
        structure=new Structure();
    }

    @Test
    public void constructorTest(){
        assertNotNull(structure);
    }

    @Test
    public void defaultConstructorTest(){
        structure=new Structure("name",null);
        assertEquals("name",structure.getStructureName());
        assertNull(structure.getStructureParam());
    }

    @Test
    public void getStructureNameTest(){
        assertEquals("",structure.getStructureName());
    }

    @Test
    public void getStructureParamTest(){
        assertNull(structure.getStructureParam().get(""));
    }

    @Test
    public void setStructureNameTest(){
        structure.setStructureName("name");
        assertEquals("Name",structure.getStructureName());
    }

    @Test
    public void setStructureParamList(){
        HashMap<String,String> param=new HashMap<>();
        param.put("param","param");
        structure.setStructureParam(param);
        assertEquals(1,structure.getStructureParam().size());
    }

    @Test(expected=NullPointerException.class)
    public void createAPINullPLATest(){
        String[] types={"int","float"};
        structure.createAPI(null,0,2,types,true);
    }

    @Test(expected=ArrayIndexOutOfBoundsException.class)
    public void createAPIArrayOutTest(){
        String[] pla={"hello","world"};
        structure.createAPI(pla,2,3,null,true);
    }

    @Test
    public void createAPITest(){
        structure.setStructureName("name");
        HashMap<String,String> param=new HashMap<>();
        param.put("param","integer");
        structure.setStructureParam(param);
        String[] pla={"<--structParamName-->-<--structName-->"};
        String[] types={"int"};
        assertEquals("int param;-Name\n",structure.createAPI(pla,0,1,types,true));
    }

    @Test
    public void createAPIUntypedTest(){
        structure.setStructureName("name");
        HashMap<String,String> param=new HashMap<>();
        param.put("param","integer");
        structure.setStructureParam(param);
        String[] pla={"<--structParamName--> <--structName-->"};
        String[] types={"/"};
        assertEquals("param Name\n",structure.createAPI(pla,0,1,types,false));
    }

}//StructureTest
