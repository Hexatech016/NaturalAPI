package com.hexaTech.adapter.framework;

import com.hexaTech.domain.entity.DoubleStruct;
import com.hexaTech.domain.entity.Gherkin;
import com.hexaTech.domain.entity.StructureBAL;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class StanfordNLPTest {
    @InjectMocks
    StanfordNLP stanfordNLP;

    @Test
    public void extractBDLFromTextTest(){
        List<DoubleStruct> BDL=stanfordNLP.extractBDLFromText("my name is Test");
        assertNotNull(BDL);
    }

    @Test
    public void extractBOFromTextTest(){
        List<StructureBAL> BO=stanfordNLP.extractBOFromText("my name is Test");
        assertNotNull(BO);
    }

    @Test
    public void extractFromGherkinTest(){
        List<Gherkin> gherkin=stanfordNLP.extractFromGherkin("SCENARIO: hello");
        assertNotNull(gherkin);
    }

    @Test
    public void extractBDLFromGherkinTest(){
        List<DoubleStruct> BDL=stanfordNLP.extractBDLFromGherkin("SCENARIO: my name is Test");
        assertNotNull(BDL);
    }

}//StanfordNLPTest
