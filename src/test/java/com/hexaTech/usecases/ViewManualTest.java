package com.hexaTech.usecases;

import com.hexaTech.adapter.interfaceadapter.ViewManualPresenter;
import com.hexaTech.adapter.repository.RepoDocument;
import com.hexaTech.domain.port.out.repository.RepoDocumentInterface;
import com.hexaTech.domain.port.out.usecase.ViewManualOutputPort;
import com.hexaTech.domain.usecase.ViewManual;
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
public class ViewManualTest {
    @InjectMocks
    ViewManual viewManual;

    @Mock
    ViewManualOutputPort viewManualOutputPort;

    @Mock
    RepoDocumentInterface repoDocumentInterface;

    @Before
    public void before(){
        viewManualOutputPort=Mockito.spy(new ViewManualPresenter());
        repoDocumentInterface=Mockito.spy(new RepoDocument());
        viewManual=new ViewManual(viewManualOutputPort,repoDocumentInterface);
    }

    @Test
    public void openManualTest() throws IOException {
        viewManual.openManual("MAIN:");
        verify(viewManualOutputPort).showViewedManual(anyString());
    }

}//ViewManualTest
