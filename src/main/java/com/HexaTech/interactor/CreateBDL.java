/**
 * @file CreateBDL
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Alessio Barbiero
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.HexaTech.interactor;


import com.HexaTech.entities.BDL;
import com.HexaTech.model.ModelDiscoverInterface;
import com.HexaTech.portInterface.CreateBDLInputPort;
import com.HexaTech.portInterface.CreateBDLOutputPort;
import com.HexaTech.repo.RepoDiscoverInterface;

import java.io.IOException;

/**
 * Class used to manage a BDL object's creation.
 */
public class CreateBDL implements CreateBDLInputPort {
    CreateBDLOutputPort createBDLOutputPort;
    RepoDiscoverInterface repoDiscoverInterface;
    ModelDiscoverInterface modelDiscoverInterface;

    /**
     * CreateBDL standard constructor.
     * @param output CreateBDLOutputPort - used to send output notifications.
     * @param repo RepoInterface - used to communicate with Repo.
     * @param model ModelInterface - used to communicate with Model.
     */
    public CreateBDL(CreateBDLOutputPort output, RepoDiscoverInterface repo, ModelDiscoverInterface model) {
        this.createBDLOutputPort = output;
        this.repoDiscoverInterface= repo;
        this.modelDiscoverInterface= model;
    }

    /**
     * Creates a new BDL object.
     * @throws IOException if an error occurs while loading or parsing any file.
     */
    public void createBDL() throws IOException {
        BDL bdl=new BDL();
        for(String path: repoDiscoverInterface.getLista()) {
            String document = repoDiscoverInterface.returnContentFromTxt(path);
            BDL bdlToMerge=modelDiscoverInterface.extractBDL(document);
            bdl.mergeBDL(bdlToMerge);
        }//for
        repoDiscoverInterface.saveBDL(bdl);
        repoDiscoverInterface.delete(".\\Discover\\temp.txt");
        createBDLOutputPort.showCreateBdl("BDL created into folder: Discover");
    }//createBDL

}//CreateBDL
