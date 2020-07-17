package com.hexaTech.usecases.discover;

import com.hexaTech.adapter.interfaceadapter.discover.DiscoverPresenter;
import com.hexaTech.adapter.repository.RepoDocument;
import com.hexaTech.domain.port.out.repository.RepoDocumentInterface;
import com.hexaTech.domain.port.out.usecase.AddDocumentOutputPort;
import com.hexaTech.domain.usecase.discover.AddDocument;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class AddDocumentTest {
    @InjectMocks
    AddDocument addDocument;

    @Mock
    AddDocumentOutputPort addDocumentOutputPort;
    @Mock
    RepoDocumentInterface repoDocumentInterface;

    @Before
    public void before(){
        addDocumentOutputPort=Mockito.spy(new DiscoverPresenter());
        repoDocumentInterface=Mockito.spy(new RepoDocument());
        addDocument=new AddDocument(addDocumentOutputPort,repoDocumentInterface);
    }

    @Test
    public void checkThereAreDocTest(){
        addDocument.checkThereAreDoc("test");
        verify(addDocumentOutputPort).showAddDocument(false);
    }

    @Test
    public void deleteDocsTest(){
        addDocument.deleteDocs("path");
        verify(repoDocumentInterface).deleteDoc("path");
        verifyZeroInteractions(addDocumentOutputPort);
    }

    @Test(expected=IOException.class)
    public void loadBackupFailTest() throws IOException {
        addDocument.loadBackUp("");
    }

    @Test
    public void isRepoEmptyTest(){
        addDocument.isRepoEmpty();
        verify(addDocumentOutputPort).showAddDocument(true);
    }

     @Test
    public void deleteDocTest(){
        addDocument.deleteDoc(0);
        verify(addDocumentOutputPort).showAddDocument(false);
     }

}//AddDocumentTest
