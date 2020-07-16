package com.hexaTech.usecases.discover;

import com.hexaTech.adapter.framework.StanfordNLP;
import com.hexaTech.adapter.interfaceadapter.discover.DiscoverPresenter;
import com.hexaTech.adapter.repository.RepoBDL;
import com.hexaTech.adapter.repository.RepoGherkin;
import com.hexaTech.domain.entity.BDL;
import com.hexaTech.domain.entity.Document;
import com.hexaTech.domain.port.out.framework.TextsParsingInterface;
import com.hexaTech.domain.port.out.repository.RepoBDLInterface;
import com.hexaTech.domain.port.out.repository.RepoGherkinInterface;
import com.hexaTech.domain.port.out.usecase.CheckBetweenBDLAndGherkinOutputPort;
import com.hexaTech.domain.usecase.discover.CheckBetweenBDLAndGherkin;
import net.didion.jwnl.JWNLException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CheckBetweenBDLAndGherkinTest {
    @InjectMocks
    CheckBetweenBDLAndGherkin checkBetweenBDLAndGherkin;

    @Mock
    CheckBetweenBDLAndGherkinOutputPort checkBetweenBDLAndGherkinOutputPort;
    @Mock
    RepoBDLInterface repoBDLInterface;
    @Mock
    RepoGherkinInterface repoGherkinInterface;
    @Mock
    TextsParsingInterface textsParsingInterface;

    @Before
    public void before(){
        checkBetweenBDLAndGherkinOutputPort=Mockito.spy(new DiscoverPresenter());
        repoBDLInterface=Mockito.spy(new RepoBDL());
        repoGherkinInterface=Mockito.spy(new RepoGherkin());
        textsParsingInterface=Mockito.spy(new StanfordNLP());
        checkBetweenBDLAndGherkin=new CheckBetweenBDLAndGherkin(checkBetweenBDLAndGherkinOutputPort,repoBDLInterface,repoGherkinInterface,textsParsingInterface);
    }

    @Test(expected=NullPointerException.class)
    public void checkExceptionTest() throws IOException, JWNLException {
        checkBetweenBDLAndGherkin.check("test");
    }

    @Test(expected=NullPointerException.class)
    public void checkException2Test() throws IOException, JWNLException {
        repoBDLInterface.setBDL(new BDL());
        checkBetweenBDLAndGherkin.check("test");
    }

    @Test
    public void checkTest() throws IOException, JWNLException {
        repoBDLInterface.setBDL(new BDL());
        repoGherkinInterface.setGherkin(new Document("title",
                "." + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "testFiles" + File.separator + "pla.pla"));
        checkBetweenBDLAndGherkin.check("test");
        verify(checkBetweenBDLAndGherkinOutputPort).showCheck(anyString());
    }


}//CheckBetweenBDLAndGherkinTest
