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
public class GherkinTest {
    Gherkin gherkin=null;

    @Before
    public void before(){
        gherkin=new Gherkin();
    }

    @Test
    public void setDescriptionTest(){
        gherkin.setDescription("description");
        assertEquals("description",gherkin.getDescription());
    }

    @Test
    public void setScenarioTest(){
        gherkin.setScenario("scenario");
        assertEquals("scenario",gherkin.getScenario());
    }

    @Test
    public void getWhenTest(){
        assertNotNull(gherkin.getWhen());
    }

    @Test
    public void getThenTest(){
        assertNull(gherkin.getThen());
    }


}//GherkinTest
