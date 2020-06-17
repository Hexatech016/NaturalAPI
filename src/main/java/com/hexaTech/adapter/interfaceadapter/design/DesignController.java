package com.hexaTech.adapter.interfaceadapter.design;

import com.hexaTech.domain.port.in.AddBDLInputPort;
import com.hexaTech.domain.port.in.AddBOInputPort;
import com.hexaTech.domain.port.in.AddGherkinInputPort;
import com.hexaTech.domain.port.in.CreateBALInputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DesignController {
    private final AddBDLInputPort addBDLInputPort;

    private final AddGherkinInputPort addGherkinInputPort;

    private final CreateBALInputPort createBALInputPort;

    private final AddBOInputPort addBOInputPort;

    @Autowired
    public DesignController(AddBDLInputPort addBDLInputPort, AddGherkinInputPort
            addGherkinInputPort, CreateBALInputPort createBALInputPort, AddBOInputPort addBOInputPort) {
        this.addBDLInputPort = addBDLInputPort;
        this.addGherkinInputPort = addGherkinInputPort;
        this.createBALInputPort = createBALInputPort;
        this.addBOInputPort = addBOInputPort;
    }

    /**
     * Invokes AddBDL method to add a BDL.
     * @throws IOException if the document to add doesn't exist.
     */
    public void addBDL(String document) throws IOException {
        addBDLInputPort.addBDL(document);
    }

    /**
     * Invokes AddGherkin method to add a new scenario.
     * @throws IOException if the document to add doesn't exist.
     */
    public void addGherkin(String directory,String document) throws IOException {
        addGherkinInputPort.addGherkin(directory,document);
    }

    /**
     * Invokes AddPLA method to restore backup.
     * @throws IOException if the document to restore doesn't exist.
     */
    public void restoreBackup(String directory) throws IOException{
        addGherkinInputPort.loadBackUp(directory);
    }

    /**
     * Invokes DeleteDocument method to delete a document.
     * @param path string - path to the file to be removed.
     */
    public void deleteGherkin(String path){
        addGherkinInputPort.deleteDoc(path);
    }

    /**
     * Invokes CreateBAL method to create a new BAL object.
     * @throws IOException if the document to add doesn't exist.
     */
    public void createBAL(String name) throws IOException {
        createBALInputPort.createBAL(name);
        //addBOInputPort.addBO();
    }

    public void createBO(String directory,String document) throws IOException {
        addBOInputPort.addBO(directory,document);
    }

    public void checkSuggestions(String nameBAL) throws IOException {
        createBALInputPort.checkTypes(nameBAL);
    }

    public void existsGherkin(String path){
        addGherkinInputPort.existsDoc(path);
    }


}
