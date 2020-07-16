package com.hexaTech.usecases.design;

import com.hexaTech.adapter.interfaceadapter.design.DesignPresenter;
import com.hexaTech.adapter.repository.RepoBO;
import com.hexaTech.domain.port.out.repository.RepoBOInterface;
import com.hexaTech.domain.port.out.usecase.AddBOOutputPort;
import com.hexaTech.domain.usecase.design.AddBO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AddBOTest {
    @InjectMocks
    AddBO addBO;

    @Mock
    RepoBOInterface repoBOInterface;

    @Mock
    AddBOOutputPort addBOOutputPort;

    @Before
    public void before(){
        repoBOInterface=Mockito.spy(new RepoBO());
        addBOOutputPort=Mockito.spy(new DesignPresenter());
        addBO=new AddBO(addBOOutputPort,repoBOInterface);
    }

    @Test
    public void addBOTest() throws IOException {
        addBO.addBO("testFiles","bal.json");
        verify(addBOOutputPort).showDone(anyBoolean());
    }

}//AddBOTest
