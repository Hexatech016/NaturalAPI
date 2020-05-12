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

package com.HexaTech;

import com.HexaTech.client.CLI;
import com.HexaTech.controllerPresenter.*;
import com.HexaTech.interactor.*;
import com.HexaTech.model.ModelDesign;
import com.HexaTech.model.ModelDevelop;
import com.HexaTech.model.ModelDiscover;
import com.HexaTech.repo.RepoDesign;
import com.HexaTech.repo.RepoDevelop;
import com.HexaTech.repo.RepoDiscover;
import com.HexaTech.stanford.Stanford;
import com.HexaTech.swagger.Swagger;
import com.HexaTech.fileSystem.FileSystem;

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
        RepoDevelop repoDevelop=new RepoDevelop(fileSystem);
        Swagger swagger=new Swagger();
        Stanford stanford=new Stanford();

        ModelDiscover modelDiscover=new ModelDiscover(stanford);
        ModelDesign modelDesign=new ModelDesign(stanford);
        ModelDevelop modelDevelop=new ModelDevelop(swagger);
        AddDocToParse addDocToParse=new AddDocToParse(presenterDiscover,repoDiscover);
        CheckThereAreDoc checkThereAreDoc=new CheckThereAreDoc(presenterDiscover,repoDiscover);
        CreateBDL createBDL=new CreateBDL(presenterDiscover,repoDiscover,modelDiscover);
        DeleteDoc deleteDoc=new DeleteDoc(presenterDiscover,repoDiscover);

        AddGherkin addGherkin=new AddGherkin(presenterDesign,repoDesign,modelDesign);
        AddBDL addBDL=new AddBDL(presenterDesign,repoDesign);
        CreateBAL createBAL=new CreateBAL(presenterDesign,repoDesign,modelDesign);

        AddDocument addDocument=new AddDocument(presenterDevelop,repoDevelop);
        RemoveDocument removeDocument=new RemoveDocument(presenterDevelop,repoDevelop);
        CreateAPI createAPI=new CreateAPI(presenterDevelop,repoDevelop,modelDevelop);

        ControllerDiscover controllerDiscover=new ControllerDiscover(addDocToParse,createBDL,deleteDoc,checkThereAreDoc);
        ControllerDesign controllerDesign=new ControllerDesign(addBDL,addGherkin,createBAL);
        ControllerDevelop controllerDevelop=new ControllerDevelop(addDocument,createAPI,removeDocument);

        CLI client=new CLI(controllerDiscover,presenterDiscover,controllerDesign,presenterDesign,controllerDevelop,presenterDevelop);
        client.useCaseNaturalAPI();
    }//main
}//MainDevelop
