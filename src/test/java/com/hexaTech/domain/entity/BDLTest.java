package com.hexaTech.domain.entity;

import com.hexaTech.application.config.SpringConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=SpringConfig.class)
public class BDLTest {
    BDL bdl=null;

    @Before
    public void before(){
        bdl=new BDL();
    }

    @Test
    public void defaultConstructorTest(){
        Map<String, Integer> nouns=new HashMap<>();
        Map<String, Integer> verbs=new HashMap<>();
        Map<String, Integer> preds=new HashMap<>();
        nouns.put("noun",1);
        verbs.put("verb",1);
        preds.put("pred",1);
        bdl=new BDL(nouns,verbs,preds);
        assertEquals(1,bdl.getNouns().size());
        assertEquals(1,bdl.getVerbs().size());
        assertEquals(1,bdl.getPredicates().size());
    }

    @Test
    public void constructorTest(){
        assertNotNull(bdl);
    }

    @Test
    public void getNounsTest(){
        assertNotNull(bdl.getNouns());
    }

    @Test
    public void getVerbsTest(){
        assertNotNull(bdl.getVerbs());
    }

    @Test
    public void getPredicatesTest(){
        assertNotNull(bdl.getPredicates());
    }

}//BDLTest
