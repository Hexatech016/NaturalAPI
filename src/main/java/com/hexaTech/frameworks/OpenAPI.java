package com.hexaTech.frameworks;

import com.hexaTech.interactor.entities.API;
import com.hexaTech.interactor.entities.Method;
import com.hexaTech.interactor.entities.Structure;
import com.hexaTech.interactor.frameworksInterface.JsonParsingInterface;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.parser.OpenAPIV3Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenAPI implements JsonParsingInterface {

    public API extractAPI(String path){
        API API=new API();
        if(extractAPIMethods(path)!=null && extractAPIStructures(path)!=null &&
                extractAPIName(path)!=null && extractAPIDescription(path)!=null){
            API.setAPIMethods(extractAPIMethods(path));
            API.setAPIStructures(extractAPIStructures(path));
            API.setAPIName(extractAPIName(path));
            API.setAPIComment(extractAPIDescription(path));
            return API;
        }else
            return null;
    }//extractAPI

    //SWAGGER METHODS
    /**
     * Extracts methods specified into BAL document.
     * @param path string - BAL path.
     * @return List<Method> - a list of methods found; null if an error occurs.
     * @throws IllegalArgumentException if the content of the BAL file isn't valid.
     */
    public List<Method> extractAPIMethods(String path) throws IllegalArgumentException{
        try {
            List<Method> result=new ArrayList<>();
            io.swagger.v3.oas.models.OpenAPI openAPI=new OpenAPIV3Parser().read(path);
            if(openAPI==null)
                throw new IllegalArgumentException();
            if(openAPI.getPaths()==null)
                return result;
            for(Map.Entry<String, PathItem> method : openAPI.getPaths().entrySet())
                result.add(getMethod(method));
            return result;
        }catch(IllegalArgumentException e){
            System.out.println("BAL's body can't be empty.");
        }//try_catch
        return null;
    }//extractAPIMethods

    /**
     * Extracts structures specified into BAL document.
     * @param path string - BAL path. null if an error occurs.
     * @return List<Structures> - a list of methods found; null if an error occurs.
     * @throws IllegalArgumentException if the content of the BAL file isn't valid.
     */
    public List<Structure> extractAPIStructures(String path) throws IllegalArgumentException{
        try {
            List<Structure> result=new ArrayList<>();
            io.swagger.v3.oas.models.OpenAPI openAPI=new OpenAPIV3Parser().read(path);
            if(openAPI==null)
                throw new IllegalArgumentException();
            if(openAPI.getComponents()==null || openAPI.getComponents().getSchemas()==null)
                return result;
            for(Map.Entry<String, Schema> struct : openAPI.getComponents().getSchemas().entrySet())
                result.add(getStructure(struct));
            return result;
        }catch(IllegalArgumentException e){
            System.out.println("BAL's body can't be empty.");
        }//try_catch
        return null;
    }//extractAPIStructures

    /**
     * Extracts name specified into BAL document.
     * @param path string - BAL path.
     * @return string - name; null if an error occurs.
     * @throws IllegalArgumentException if the content of the BAL file isn't valid.
     */
    public String extractAPIName(String path) throws IllegalArgumentException{
        try{
            io.swagger.v3.oas.models.OpenAPI openAPI=new OpenAPIV3Parser().read(path);
            if(openAPI==null)
                throw new IllegalArgumentException();
            if(openAPI.getInfo()==null || openAPI.getInfo().getTitle()==null)
                return "";
            return openAPI.getInfo().getTitle();
        }catch(IllegalArgumentException e){
            System.out.println("BAL's body can't be empty.");
        }//try_catch
        return null;
    }//extractAPIName

    /**
     * Extracts description specified into BAL document.
     * @param path string - BAL path.
     * @return string - description; null if an error occurs.
     * @throws IllegalArgumentException if the content of the BAL file isn't valid.
     */
    public String extractAPIDescription(String path) throws IllegalArgumentException{
        try{
            io.swagger.v3.oas.models.OpenAPI openAPI=new OpenAPIV3Parser().read(path);
            if(openAPI==null)
                throw new IllegalArgumentException();
            if(openAPI.getInfo()==null || openAPI.getInfo().getDescription()==null)
                return "";
            return openAPI.getInfo().getDescription();
        }catch(IllegalArgumentException e){
            System.out.println("BAL's body can't be empty.");
        }//try_catch
        return null;
    }//extractAPIDescription

    /**
     * Extracts method's information from a swagger-method list element.
     * @param meth Map.Entry<String,PathItem> - method to analyze.
     * @return Method - method object created with the extracted information.
     */
    private Method getMethod(Map.Entry<String,PathItem> meth){
        Method method=new Method();
        method.setMethodName(getMethodName(meth));//METHOD_NAME
        method.setMethodComment(getMethodDescription(meth.getValue()));//METHOD_DESCRIPTION
        method.setMethodReturnType(getMethodReturn(meth.getValue().getGet().getResponses()));//METHOD_RETURN_TYPE
        method.setMethodParam(getMethodParameters(meth.getValue().getGet()));//METHOD_PARAMETERS
        return method;
    }//getMethod

    /**
     * Extracts structure's information from a swagger-structure list element.
     * @param struct Map.Entry<String,Schema> - structure to analyze.
     * @return Structure - structure object created with the extracted information.
     */
    private Structure getStructure(Map.Entry<String,Schema> struct){
        Structure structure=new Structure();
        structure.setStructureName(getStructureName(struct));//STRUCTURE_NAME
        structure.setStructureParam(getStructureParam(struct));//STRUCTURE_PARAMETERS
        return structure;
    }//getStructure

    /**
     * Extracts structure's name from a swagger-structure element.
     * @param struct Map.Entry<String,Schema> - structure to analyze.
     * @return string - structure's name.
     */
    private String getStructureName(Map.Entry<String,Schema> struct){
        return struct.getKey();
    }

    /**
     * Extracts structure's parameters list from a swagger-structure element.
     * @param struct Map.Entry<String,Schema> - structure to analyze.
     * @return HashMap<String,String> - structure's parameters list.
     *                              Key is used to store parameter's name, value to store parameter's type.
     */
    private HashMap<String,String> getStructureParam(Map.Entry<String,Schema> struct){
        HashMap<String,String> tempStruct=new HashMap<>();
        Map<String,Schema> temp=struct.getValue().getProperties();
        for(Map.Entry<String, Schema> schema:temp.entrySet()){
            tempStruct.put(schema.getKey(),schema.getValue().getType());
        }//for
        return tempStruct;
    }//getStructureParam

    /**
     * Extracts method's return type from a swagger-method element.
     * @param meth ApiResponses - method to analyze.
     * @return string - method's return type.
     */
    private String getMethodReturn(ApiResponses meth){
        Map.Entry<String, ApiResponse> response=meth.entrySet().iterator().next();
        Schema schema=response.getValue().getContent().entrySet().iterator().next().getValue().getSchema();
        if(schema instanceof ArraySchema){
            ArraySchema arraySchema=(ArraySchema)schema;
            if(arraySchema.getItems().get$ref()!=null && !arraySchema.getItems().get$ref().equals(""))
                return arraySchema.getItems().get$ref().substring("#/components/schemas/".length())+"[]";
            else
                return arraySchema.getItems().getType()+"[]";
        }//if
        if(schema!=null && schema.get$ref()!=null && !schema.get$ref().equals(""))
            return schema.get$ref().substring("#/components/schemas/".length());
        if(schema==null || (schema.getType()==null || schema.getType().equals("") || schema.getType().equals("object") || schema.getType()==null))
            return "void";
        else
            return schema.getType();
    }//getMethodReturn


    /**
     * Extracts method's name from a swagger-method element.
     * @param meth Map.Entry<String,PathItem> - method to analyze.
     * @return string - method's name.
     */
    private String getMethodName(Map.Entry<String,PathItem> meth){
        return meth.getKey().substring(1);
    }

    /**
     * Extracts method's parameters list from a swagger-method element.
     * @param meth Operation - method to analyze.
     * @return HashMap<String,String> - method's parameters list.
     *                              Key is used to store parameter's name, value to store parameter's type.
     */
    private HashMap<String,String> getMethodParameters(Operation meth){
        HashMap<String,String> result=new HashMap<>();
        String name,type;
        if(meth.getParameters()!=null){
            for(Parameter p:meth.getParameters()){
                if(!p.getSchema().getType().equals("array"))
                    type=p.getSchema().getType();
                else{
                    ArraySchema arraySchema=(ArraySchema) p.getSchema();
                    if(arraySchema.getItems().get$ref()!=null && !arraySchema.getItems().get$ref().equals(""))
                        type=arraySchema.getItems().get$ref().substring("#/definitions/schemas".length())+"[]";
                    else
                        type=arraySchema.getItems().getType()+"[]";
                }//if_else
                name=p.getName();
                result.put(name,type);
            }//for
        }//if
        return result;
    }//getMethodParameters

    /**
     * Extracts method's description from a swagger-method element.
     * @param meth PathItem - method to analyze.
     * @return string - method's description.
     */
    private String getMethodDescription(PathItem meth){
        return meth.getGet().getResponses().entrySet().iterator().next().getValue().getDescription() + " - " + meth.getGet().getOperationId();
    }//getMethodDescription


}//OpenAPI
