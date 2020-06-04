package com.hexaTech.adapter.interfaceadapter;

import com.hexaTech.domain.port.in.AddBALInputPort;
import com.hexaTech.domain.port.in.AddPLAInputPort;
import com.hexaTech.domain.port.in.CreateAPIInputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DevelopController {
    private final AddPLAInputPort addPLAInputPort;

    private final AddBALInputPort addBALInputPort;

    private final CreateAPIInputPort createAPIInputPort;

    @Autowired
    public DevelopController(AddPLAInputPort addPLAInputPort, AddBALInputPort addBALInputPort,
                             CreateAPIInputPort createAPIInputPort) {
        this.addPLAInputPort = addPLAInputPort;
        this.addBALInputPort = addBALInputPort;
        this.createAPIInputPort = createAPIInputPort;
    }


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
