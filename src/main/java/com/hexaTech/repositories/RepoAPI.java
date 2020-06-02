/**
 * @file RepoAPI
 * @version 1.0.0
 * @type java
 * @data 2020-05-13
 * @author Alessio Barbiero
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.repositories;

import com.hexaTech.interactor.entities.API;
import com.hexaTech.interactor.entities.Method;
import com.hexaTech.interactor.entities.Structure;
import com.hexaTech.interactor.repositoriesInterface.RepoAPIInterface;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.parser.OpenAPIV3Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RepoAPI class.
 */
@Component
public class RepoAPI implements RepoAPIInterface {
    @Autowired
    private API API;

    /**
     * RepoAPI empty constructor.
     */
    public RepoAPI(){
        API=new API();
    }

    /**
     * Fills API object with Swagger found arguments into the specified PLA.
     * @param newAPI API - new API object.
     */
    public void setAPI(API newAPI){
        this.API=newAPI;
    }//setAPI

    public API getAPI(){
        return API;
    }

    /**
     * Saves a new document.
     * @param content string - document content.
     * @param path string - document path.
     */
    public void saveOutput(String content,String path){
        try{
            File directory = new File("Develop");
            if (!directory.exists())
                directory.mkdir();
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(directory + "/" + path));
            String[] rows=content.split("\n");
            for(String row: rows){
                out.write(row);
                out.newLine();
            }//for
            out.close();
        }catch (IOException e) {
            System.out.println("exception occurred" + e);
        }//try_catch
    }//saveOutput

    public void openFile(String path) throws IOException{
        Desktop.getDesktop().open(new File(path));
    }

}//RepoAPI
