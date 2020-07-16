package com.hexaTech.usecases.design;

import com.hexaTech.adapter.interfaceadapter.design.DesignPresenter;
import com.hexaTech.adapter.repository.RepoGherkin;
import com.hexaTech.domain.port.out.repository.RepoGherkinInterface;
import com.hexaTech.domain.port.out.usecase.AddGherkinOutputPort;
import com.hexaTech.domain.usecase.design.AddGherkin;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AddGherkinTest {
    @InjectMocks
    AddGherkin addGherkin;

    @Mock
    AddGherkinOutputPort addGherkinOutputPort;

    @Mock
    RepoGherkinInterface repoGherkinInterface;

    @Before
    public void before(){
        addGherkinOutputPort=Mockito.spy(new DesignPresenter());
        repoGherkinInterface=Mockito.spy(new RepoGherkin());
        addGherkin=new AddGherkin(addGherkinOutputPort,repoGherkinInterface);
    }

    @Test
    public void addGherkinTest() throws IOException {
        addGherkin.addGherkin("test","test");
        verify(addGherkinOutputPort).showDone(false);
    }

    @Test
    public void deleteDocTest(){
        addGherkin.deleteDoc("test");
        verify(addGherkinOutputPort).showAddedGherkin(anyString());
    }

    @Test
    public void existsDocTest(){
        addGherkin.existsDoc("test");
        verify(addGherkinOutputPort).showDone(false);
    }

}//AddGherkinTest
