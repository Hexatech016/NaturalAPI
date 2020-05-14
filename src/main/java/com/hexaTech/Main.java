/**
 * @file main
 * @version 1.0.0
 * @type java
 * @data 2020-05-01
 * @author Jacopo Battilana
 * @email hexatech016@gmail.com
 * @license
 * @changeLog
 */

package com.hexaTech;

import com.hexaTech.client.CLI;
import com.hexaTech.controllerPresenter.*;
import com.hexaTech.interactor.*;
import com.hexaTech.model.ModelDesign;
import com.hexaTech.model.ModelDiscover;
import com.hexaTech.portInterface.AddBALInputPort;
import com.hexaTech.portInterface.AddPLAInputPort;
import com.hexaTech.portInterface.CreateAPIInputPort;
import com.hexaTech.repo.*;
import com.hexaTech.stanford.Stanford;
import com.hexaTech.fileSystem.FileSystem;

import java.io.IOException;

/**
 * Class used to manage the flow of program execution.
 */
public class Main{
    public static void main(String[] args) throws IOException {
        //PRESENTERS
        PresenterDiscover presenterDiscover=new PresenterDiscover();
        PresenterDesign presenterDesign=new PresenterDesign();
        PresenterDevelop presenterDevelop=new PresenterDevelop();
        //DA_RIMOUVERE
        FileSystem fileSystem=new FileSystem();
        //REPOS
            //DISCOVER
        RepoDiscoverInterface repoDiscover=new RepoDiscover(fileSystem);
        RepoDocumentInterface repoDocument=new RepoDocument();
            //DESIGN
        RepoDesignInterface repoDesign=new RepoDesign(fileSystem);
            //DEVELOP
        RepoAPIInterface repoAPI=new RepoAPI();
        RepoBALInterface repoBAL=new RepoBAL();
        RepoPLAInterface repoPLA=new RepoPLA();
        //DA_RIMUOVERE
        Stanford stanford=new Stanford();
        ModelDiscover modelDiscover=new ModelDiscover(stanford);
        ModelDesign modelDesign=new ModelDesign(stanford);
        //INTERACTORS
            //DISCOVER
        AddDocToParse addDocToParse=new AddDocToParse(presenterDiscover,repoDocument);
        CheckThereAreDoc checkThereAreDoc=new CheckThereAreDoc(presenterDiscover,repoDiscover);
        CreateBDL createBDL=new CreateBDL(presenterDiscover,repoDiscover,modelDiscover);
        DeleteDoc deleteDoc=new DeleteDoc(presenterDiscover,repoDiscover);
            //DESIGN
        AddGherkin addGherkin=new AddGherkin(presenterDesign,repoDesign,modelDesign);
        AddBDL addBDL=new AddBDL(presenterDesign,repoDesign);
        CreateBAL createBAL=new CreateBAL(presenterDesign,repoDesign,modelDesign);
            //DEVELOP
        AddPLAInputPort addPLA=new AddPLA(presenterDevelop,repoPLA);
        AddBALInputPort addBAL=new AddBAL(presenterDevelop,repoBAL);
        CreateAPIInputPort createAPI=new CreateAPI(presenterDevelop,repoPLA,repoBAL,repoAPI);
        //CONTROLLERS
        ControllerDiscover controllerDiscover=new ControllerDiscover(addDocToParse,createBDL,deleteDoc,checkThereAreDoc);
        ControllerDesign controllerDesign=new ControllerDesign(addBDL,addGherkin,createBAL);
        ControllerDevelop controllerDevelop=new ControllerDevelop(addPLA,addBAL,createAPI);
        //CLIENT
        CLI client=new CLI(controllerDiscover,presenterDiscover,controllerDesign,presenterDesign,controllerDevelop,presenterDevelop);
        client.useCaseNaturalAPI();
    }//main
}//MainDevelop
