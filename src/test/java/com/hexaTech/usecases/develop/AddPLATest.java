package com.hexaTech.usecases.develop;

import com.hexaTech.adapter.interfaceadapter.develop.DevelopPresenter;
import com.hexaTech.domain.entity.Document;
import com.hexaTech.domain.entity.PLA;
import com.hexaTech.domain.port.out.usecase.AddPLAOutputPort;
import com.hexaTech.domain.port.out.repository.RepoPLAInterface;
import com.hexaTech.domain.usecase.develop.AddPLA;
import com.hexaTech.adapter.repository.RepoPLA;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AddPLATest{

    @Mock
    RepoPLAInterface repoPLAInterface;

    @Mock
    AddPLAOutputPort addPLAOutputPort;

    @InjectMocks
    AddPLA addPLA;

    @Before
    public void before(){
        addPLAOutputPort=Mockito.spy(new DevelopPresenter());
        repoPLAInterface=Mockito.spy(new RepoPLA());
        addPLA=new AddPLA(addPLAOutputPort,repoPLAInterface);
    }

    @Test
    public void constructorTest(){
        assertNotNull(addPLA);
    }

    @Test
    public void addPLATest() throws IOException {
        Document pla=new Document("pla.pla",".\\src\\main\\resources\\testFiles\\pla.pla");
        addPLA.addPLA("Develop",".\\src\\main\\resources\\testFiles\\pla.pla");
        assertEquals(repoPLAInterface.getPLA().getPath(), pla.getPath());
    }

    @Test
    public void addPLANullTest() throws IOException {
        addPLA.addPLA("Develop","");
        verify(addPLAOutputPort).showDone(false);
    }

    @Test(expected=FileNotFoundException.class)
    public void updatePLAExceptionTest() throws IOException {
        addPLA.updatePLA(new PLA("newPath","newExt").getPath());
    }

    @Test
    public void updatePLATest() throws IOException {
        PLA test=new PLA(".\\src\\main\\resources\\testFiles\\pla.pla","plaTest");
        addPLA.updatePLA(test.getPath());
        assertEquals(".\\src\\main\\resources\\testFiles\\pla.pla",repoPLAInterface.getPLA().getPath());
        assertEquals("plaTest",repoPLAInterface.getPLA().getExtension());
    }

    @Test
    public void existsDocTrueTest(){
        addPLA.existsDoc(".\\src\\main\\resources\\testFiles\\pla.pla");
        verify(addPLAOutputPort).showDone(true);
    }

    @Test
    public void existsDocFalseTest(){
        addPLA.existsDoc("");
        verify(addPLAOutputPort).showDone(false);
    }

    @Test
    public void deleteDocTest(){
        addPLA.deleteDoc("path");
        verify(repoPLAInterface).deleteDoc("path");
        verify(addPLAOutputPort).showRemovedPLA("Error. Please retry.");
    }

}//AddPLATest
