package com.hexaTech.usecases.discover;

import com.hexaTech.adapter.framework.StanfordNLP;
import com.hexaTech.adapter.interfaceadapter.discover.DiscoverPresenter;
import com.hexaTech.adapter.repository.RepoBDL;
import com.hexaTech.adapter.repository.RepoBO;
import com.hexaTech.adapter.repository.RepoDocument;
import com.hexaTech.domain.port.out.framework.TextsParsingInterface;
import com.hexaTech.domain.port.out.repository.RepoBDLInterface;
import com.hexaTech.domain.port.out.repository.RepoBOInterface;
import com.hexaTech.domain.port.out.repository.RepoDocumentInterface;
import com.hexaTech.domain.port.out.usecase.CreateBDLOutputPort;
import com.hexaTech.domain.usecase.discover.CreateBDL;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CreateBDLTest {
    @InjectMocks
    CreateBDL createBDL;

    @Mock
    CreateBDLOutputPort createBDLOutputPort;
    @Mock
    RepoBDLInterface repoBDLInterface;
    @Mock
    RepoBOInterface repoBOInterface;
    @Mock
    RepoDocumentInterface repoDocumentInterface;
    @Mock
    TextsParsingInterface textsParsingInterface;

    @Before
    public void before(){
        createBDLOutputPort=Mockito.spy(new DiscoverPresenter());
        repoBDLInterface=Mockito.spy(new RepoBDL());
        repoBOInterface=Mockito.spy(new RepoBO());
        repoDocumentInterface=Mockito.spy(new RepoDocument());
        textsParsingInterface=Mockito.spy(new StanfordNLP());
    }

}//CreateBDLTest
