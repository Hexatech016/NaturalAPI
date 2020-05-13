package com.hexaTech.repo;

import com.hexaTech.entities.API;
import com.hexaTech.swagger.Swagger;
import com.hexaTech.swagger.SwaggerInterface;

public class RepoAPI{
    SwaggerInterface swaggerInterface;
    API API;

    /**
     * RepoAPI empty constructor.
     */
    public RepoAPI(){
        API=new API();
        swaggerInterface=new Swagger();
    }

    /**
     * Fills API object with Swagger found arguments into the specified PLA.
     * @param path string - PLA's path.
     * @return API - new API object.
     */
    public API setAPI(String path) throws IllegalArgumentException{
        if(swaggerInterface.extractAPIMethods(path)!=null && swaggerInterface.extractAPIStructures(path)!=null &&
                swaggerInterface.extractAPIName(path)!=null && swaggerInterface.extractAPIDescription(path)!=null){
            API.setAPIMethods(swaggerInterface.extractAPIMethods(path));
            API.setAPIStructures(swaggerInterface.extractAPIStructures(path));
            API.setAPIName(swaggerInterface.extractAPIName(path));
            API.setAPIComment(swaggerInterface.extractAPIDescription(path));
            return API;
        }else
            return null;
    }//setAPI

}//RepoAPI
