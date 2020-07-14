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
public class DocumentTest {
    Document document=null;

    @Before
    public void before(){
        document=new Document();
    }

    @Test
    public void defaultConstructorTest(){
        document=new Document("name","path");
        assertEquals("name",document.getTitle());
        assertEquals("path",document.getPath());
    }

    @Test
    public void setTitleTest(){
        document.setTitle("title");
        assertEquals("title",document.getTitle());
    }

    @Test
    public void setPathTest(){
        document.setPath("path");
        assertEquals("path",document.getPath());
    }

}//DocumentTest
