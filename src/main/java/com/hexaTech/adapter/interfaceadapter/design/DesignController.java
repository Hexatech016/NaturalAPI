package com.hexaTech.adapter.interfaceadapter.design;

import com.hexaTech.domain.port.in.AddBOInputPort;
import com.hexaTech.domain.port.in.AddGherkinInputPort;
import com.hexaTech.domain.port.in.CreateBALInputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DesignController {

    private final AddGherkinInputPort addGherkinInputPort;

    private final CreateBALInputPort createBALInputPort;

    private final AddBOInputPort addBOInputPort;

    @Autowired
    public DesignController(AddGherkinInputPort
            addGherkinInputPort, CreateBALInputPort createBALInputPort, AddBOInputPort addBOInputPort) {
        this.addGherkinInputPort = addGherkinInputPort;
        this.createBALInputPort = createBALInputPort;
        this.addBOInputPort = addBOInputPort;
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
    }

    public void createBO(String directory,String document) throws IOException {
        addBOInputPort.addBO(directory,document);
    }

    public void checkIfHasMethod(int sentinel){
        createBALInputPort.hasMethod(sentinel);
    }

    public void existsGherkin(String path){
        addGherkinInputPort.existsDoc(path);
    }

    public void showMethod(int sentinel){
        createBALInputPort.showMethod(sentinel);
    }

    public void alterMethodReturn(int sentinel,String type,boolean isArray,boolean isObject){
        createBALInputPort.alterMethod(sentinel,type,isArray,isObject);
    }

    public void checkIfHasParameter(int sentinel,int identifier){
        createBALInputPort.hasParameter(sentinel,identifier);
    }

    public void showParameter(int sentinel,int identifier){
        createBALInputPort.showParameter(sentinel,identifier);
    }

    public void alterParameterType(int sentinel,int identifier,String type,boolean isArray,boolean isObject){
        createBALInputPort.alterParameter(sentinel,identifier,type,isArray,isObject);
    }

    public void updateBAL(String nameBAL) throws IOException {
        createBALInputPort.updateBAL(nameBAL);
    }

    public void showObjects(){
        createBALInputPort.showObjects();
    }

    public void chooseObject(int position){
        createBALInputPort.chooseObject(position);
    }

    public void addStructure(String structName,String paramName,String paramType){
        createBALInputPort.addObject(structName,paramName,paramType);
    }

}//DesignController
