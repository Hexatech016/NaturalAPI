package com.hexaTech.usecases.discover;

import com.hexaTech.adapter.interfaceadapter.design.DesignPresenter;
import com.hexaTech.adapter.repository.RepoBDL;
import com.hexaTech.domain.port.out.repository.RepoBDLInterface;
import com.hexaTech.domain.port.out.usecase.AddBDLOutputPort;
import com.hexaTech.domain.usecase.design.AddBDL;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AddBDLToDiscoverTest {
    @InjectMocks
    AddBDL addBDL;

    @Mock
    AddBDLOutputPort addBDLOutputPort;

    @Mock
    RepoBDLInterface repoBDLInterface;

    @Before
    public void before(){
        addBDLOutputPort=Mockito.spy(new DesignPresenter());
        repoBDLInterface=Mockito.spy(new RepoBDL());
        addBDL=new AddBDL(addBDLOutputPort,repoBDLInterface);
    }

    @Test
    public void addBDLTest() throws IOException {
        addBDL.addBDL("");
        verify(addBDLOutputPort).showDone(false);
    }

    @Test
    public void checkIfRepoBDLIsEmpty(){
        addBDL.checkIfRepoBDLIsEmpty();
        verify(addBDLOutputPort).showDone(true);
    }

}//AddBDLToDiscoverTest
