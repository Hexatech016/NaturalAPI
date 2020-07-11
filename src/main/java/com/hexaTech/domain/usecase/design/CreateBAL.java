/**
 * @file CreateBAL
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Denis Salviato
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.domain.usecase.design;

import com.hexaTech.domain.entity.*;
import com.hexaTech.domain.port.out.framework.TextsParsingInterface;
import com.hexaTech.domain.port.in.CreateBALInputPort;
import com.hexaTech.domain.port.out.usecase.CreateBALOutputPort;
import com.hexaTech.domain.port.out.repository.RepoBALDocumentInterface;
import com.hexaTech.domain.port.out.repository.RepoBALInterface;
import com.hexaTech.domain.port.out.repository.RepoBOInterface;
import com.hexaTech.domain.port.out.repository.RepoGherkinInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Class used to manage a BAL creation.
 */
@Component
public class CreateBAL implements CreateBALInputPort {
    private final CreateBALOutputPort createBALOutputPort;
    private final RepoGherkinInterface repoGherkinInterface;
    private final RepoBALDocumentInterface repoBALDocumentInterface;
    private final RepoBALInterface repoBALInterface;
    private final RepoBOInterface repoBOInterface;
    private final TextsParsingInterface textsParsingInterface;

    /**
     * CreateBAL class constructor.
     * @param createBALOutputPort CreateBALOutputPort - used to send output notifications.
     * @param repoGherkinInterface RepoInterface - used to communicate with repo.
     */
    @Autowired
    public CreateBAL(CreateBALOutputPort createBALOutputPort, RepoGherkinInterface repoGherkinInterface,
                     RepoBALDocumentInterface repoBALDocumentInterface, RepoBALInterface repoBALInterface,
                     RepoBOInterface repoBOInterface, TextsParsingInterface textsParsingInterface) {
        this.createBALOutputPort = createBALOutputPort;
        this.repoGherkinInterface = repoGherkinInterface;
        this.repoBALDocumentInterface = repoBALDocumentInterface;
        this.repoBALInterface = repoBALInterface;
        this.repoBOInterface = repoBOInterface;
        this.textsParsingInterface = textsParsingInterface;
    }

    /**
     * Create a new BAL object.
     * @throws IOException if an error occurs during loading process.
     */
    @Override
    public void createBAL(String nameBAL) throws IOException {
        String path=repoGherkinInterface.getGherkin().getPath();
        String document = repoGherkinInterface.getContentFromPath(path);
        List<Gherkin> gherkins = textsParsingInterface.extractFromGherkin(document);
        BAL bal=repoBALDocumentInterface.setBALFromGherkin(gherkins, nameBAL);
        bal.joinBO(repoBOInterface.getBoOpenAPI().getOntologyObjects());
        repoBALInterface.setBAL(bal);
        Document savedBAL = new Document();
        savedBAL.setPath("." + File.separator + "Design" + File.separator + nameBAL + "BAL.json");
        repoBALDocumentInterface.setBAL(savedBAL);
        repoBALDocumentInterface.saveBAL(bal,nameBAL);
        createBALOutputPort.showCreatedBAL("BAL created into folder: Design.");
    }//createBAL

    public void hasMethod(int sentinel){
        createBALOutputPort.showDone(repoBALInterface.hasMethod(sentinel));
    }//hasMethod

    public void showMethod(int sentinel){
        createBALOutputPort.showCreatedBAL(repoBALInterface.getMethod(sentinel));
    }//showMethod

    public void alterMethod(int sentinel,String type,boolean isArray,boolean isObject){
        if(isObject)
            type=repoBALInterface.getObjectType(Integer.parseInt(type)-1);
        if(isArray)
            type=type+"[]";
        repoBALInterface.alterMethod(sentinel,type);
    }//alterMethod

    public void hasParameter(int sentinel,int identifier){
        createBALOutputPort.showDone(repoBALInterface.hasParameter(sentinel,identifier));
    }

    public void showParameter(int sentinel,int identifier){
        createBALOutputPort.showCreatedBAL(repoBALInterface.getParameter(sentinel,identifier));
    }

    public void alterParameter(int sentinel,int identifier,String type,boolean isArray,boolean isObject){
        if(isObject)
            type=repoBALInterface.getObjectType(Integer.parseInt(type)-1);
        if(isArray)
            type=type+"[]";
        repoBALInterface.alterParameter(sentinel,identifier,type);
    }

    public void showObjects(){
        createBALOutputPort.showCreatedBAL(repoBALInterface.getObjects());
    }

    public void chooseObject(int position){
        createBALOutputPort.showDone(repoBALInterface.hasObject(position-1));
    }

    public void addObject(String structName,String paramName,String paramType){
        repoBALInterface.addObject(structName,paramName,paramType);
    }

    public void updateBAL(String nameBAL) throws IOException {
        BAL bal=repoBALInterface.getBAL();
        Document savedBAL=new Document();
        savedBAL.setPath("." + File.separator + "Design" + File.separator + nameBAL + "BAL.json");
        repoBALDocumentInterface.setBAL(savedBAL);
        repoBALDocumentInterface.saveBAL(bal,nameBAL);
        createBALOutputPort.showCreatedBAL("BAL updated into folder: Design.");
        repoGherkinInterface.deleteDoc("." + File.separator + "Design" + File.separator + "BackupGherkin.txt");
        repoBALDocumentInterface.openFile("." + File.separator + "Design" + File.separator + nameBAL + "BAL.json");
    }

}//CreateBAL
