package com.hexaTech.usecases.develop;

import com.hexaTech.entities.Document;
import com.hexaTech.interactor.usecases.design.AddBDLInputPort;
import com.hexaTech.interactor.usecases.design.AddBOInputPort;
import com.hexaTech.interactor.usecases.design.AddGherkinInputPort;
import com.hexaTech.interactor.usecases.design.CreateBALInputPort;
import com.hexaTech.interactor.usecases.develop.*;
import com.hexaTech.interactor.repositoriesInterface.RepoBALDocumentInterface;
import com.hexaTech.interactor.usecases.discover.AddDocumentInputPort;
import com.hexaTech.interactor.usecases.discover.CheckBetweenBDLAndGherkinInputPort;
import com.hexaTech.interactor.usecases.discover.CreateBDLInputPort;
import com.hexaTech.interfaceadapters.Controller;
import com.hexaTech.repositories.RepoBALDocument;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.*;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

import com.hexaTech.usecases.*;

@RunWith(MockitoJUnitRunner.class)
public class AddBALTest{

    @Mock
    AddBALOutputPort addBALOutputPort;

    @Mock
    RepoBALDocumentInterface repoBALDocumentInterface;

    @Mock
    Controller controller;

    @Mock
    AddBAL addBAL;

    @Before
    public void before(){
        repoBALDocumentInterface=Mockito.spy(new RepoBALDocument());
        addBAL=Mockito.spy(new AddBAL(addBALOutputPort, repoBALDocumentInterface));
        controller=new Controller(Mockito.mock(AddDocumentInputPort.class),Mockito.mock(CreateBDLInputPort.class),Mockito.mock(CheckBetweenBDLAndGherkinInputPort.class),
                Mockito.mock(AddBDLInputPort.class),Mockito.mock(AddGherkinInputPort.class),Mockito.mock(CreateBALInputPort.class),Mockito.mock(AddBOInputPort.class),
                Mockito.mock(AddPLAInputPort.class),addBAL,Mockito.mock(CreateAPIInputPort.class));
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

    @Test
    public void controllerAddBALTest() throws IOException {
        controller.addBAL(anyString(),anyString());
        verify(addBAL).addBAL(anyString(),anyString());
    }

}//AddBALTest
