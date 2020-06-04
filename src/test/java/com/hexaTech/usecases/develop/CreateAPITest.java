package com.hexaTech.usecases.develop;

import com.hexaTech.adapter.framework.OpenAPI;
import com.hexaTech.domain.entity.Document;
import com.hexaTech.domain.entity.PLA;
import com.hexaTech.domain.port.out.framework.JsonParsingInterface;
import com.hexaTech.domain.port.out.usecase.CreateAPIOutputPort;
import com.hexaTech.domain.port.out.repository.RepoAPIInterface;
import com.hexaTech.domain.port.out.repository.RepoBALDocumentInterface;
import com.hexaTech.domain.port.out.repository.RepoPLAInterface;
import com.hexaTech.domain.usecase.develop.CreateAPI;
import com.hexaTech.adapter.repository.RepoAPI;
import com.hexaTech.adapter.repository.RepoBALDocument;
import com.hexaTech.adapter.repository.RepoPLA;
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
