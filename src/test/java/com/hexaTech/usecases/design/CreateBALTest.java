package com.hexaTech.usecases.design;

import com.hexaTech.adapter.framework.StanfordNLP;
import com.hexaTech.adapter.interfaceadapter.design.DesignPresenter;
import com.hexaTech.adapter.repository.RepoBAL;
import com.hexaTech.adapter.repository.RepoBALDocument;
import com.hexaTech.adapter.repository.RepoBO;
import com.hexaTech.adapter.repository.RepoGherkin;
import com.hexaTech.domain.entity.BAL;
import com.hexaTech.domain.entity.Document;
import com.hexaTech.domain.entity.MethodBAL;
import com.hexaTech.domain.entity.Parameter;
import com.hexaTech.domain.port.out.framework.TextsParsingInterface;
import com.hexaTech.domain.port.out.repository.RepoBALDocumentInterface;
import com.hexaTech.domain.port.out.repository.RepoBALInterface;
import com.hexaTech.domain.port.out.repository.RepoBOInterface;
import com.hexaTech.domain.port.out.repository.RepoGherkinInterface;
import com.hexaTech.domain.port.out.usecase.CreateBALOutputPort;
import com.hexaTech.domain.usecase.design.CreateBAL;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CreateBALTest {
    @InjectMocks
    CreateBAL createBAL;

    @Mock
    CreateBALOutputPort createBALOutputPort;
    @Mock
    RepoGherkinInterface repoGherkinInterface;
    @Mock
    RepoBALDocumentInterface repoBALDocumentInterface;
    @Mock
    RepoBALInterface repoBALInterface;
    @Mock
    RepoBOInterface repoBOInterface;
    @Mock
    TextsParsingInterface textsParsingInterface;

    @Before
    public void before(){
        createBALOutputPort=Mockito.spy(new DesignPresenter());
        repoGherkinInterface=Mockito.spy(new RepoGherkin());
        repoBALDocumentInterface=Mockito.spy(new RepoBALDocument());
        repoBALInterface=Mockito.spy(new RepoBAL());
        repoBOInterface=Mockito.spy(new RepoBO());
        textsParsingInterface=Mockito.spy(new StanfordNLP());
        createBAL=new CreateBAL(createBALOutputPort,repoGherkinInterface,
                repoBALDocumentInterface,repoBALInterface,repoBOInterface,textsParsingInterface);
    }

    @Test(expected=NullPointerException.class)
    public void createBALExceptionTest() throws IOException {
        createBAL.createBAL("test");
    }

    @Test
    public void createBALTest() throws IOException {
        repoGherkinInterface.setGherkin(new Document("title","." + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "testFiles" + File.separator + "pla.pla"));
        createBAL.createBAL("test");
        verify(repoBALInterface).setBAL(any());
    }

    @Test
    public void hasMethodTest(){
        BAL bal=new BAL();
        List<MethodBAL> list=new ArrayList<>();
        MethodBAL methodBAL=new MethodBAL();
        list.add(methodBAL);
        bal.setMethods(list);
        repoBALInterface.setBAL(bal);
        createBAL.hasMethod(0);
        verify(createBALOutputPort).showDone(true);
    }

    @Test
    public void showMethodTest(){
        BAL bal=new BAL();
        List<MethodBAL> list=new ArrayList<>();
        MethodBAL methodBAL=new MethodBAL();
        list.add(methodBAL);
        bal.setMethods(list);
        repoBALInterface.setBAL(bal);
        createBAL.showMethod(0);
        verify(createBALOutputPort).showCreatedBAL(anyString());
    }

    @Test
    public void alterMethodArrayTest(){
        BAL bal=new BAL();
        List<MethodBAL> list=new ArrayList<>();
        MethodBAL methodBAL=new MethodBAL();
        list.add(methodBAL);
        bal.setMethods(list);
        repoBALInterface.setBAL(bal);
        createBAL.alterMethod(0,"type",true,false);
        verify(repoBALInterface).alterMethod(0,"type[]");
    }

    @Test
    public void alterMethodTest(){
        BAL bal=new BAL();
        List<MethodBAL> list=new ArrayList<>();
        MethodBAL methodBAL=new MethodBAL();
        list.add(methodBAL);
        bal.setMethods(list);
        repoBALInterface.setBAL(bal);
        createBAL.alterMethod(0,"type",false,false);
        verify(repoBALInterface).alterMethod(0,"type");
    }

    @Test
    public void hasParameterTest(){
        BAL bal=new BAL();
        List<MethodBAL> list=new ArrayList<>();
        List<Parameter> listParam=new ArrayList<>();
        listParam.add(new Parameter());
        MethodBAL methodBAL=new MethodBAL();
        methodBAL.setParameters(listParam);
        list.add(methodBAL);
        bal.setMethods(list);
        repoBALInterface.setBAL(bal);
        createBAL.hasParameter(0,0);
        verify(createBALOutputPort).showDone(true);
    }

    @Test
    public void showParameterTest(){
        BAL bal=new BAL();
        List<MethodBAL> list=new ArrayList<>();
        List<Parameter> listParam=new ArrayList<>();
        listParam.add(new Parameter());
        MethodBAL methodBAL=new MethodBAL();
        methodBAL.setParameters(listParam);
        list.add(methodBAL);
        bal.setMethods(list);
        repoBALInterface.setBAL(bal);
        createBAL.showParameter(0,0);
        verify(createBALOutputPort).showCreatedBAL(anyString());
    }

    @Test
    public void alterParameterArrayTest(){
        BAL bal=new BAL();
        List<MethodBAL> list=new ArrayList<>();
        List<Parameter> listParam=new ArrayList<>();
        listParam.add(new Parameter());
        MethodBAL methodBAL=new MethodBAL();
        methodBAL.setParameters(listParam);
        list.add(methodBAL);
        bal.setMethods(list);
        repoBALInterface.setBAL(bal);
        createBAL.alterParameter(0,0,"type",true,false);
        verify(repoBALInterface).alterParameter(0,0,"type[]");
    }

    @Test
    public void alterParameterTest(){
        BAL bal=new BAL();
        List<MethodBAL> list=new ArrayList<>();
        List<Parameter> listParam=new ArrayList<>();
        listParam.add(new Parameter());
        MethodBAL methodBAL=new MethodBAL();
        methodBAL.setParameters(listParam);
        list.add(methodBAL);
        bal.setMethods(list);
        repoBALInterface.setBAL(bal);
        createBAL.alterParameter(0,0,"type",false,false);
        verify(repoBALInterface).alterParameter(0,0,"type");
    }

}//CreateBALTest
