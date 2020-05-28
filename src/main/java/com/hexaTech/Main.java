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
import com.hexaTech.frameworks.StanfordNLP;
import com.hexaTech.frameworks.WordNet;
import com.hexaTech.interactor.frameworksInterface.TextsParsingInterface;
import com.hexaTech.interactor.frameworksInterface.WordParsingInterface;
import com.hexaTech.interactor.portInterface.*;
import com.hexaTech.interactor.repositoriesInterface.*;
import com.hexaTech.interactor.usecases.discover.CheckBetweenBDLAndGherkinInputPort;
import com.hexaTech.repositories.*;
import com.hexaTech.interactor.usecases.design.AddBDL;
import com.hexaTech.interactor.usecases.design.AddGherkin;
import com.hexaTech.interactor.usecases.design.CreateBO;
import com.hexaTech.interactor.usecases.develop.AddBAL;
import com.hexaTech.interactor.usecases.develop.AddPLA;
import com.hexaTech.interactor.usecases.develop.CreateAPI;
import com.hexaTech.interactor.usecases.design.CreateBAL;
import com.hexaTech.interactor.usecases.discover.AddDocument;
import com.hexaTech.interactor.usecases.discover.CheckBetweenBDLAndGherkin;
import com.hexaTech.interactor.usecases.discover.CreateBDL;
import net.didion.jwnl.JWNLException;

import java.io.IOException;

/**
 * Class used to manage the flow of program execution.
 */
public class Main{
    public static void main(String[] args) throws IOException, JWNLException {

        //PRESENTER
        Presenter presenter=new Presenter();
        //FRAMEWORK
        WordParsingInterface wordNet =new WordNet();
        TextsParsingInterface stanfordNLP=new StanfordNLP();
        //REPOS
            //DISCOVER
        RepoDocumentInterface repoDocument=new RepoDocument();
        RepoBDLInterface repoBDLInterface=new RepoBDL();
            //DESIGN
        RepoBDLInterface repoBDL=new RepoBDL();
        RepoGherkinInterface repoGherkin=new RepoGherkin();
        RepoBOInterface repoBO=new RepoBO();
            //DEVELOP
        RepoAPIInterface repoAPI=new RepoAPI();
        RepoBALInterface repoBAL=new RepoBAL();
        RepoBALDocumentInterface repoBALDocument=new RepoBALDocument();
        RepoPLAInterface repoPLA=new RepoPLA();
        //INTERACTORS
            //DISCOVER
        AddDocumentInputPort addDocument =new AddDocument(presenter,repoDocument);
        CreateBDLInputPort createBDL=new CreateBDL(presenter,repoBDLInterface,repoDocument,stanfordNLP);
        CheckBetweenBDLAndGherkinInputPort checkBetweenBDLAndGherkin=new CheckBetweenBDLAndGherkin(repoBDLInterface,repoGherkin, wordNet,stanfordNLP);
            //DESIGN
        AddGherkinInputPort addGherkin=new AddGherkin(presenter,repoGherkin);
        AddBDLInputPort addBDL=new AddBDL(presenter,repoBDL);
        CreateBOInputPort createBO= new CreateBO(presenter, repoBO);
        CreateBALInputPort createBAL=new CreateBAL(presenter,repoGherkin, repoBALDocument, repoBAL, repoBO, repoBDL,stanfordNLP);
            //DEVELOP
        AddPLAInputPort addPLA=new AddPLA(presenter,repoPLA);
        AddBALInputPort addBAL=new AddBAL(presenter, repoBALDocument);
        CreateAPIInputPort createAPI=new CreateAPI(presenter,repoPLA, repoBALDocument,repoAPI);
        //CONTROLLER
        Controller controller=new Controller(addDocument,createBDL,checkBetweenBDLAndGherkin,
                addBDL,addGherkin,createBAL, createBO,
                addPLA,addBAL,createAPI);
        //CLIENT
        CLI client=new CLI(controller,presenter);
        client.useCaseNaturalAPI();
    }//main
}//MainDevelop
