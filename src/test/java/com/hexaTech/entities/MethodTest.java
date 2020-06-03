package com.hexaTech.entities;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.HashMap;
import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=com.hexaTech.SpringConfig.class)
public class MethodTest{

    Method method=null;

    @Before
    public void before(){
        method=new Method();
    }

    @Test
    public void constructorTest(){
        assertNotNull(method);
    }

    @Test
    public void getMethodNameTest(){
        assertEquals("",method.getMethodName());
    }

    @Test
    public void getMethodReturnTypeTest(){
        assertEquals("",method.getMethodReturnType());
    }

    @Test
    public void getMethodCommentTest(){
        assertEquals("",method.getMethodComment());
    }

    @Test
    public void getMethodParamTest(){
        assertEquals(new HashMap<>(),method.getMethodParam());
    }

    @Test
    public void setMethodNameTest(){
        method.setMethodName("method");
        assertEquals("method",method.getMethodName());
    }

    @Test
    public void setMethodReturnTypeTest(){
        method.setMethodReturnType("return");
        assertEquals("return",method.getMethodReturnType());
    }

    @Test
    public void setMethodCommentTest(){
        method.setMethodComment("comment");
        assertEquals("comment",method.getMethodComment());
    }

    @Test
    public void setMethodParamTest(){
        HashMap<String,String> param=new HashMap<>();
        param.put("param","param");
        method.setMethodParam(param);
        assertEquals(1,method.getMethodParam().size());
    }

    @Test(expected=NullPointerException.class)
    public void createAPINullPLATest(){
        String[] types={"int","float"};
        method.createAPI(null,0,2,types,true);
    }

    @Test(expected=NullPointerException.class)
    public void createAPINullTypesTest(){
        method.setMethodReturnType("integer");
        String[] pla={"pla","java","hello","world","<--methodReturn-->"};
        method.createAPI(pla,0,5,null,true);
    }

    @Test(expected=NullPointerException.class)
    public void createTestsNullPLATest(){
        String[] types={"int","float"};
        method.createTests(null,0,2,types,true,"");
    }

    @Test(expected=NullPointerException.class)
    public void createTestsNullTypesTest(){
        method.setMethodReturnType("integer");
        String[] pla={"pla","java","hello","world","<--methodReturn-->"};
        method.createTests(pla,0,5,null,true,"");
    }

    @Test(expected=ArrayIndexOutOfBoundsException.class)
    public void createAPIArrayOutTest(){
        String[] pla={"hello","world"};
        method.createAPI(pla,2,3,null,true);
    }

    @Test(expected=ArrayIndexOutOfBoundsException.class)
    public void createTestsArrayOutTest(){
        String[] pla={"hello","world"};
        method.createTests(pla,2,3,null,true,"");
    }

    @Test
    public void createAPITest(){
        method.setMethodReturnType("integer");
        method.setMethodName("name");
        String[] pla={"<--methodReturn-->-<--methodName-->"};
        String[] types={"int"};
        assertEquals("int-name\n",method.createAPI(pla,0,1,types,true));
    }

    @Test
    public void createTestsTest(){
        method.setMethodReturnType("integer");
        method.setMethodName("name");
        String[] pla={"<--methodReturn-->-<--methodName-->"};
        String[] types={"int"};
        assertEquals("int-nameTest\n",method.createTests(pla,0,1,types,true,""));
    }

}//MethodTest
