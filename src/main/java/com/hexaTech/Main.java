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
import com.hexaTech.model.ModelDevelop;
import com.hexaTech.model.ModelDiscover;
import com.hexaTech.repo.*;
import com.hexaTech.stanford.Stanford;
import com.hexaTech.swagger.Swagger;
import com.hexaTech.fileSystem.FileSystem;

import java.io.IOException;

/**
 * Class used to manage the flow of program execution.
 */
public class Main{
    public static void main(String[] args) throws IOException {

        PresenterDiscover presenterDiscover=new PresenterDiscover();
        PresenterDesign presenterDesign=new PresenterDesign();
        PresenterDevelop presenterDevelop=new PresenterDevelop();

        FileSystem fileSystem=new FileSystem();
        RepoDiscover repoDiscover=new RepoDiscover(fileSystem);
        RepoDesign repoDesign=new RepoDesign(fileSystem);

        RepoBAL repoBAL=new RepoBAL();
        RepoPLA repoPLA=new RepoPLA();
        RepoDocument repoDocument=new RepoDocument();
        Swagger swagger=new Swagger();
        Stanford stanford=new Stanford();

        ModelDiscover modelDiscover=new ModelDiscover(stanford);
        ModelDesign modelDesign=new ModelDesign(stanford);
        ModelDevelop modelDevelop=new ModelDevelop(swagger);
        AddDocToParse addDocToParse=new AddDocToParse(presenterDiscover,repoDocument);
        CheckThereAreDoc checkThereAreDoc=new CheckThereAreDoc(presenterDiscover,repoDiscover);
        CreateBDL createBDL=new CreateBDL(presenterDiscover,repoDiscover,modelDiscover);
        DeleteDoc deleteDoc=new DeleteDoc(presenterDiscover,repoDiscover);

        AddGherkin addGherkin=new AddGherkin(presenterDesign,repoDesign,modelDesign);
        AddBDL addBDL=new AddBDL(presenterDesign,repoDesign);
        CreateBAL createBAL=new CreateBAL(presenterDesign,repoDesign,modelDesign);

        AddDocument addDocument=new AddDocument(presenterDevelop,repoPLA,repoBAL);
        CreateAPI createAPI=new CreateAPI(presenterDevelop,repoPLA,repoBAL,modelDevelop);

        ControllerDiscover controllerDiscover=new ControllerDiscover(addDocToParse,createBDL,deleteDoc,checkThereAreDoc);
        ControllerDesign controllerDesign=new ControllerDesign(addBDL,addGherkin,createBAL);
        ControllerDevelop controllerDevelop=new ControllerDevelop(addDocument,createAPI);

        CLI client=new CLI(controllerDiscover,presenterDiscover,controllerDesign,presenterDesign,controllerDevelop,presenterDevelop);
        client.useCaseNaturalAPI();
    }//main
}//MainDevelop
