package com.hexaTech.adapter.repository;

import com.hexaTech.domain.entity.BAL;
import com.hexaTech.domain.entity.Parameter;
import com.hexaTech.domain.entity.StructureBAL;
import com.hexaTech.domain.port.out.repository.RepoBALInterface;
import org.springframework.stereotype.Component;

@Component
public class RepoBAL implements RepoBALInterface {
    private BAL BAL;

    /**
     * Class empty constructor.
     */
    public RepoBAL(){
        BAL=new BAL();
    }

    /**
     * Fills API object with Swagger found arguments into the specified PLA.
     * @param bal BAL - BAL object.
     */
    @Override
    public void setBAL(BAL bal) throws IllegalArgumentException {
        this.BAL=bal;
    }

    /**
     * Returns BAL value.
     * @return BAL - object value.
     */
    @Override
    public BAL getBAL() {
        return BAL;
    }

    public boolean hasMethod(int position){
        return BAL.getMethods().size() > position && position >= 0;
    }

    public String getMethod(int position){
        return "Method name: " + BAL.getMethods().get(position).getName()
                + "\nMethod return type: " + BAL.getMethods().get(position).getToReturn().getType();
    }

    public void alterMethod(int position,String type){
        BAL.getMethods().get(position).setToRet(type);
    }

    public boolean hasParameter(int position,int identifier){
        return BAL.getMethods().get(position).getParameters().size() > identifier && identifier >= 0;
    }

    public String getParameter(int position,int identifier){
        return "\tParameter name: " + BAL.getMethods().get(position).getParameters().get(identifier).getName()
                + "\n\tParameter type: " + BAL.getMethods().get(position).getParameters().get(identifier).getType();
    }

    public void alterParameter(int position,int identifier,String type){
        BAL.getMethods().get(position).getParameters().get(identifier).setType(type);
    }

    public boolean hasObject(int position){
        return BAL.getStructures().size() > position && position >= 0;
    }

    public String getObjectType(int position){
        return BAL.getStructures().get(position).getName();
    }

    public String getObjects(){
        StringBuilder message=new StringBuilder();
        int count=1;
        for(StructureBAL structure:BAL.getStructures()){
            message.append("\t").append(count).append(": ").append(structure.getName()).append("\n");
            for(Parameter parameter:structure.getParameters())
                message.append("\t\t- ").append(parameter.getType()).append(" ").append(parameter.getName()).append("\n");
            count++;
        }//for
        return message.toString();
    }//getObjects

    public void addObject(String structName,String paramName,String paramType){
        Parameter tempParam=new Parameter();
        tempParam.setType(paramType);
        tempParam.setName(paramName);
        int count=0;
        boolean found=false;
        for(StructureBAL structure:BAL.getStructures()){
            if(structure.getName().equals(structName)) {
                found=true;
                break;
            }//if
            count++;
        }//for
        if(found)
            BAL.getStructures().get(count).setParameters(tempParam);
        else{
            StructureBAL tempStructure=new StructureBAL();
            tempStructure.setName(structName);
            tempStructure.setParameters(tempParam);
            BAL.addStructure(tempStructure);
        }//if_else
    }//addObject

}//RepoBAL
