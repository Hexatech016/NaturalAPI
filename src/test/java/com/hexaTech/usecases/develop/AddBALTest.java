package com.hexaTech.usecases.develop;

import com.hexaTech.adapter.interfaceadapter.design.DesignController;
import com.hexaTech.adapter.interfaceadapter.develop.DevelopController;
import com.hexaTech.adapter.interfaceadapter.discover.DiscoverController;
import com.hexaTech.domain.entity.Document;
import com.hexaTech.domain.port.in.*;
import com.hexaTech.domain.usecase.develop.AddBAL;
import com.hexaTech.domain.port.out.usecase.AddBALOutputPort;
import com.hexaTech.domain.port.out.repository.RepoBALDocumentInterface;
import com.hexaTech.adapter.repository.RepoBALDocument;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AddBALTest{

    @Mock
    AddBALOutputPort addBALOutputPort;

    @Mock
    RepoBALDocumentInterface repoBALDocumentInterface;

    @Mock
    DiscoverController discoverController;

    @Mock
    DesignController designController;

    @Mock
    DevelopController developController;

    @Mock
    AddBAL addBAL;

    @Before
    public void before(){
        repoBALDocumentInterface=Mockito.spy(new RepoBALDocument());
        addBAL=Mockito.spy(new AddBAL(addBALOutputPort, repoBALDocumentInterface));
        discoverController =new DiscoverController(
                Mockito.mock(AddDocumentInputPort.class),
                Mockito.mock(CreateBDLInputPort.class),
                Mockito.mock(CheckBetweenBDLAndGherkinInputPort.class),
                Mockito.mock(AddBDLToDiscoverInputPort.class),
                Mockito.mock(AddGherkinToDiscoverInputPort.class));
        designController= new DesignController(Mockito.mock(AddBDLInputPort.class),
                Mockito.mock(AddGherkinInputPort.class),
                Mockito.mock(CreateBALInputPort.class),
                Mockito.mock(AddBOInputPort.class));
        developController= new DevelopController(Mockito.mock(AddPLAInputPort.class),addBAL,
                Mockito.mock(CreateAPIInputPort.class));
    }

    @Test
    public void constructorTest(){
        assertNotNull(addBAL);
    }

    @Test
    public void addBALTest() throws IOException {
        Document bal=new Document("balOA.json","." + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "testFiles" + File.separator + "balOA.json");
        addBAL.addBAL("Develop","." + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "testFiles" + File.separator + "balOA.json");
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
        addBAL.existsDoc("." + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "testFiles" + File.separator + "balOA.json");
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
        addBAL.addBAL("Develop","." + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "testFiles" + File.separator + "balOA.json");
        addBAL.loadBackUp("Develop");
        verify(repoBALDocumentInterface).loadBackup("Develop");
        verify(addBALOutputPort).showRestoredBackUp(anyString());
    }

    @Test
    public void controllerAddBALTest() throws IOException {
        developController.addBAL(anyString(),anyString());
        verify(addBAL).addBAL(anyString(),anyString());
    }

}//AddBALTest
