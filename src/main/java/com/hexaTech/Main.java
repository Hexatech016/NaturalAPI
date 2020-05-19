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
import com.hexaTech.portInterface.AddBALInputPort;
import com.hexaTech.portInterface.AddPLAInputPort;
import com.hexaTech.portInterface.CreateAPIInputPort;
import com.hexaTech.repo.*;
import com.hexaTech.repointerface.*;

import java.io.IOException;

/**
 * Class used to manage the flow of program execution.
 */
public class Main{
    public static void main(String[] args) throws IOException {
        //PRESENTERS
        Presenter presenter=new Presenter();
        //REPOS
            //DISCOVER
        RepoDocumentInterface repoDocument=new RepoDocument();
        RepoBDLInterface repoBDLInterface=new RepoBDL();
            //DESIGN
        RepoGherkin repoGherkin=new RepoGherkin();
            //DEVELOP
        RepoAPIInterface repoAPI=new RepoAPI();
        RepoBALInterface repoBAL=new RepoBAL();
        RepoPLAInterface repoPLA=new RepoPLA();
        //INTERACTORS
            //DISCOVER
        AddDocument addDocument =new AddDocument(presenter,repoDocument);
        CreateBDL createBDL=new CreateBDL(presenter,repoBDLInterface,repoDocument);
            //DESIGN
        AddGherkin addGherkin=new AddGherkin(presenter,repoGherkin);
        AddBDL addBDL=new AddBDL(presenter);
        CreateBAL createBAL=new CreateBAL(presenter,repoGherkin,repoBAL);
            //DEVELOP
        AddPLAInputPort addPLA=new AddPLA(presenter,repoPLA);
        AddBALInputPort addBAL=new AddBAL(presenter,repoBAL);
        CreateAPIInputPort createAPI=new CreateAPI(presenter,repoPLA,repoBAL,repoAPI);
        //CONTROLLERS
        ControllerDiscover controllerDiscover=new ControllerDiscover(addDocument,createBDL);
        ControllerDesign controllerDesign=new ControllerDesign(addBDL,addGherkin,createBAL);
        ControllerDevelop controllerDevelop=new ControllerDevelop(addPLA,addBAL,createAPI);
        //CLIENT
        CLI client=new CLI(controllerDiscover,controllerDesign,controllerDevelop,presenter);
        client.useCaseNaturalAPI();
    }//main
}//MainDevelop
