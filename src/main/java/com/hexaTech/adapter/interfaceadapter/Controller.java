package com.hexaTech.adapter.interfaceadapter;

import com.hexaTech.domain.port.in.*;
import net.didion.jwnl.JWNLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Controller {
    /* ************************ DISCOVER ************************ */

    private final AddDocumentInputPort addDocumentInputPort;

    private final CreateBDLInputPort createBDLInputPort;

    private final CheckBetweenBDLAndGherkinInputPort checkBetweenBDLAndGherkinInputPort;
    /* ************************ DESIGN ************************ */

    private final AddBDLInputPort addBDLInputPort;

    private final AddGherkinInputPort addGherkinInputPort;

    private final CreateBALInputPort createBALInputPort;

    private final AddBOInputPort addBOInputPort;
    /* ************************ DEVELOP ************************ */
    private final AddPLAInputPort addPLAInputPort;

    private final AddBALInputPort addBALInputPort;

    private final CreateAPIInputPort createAPIInputPort;

    @Autowired
    public Controller(AddDocumentInputPort addDocumentInputPort, CreateBDLInputPort createBDLInputPort,
                      CheckBetweenBDLAndGherkinInputPort checkBetweenBDLAndGherkinInputPort,
                      AddBDLInputPort addBDLInputPort, AddGherkinInputPort addGherkinInputPort,
                      CreateBALInputPort createBALInputPort, AddBOInputPort addBOInputPort,
                      AddPLAInputPort addPLAInputPort, AddBALInputPort addBALInputPort,
                      CreateAPIInputPort createAPIInputPort) {
        this.addDocumentInputPort = addDocumentInputPort;
        this.createBDLInputPort = createBDLInputPort;
        this.checkBetweenBDLAndGherkinInputPort = checkBetweenBDLAndGherkinInputPort;
        this.addBDLInputPort = addBDLInputPort;
        this.addGherkinInputPort = addGherkinInputPort;
        this.createBALInputPort = createBALInputPort;
        this.addBOInputPort=addBOInputPort;
        this.addPLAInputPort = addPLAInputPort;
        this.addBALInputPort = addBALInputPort;
        this.createAPIInputPort = createAPIInputPort;
    }

    /* ************************ DISCOVER ************************ */

    /**
     * Invokes AddDocToParse method to add a new document.
     * @throws IOException if the document to add doesn't exist.
     */
    public void addTextDoc(String directory,String document) throws IOException {
        addDocumentInputPort.addDocument(directory,document);
    }

    /**
     * Invokes AddDocToParse method to restore a backup file.
     * @throws IOException if the backup file doesn't exist.
     */
    public void restoreTextDoc(String directory) throws IOException {
        addDocumentInputPort.loadBackUp(directory);
    }

    /**
     * Invokes CreateBDL method to create a new BDL object;
     * @param BDLpath String - path to the BDL.
     * @throws IOException if BDL can't be created from repo's stored document.
     */
    public void createBDL(String BDLpath) throws IOException {
        createBDLInputPort.createBDL(BDLpath);
    }

    /**
     * Invokes DeleteDoc method to delete a document.
     * @param path string - path to the file to be removed.
     */
    public void deleteTextDoc(String path){
        addDocumentInputPort.deleteDocs(path);
    }

    /**
     * Invokes CheckThereAreDoc method to check the presence of a stored document.
     */
    public void existsDoc(String path) {
        addDocumentInputPort.checkThereAreDoc(path);
    }

    public void checkIfRepoBDLIsEmpty(){
        addBDLInputPort.checkIfRepoBDLIsEmpty();
    }

    public void checkBetweenBDLAndGherkin(String directory) throws IOException, JWNLException {
        checkBetweenBDLAndGherkinInputPort.check(directory);
    }

    /* ************************ DESIGN ************************ */

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
    public void createBAL() throws IOException {
        createBALInputPort.createBAL();
        //addBOInputPort.addBO();
    }

    public void createBO(String directory,String document) throws IOException {
        addBOInputPort.addBO(directory,document);
    }

    public void checkSuggestions() throws IOException {
        createBALInputPort.checkTypes();
    }

    public void existsGherkin(String path){
        addGherkinInputPort.existsDoc(path);
    }

    /**
     * Invokes AddPLA method to add a new document.
     * @throws IOException if the document to add doesn't exist.
     */

    /* ************************ DEVELOP ************************ */

    public void addBAL(String directory,String document) throws IOException {
        addBALInputPort.addBAL(directory,document);
    }

    /**
     * Invokes AddPLA method to restore backup.
     * @throws IOException if the document to restore doesn't exist.
     */
    public void restoreBAL(String directory) throws IOException{
        addBALInputPort.loadBackUp(directory);
    }

    /**
     * Invokes DeleteDocument method to delete a document.
     * @param path string - path to the file to be removed.
     */
    public void deleteBAL(String path){
        addBALInputPort.deleteDoc(path);
    }

    /**
     * Invokes CreateAPI method to create a new API object;
     * @throws IOException if API can't be created to repo's stored document.
     */
    public void createAPI() throws IOException {
        createAPIInputPort.createAPI();
    }

    /**
     * Invokes AddPLA method to add a PLA file.
     * @throws IOException if the specified file doesn't exist.
     */
    public void addPLA(String directory,String document) throws IOException{
        addPLAInputPort.addPLA(directory,document);
    }

    /**
     * Invokes AddPLA method to change PLA file source.
     * @param path string - PLA source file path.
     * @throws IOException if the specified path doesn't exist.
     */
    public void refreshPLA(String path) throws IOException{
        addPLAInputPort.updatePLA(path);
    }

    public void existsBAL(String path){
        addBALInputPort.existsDoc(path);
    }

}
