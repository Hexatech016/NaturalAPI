package com.hexaTech.usecases.develop;

import com.hexaTech.frameworks.OpenAPI;
import com.hexaTech.interactor.entities.Document;
import com.hexaTech.interactor.entities.PLA;
import com.hexaTech.interactor.frameworksInterface.JsonParsingInterface;
import com.hexaTech.interactor.outputportInterface.CreateAPIOutputPort;
import com.hexaTech.interactor.repositoriesInterface.RepoAPIInterface;
import com.hexaTech.interactor.repositoriesInterface.RepoBALDocumentInterface;
import com.hexaTech.interactor.repositoriesInterface.RepoPLAInterface;
import com.hexaTech.interactor.usecases.develop.CreateAPI;
import com.hexaTech.repositories.RepoAPI;
import com.hexaTech.repositories.RepoBALDocument;
import com.hexaTech.repositories.RepoPLA;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CreateAPITest{

    @Mock
    CreateAPIOutputPort createAPIOutputPort;
    @Mock
    RepoPLAInterface repoPLAInterface;
    @Mock
    RepoBALDocumentInterface repoBALDocumentInterface;
    @Mock
    RepoAPIInterface repoAPIInterface;
    @Mock
    JsonParsingInterface jsonParsingInterface;
    @InjectMocks
    CreateAPI createAPI;

    @Before
    public void before(){
        repoAPIInterface=Mockito.spy(new RepoAPI());
        repoBALDocumentInterface=Mockito.spy(new RepoBALDocument());
        repoPLAInterface=Mockito.spy(new RepoPLA());
        jsonParsingInterface=Mockito.spy(new OpenAPI());
        createAPI=new CreateAPI(createAPIOutputPort,repoPLAInterface,repoBALDocumentInterface,repoAPIInterface,jsonParsingInterface);
    }

    @Test
    public void constructorTest(){
        assertNotNull(createAPI);
    }

    @Test
    public void createAPITest() throws IOException {
        repoPLAInterface.setPLA(new PLA(".\\src\\main\\resources\\testFiles\\pla.pla","plaTest"));
        repoBALDocumentInterface.setBAL(new Document("bal",".\\src\\main\\resources\\testFiles\\balOA.json"));
        createAPI.createAPI();
        verify(createAPIOutputPort).showErrorCodeAPI(0);
    }

    @Test
    public void createAPINotExistsPLATest() throws IOException {
        repoPLAInterface.setPLA(new PLA("plaFake.pla","pla"));
        createAPI.createAPI();
        verify(createAPIOutputPort).showErrorCodeAPI(1);
    }

    @Test
    public void createAPINotExistsBALTest() throws IOException {
        repoPLAInterface.setPLA(new PLA(".\\src\\main\\resources\\testFiles\\pla.pla","plaTest"));
        repoBALDocumentInterface.setBAL(new Document("balFake","balFake.bal"));
        createAPI.createAPI();
        verify(createAPIOutputPort).showErrorCodeAPI(2);
    }

    @Test
    public void createAPIBadSyntaxBALTest() throws IOException {
        repoPLAInterface.setPLA(new PLA(".\\src\\main\\resources\\testFiles\\pla.pla","plaTest"));
        repoBALDocumentInterface.setBAL(new Document("its_PLA",".\\src\\main\\resources\\testFiles\\pla.pla"));
        createAPI.createAPI();
        verify(createAPIOutputPort).showErrorCodeAPI(3);
    }

    @Test
    public void createAPIEmptyPLATest() throws IOException {
        repoPLAInterface.setPLA(new PLA(".\\src\\main\\resources\\testFiles\\emptyFile.txt","empty"));
        repoBALDocumentInterface.setBAL(new Document("bal",".\\src\\main\\resources\\testFiles\\balOA.json"));
        createAPI.createAPI();
        verify(createAPIOutputPort).showErrorCodeAPI(4);
    }

    @Test
    public void createAPIBadSyntaxPLATest() throws IOException {
        repoPLAInterface.setPLA(new PLA(".\\src\\main\\resources\\testFiles\\balOA.json","its_BAL"));
        repoBALDocumentInterface.setBAL(new Document("bal",".\\src\\main\\resources\\testFiles\\balOA.json"));
        createAPI.createAPI();
        verify(createAPIOutputPort).showErrorCodeAPI(4);
    }

}//CreateAPITes
