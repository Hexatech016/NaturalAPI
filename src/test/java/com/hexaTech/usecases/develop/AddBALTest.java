package com.hexaTech.usecases.develop;

import com.hexaTech.entities.Document;
import com.hexaTech.interactor.usecases.develop.AddBALOutputPort;
import com.hexaTech.interactor.repositoriesInterface.RepoBALDocumentInterface;
import com.hexaTech.interactor.usecases.develop.AddBAL;
import com.hexaTech.repositories.RepoBALDocument;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.*;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AddBALTest{

    @Mock
    AddBALOutputPort addBALOutputPort;

    @Mock
    RepoBALDocumentInterface repoBALDocumentInterface;

    @InjectMocks
    AddBAL addBAL;

    @Before
    public void before(){
        repoBALDocumentInterface=Mockito.spy(new RepoBALDocument());
        addBAL=new AddBAL(addBALOutputPort, repoBALDocumentInterface);
    }

    @Test
    public void constructorTest(){
        assertNotNull(addBAL);
    }

    @Test
    public void addBALTest() throws IOException {
        Document bal=new Document("balOA.json",".\\src\\main\\resources\\testFiles\\balOA.json");
        addBAL.addBAL("Develop",".\\src\\main\\resources\\testFiles\\balOA.json");
        assertEquals(repoBALDocumentInterface.getBAL().getPath(), bal.getPath());
        verify(repoBALDocumentInterface).saveDoc(anyString(),anyString());
    }

    @Test
    public void addBALNullTest() throws IOException {
        addBAL.addBAL("Develop","");
        verify(addBALOutputPort).showDone(false);
    }

    @Test
    public void existsDocTrueTest(){
        addBAL.existsDoc(".\\src\\main\\resources\\testFiles\\balOA.json");
        verify(addBALOutputPort).showDone(true);
    }

    @Test
    public void existsDocFalseTest(){
        addBAL.existsDoc("");
        verify(addBALOutputPort).showDone(false);
    }

    @Test
    public void deleteDocTest(){
        addBAL.deleteDoc("path");
        verify(repoBALDocumentInterface).deleteDoc("path");
        verify(addBALOutputPort).showRemovedBAL(anyString());
    }

    @Test(expected=IOException.class)
    public void loadBackupFailTest() throws IOException {
        addBAL.loadBackUp("");
    }

    @Test
    public void loadBackupTest() throws IOException {
        addBAL.addBAL("Develop",".\\src\\main\\resources\\testFiles\\balOA.json");
        addBAL.loadBackUp("Develop");
        verify(repoBALDocumentInterface).loadBackup("Develop");
        verify(addBALOutputPort).showRestoredBackUp(anyString());
    }

}//AddBALTest
