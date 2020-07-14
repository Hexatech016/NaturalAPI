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
public class DoubleStructTest {
    DoubleStruct doubleStruct=null;

    @Before
    public void before(){
        doubleStruct=new DoubleStruct("token","lemma");
    }

    @Test
    public void getLemmaTest(){
        assertEquals("lemma",doubleStruct.getLemma());
    }

    @Test
    public void getTokenTest(){
        assertEquals("token",doubleStruct.getToken());
    }

    @Test
    public void toStringTest(){
        assertEquals(doubleStruct.getToken()+" "+doubleStruct.getLemma(), doubleStruct.toString());
    }

}//DoubleStructTest
