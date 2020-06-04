package com.hexaTech.adapter.interfaceadapter.discover;

import com.hexaTech.domain.port.in.*;
import net.didion.jwnl.JWNLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DiscoverController {
    private final AddDocumentInputPort addDocumentInputPort;

    private final CreateBDLInputPort createBDLInputPort;

    private final CheckBetweenBDLAndGherkinInputPort checkBetweenBDLAndGherkinInputPort;

    @Autowired
    public DiscoverController(AddDocumentInputPort addDocumentInputPort, CreateBDLInputPort createBDLInputPort,
                              CheckBetweenBDLAndGherkinInputPort checkBetweenBDLAndGherkinInputPort) {
        this.addDocumentInputPort = addDocumentInputPort;
        this.createBDLInputPort = createBDLInputPort;
        this.checkBetweenBDLAndGherkinInputPort = checkBetweenBDLAndGherkinInputPort;
    }

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

    public void checkBetweenBDLAndGherkin(String directory) throws IOException, JWNLException {
        checkBetweenBDLAndGherkinInputPort.check(directory);
    }




}
