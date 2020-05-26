/**
 * @file Method
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Alessio Barbiero
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.entities;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Class used to represent a Method object
 */
public class Method {
    String methodReturnType;
    String methodName;
    String methodComment;
    HashMap<String,String> methodParam;

    /**
     * Method class empty constructor.
     */
    public Method(){}

    /**
     * Method class standard constructor.
     * @param methodReturnType string - method return statement.
     * @param methodName string - method name.
     * @param methodComment string - method related comment.
     * @param methodParam Hashmap<String,String> - method parameters list.
     *                    Key is used to store parameter's name, value to store parameter's type.
     */
    public Method(String methodReturnType, String methodName, String methodComment, HashMap<String, String> methodParam) {
        this.methodReturnType=methodReturnType;
        this.methodName=methodName;
        this.methodComment=methodComment;
        this.methodParam=methodParam;
    }

    /**
     * Changes method's return type.
     * @param returnType string - new return type.
     */
    public void setMethodReturnType(String returnType) {
        this.methodReturnType = returnType;
    }

    /**
     * Changes method's parameters list.
     * @param methodParam HashMap<String,String> - new method's parameters list.
     */
    public void setMethodParam(HashMap<String, String> methodParam) {
        this.methodParam=methodParam;
    }

    /**
     * Changes method's name.
     * @param methodName string - new method's name.
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * Changes method's comment.
     * @param methodComment string - new method's related comment.
     */
    public void setMethodComment(String methodComment) {
        this.methodComment = methodComment;
    }

    /**
     * Replaces PLA's parameters with method's values into a given string array.
     * @param PLA string[] - parametrized array to replace.
     * @param start integer - index from which to start analyzing.
     * @param end integer - index to which analyze.
     * @param types string[] - user defined types nomenclature.
     * @param typed boolean - true if output language is typed, false if it's not.
     * @return string - PLA developed method's content.
     */
    public String createAPI(String[] PLA, int start, int end, String[] types, boolean typed){
        String[] content=PLA;
        StringBuilder result=new StringBuilder();
        for(int temp=start;temp<end;temp++){
            content[temp]=PLA[temp];
            if(content[temp].contains("<--methodParamName-->")){
                content[temp]=content[temp].replace("<--methodParamName-->", getStringParam(types,typed));
                content[temp]=content[temp].replace("<--methodParamType-->", "");
            }else if(content[temp].contains("<--methodParamType-->")){
                content[temp]=content[temp].replace("<--methodParamType-->", getStringParam(types,typed));
                content[temp]=content[temp].replace("<--methodParamName-->", "");
            }//if_else_if
            if(content[temp].contains("<--method.start-->"))
                content[temp]=content[temp].replace("<--method.start-->","");
            if(content[temp].contains("<--method.end-->"))
                content[temp]=content[temp].replace("<--method.end-->","");
            if(content[temp].contains("<--methodReturn-->"))
                content[temp]=content[temp].replace("<--methodReturn-->",getStringReturnType(types,typed));
            if(content[temp].contains("<--methodName-->"))
                content[temp]=content[temp].replace("<--methodName-->",methodName);
            if(content[temp].contains("<--methodComment-->"))
                content[temp]=content[temp].replace("<--methodComment-->",methodComment);
            result.append(content[temp]).append("\n");
        }//for
        return result.toString();
    }//createAPI

    public String createTests(String[] PLA, int start, int end, String[] types, boolean typed, String name) {
        String[] content=PLA;
        StringBuilder result=new StringBuilder();
        for(int temp=start;temp<end;temp++) {
            content[temp] = PLA[temp];
            if (content[temp].contains("<--methodParamName-->")) {
                content[temp] = content[temp].replace("<--methodParamName-->", "");
                content[temp] = content[temp].replace("<--methodParamType-->", "");
            } else if (content[temp].contains("<--methodParamType-->")) {
                content[temp] = content[temp].replace("<--methodParamType-->", "");
                content[temp] = content[temp].replace("<--methodParamName-->", "");
            }//if_else_if
            if(content[temp].contains("<--method.start-->"))
                content[temp]=content[temp].replace("<--method.start-->","");
            if(content[temp].contains("<--method.end-->"))
                content[temp]=content[temp].replace("<--method.end-->","");
            if(content[temp].contains("<--methodReturn-->"))
                content[temp]=content[temp].replace("<--methodReturn-->",getStringReturnType(types,typed));
            if(content[temp].contains("<--methodName-->"))
                content[temp]=content[temp].replace("<--methodName-->",methodName+"Test");
            if(content[temp].contains("<--methodComment-->"))
                content[temp]=content[temp].replace("<--methodComment-->",methodComment+"\n\t"+name.toLowerCase()+"."+methodName+"("+getStringTests(types,typed)+");");
            result.append(content[temp]).append("\n");
        }
        return result.toString();
    }

    /**
     * Returns method's return type.
     * @param types string[] - user-defined nomenclatures list.
     * @param typed boolean - true if the output language is typed, false if it's not.
     * @return string - method's return type with user-defined nomenclature.
     */
    public String getStringReturnType(String[] types,boolean typed){
        boolean array=false;
        if(typed){
            if(methodReturnType.contains("[]"))
                array=true;
            if(methodReturnType.contains("integer"))
                methodReturnType=types[0];
            if(methodReturnType.contains("float"))
                methodReturnType=types[1];
            if(methodReturnType.contains("string"))
                methodReturnType=types[2];
            if(methodReturnType.contains("boolean"))
                methodReturnType=types[3];
            if(array && !methodReturnType.contains("[]"))
                methodReturnType+="[]";
        }
        return methodReturnType;
    }//getStringReturnType

    /**
     * Returns method's parameters.
     * @param types String[] - user-defined nomenclatures list.
     * @param typed boolean - true if the output language is typed, false if it's not.
     * @return string - method's parameters with user-defined nomenclature.
     */
    public String getStringParam(String[] types,boolean typed){
        StringBuilder param=new StringBuilder();
        Iterator it=methodParam.entrySet().iterator();
        if(typed){
            while(it.hasNext()){
                Map.Entry pair=(Map.Entry) it.next();
                String value=pair.getValue().toString();
                boolean array=false;
                if(value.contains("[]"))
                    array=true;
                if(value.contains("integer"))
                    value=types[0];
                if(value.contains("float"))
                    value=types[1];
                if(value.contains("string"))
                    value=types[2];
                if(value.contains("boolean"))
                    value=types[3];
                if(array && !value.contains("[]"))
                    value=value+"[]";
                param.append(value).append(" ").append(pair.getKey());
                if(it.hasNext())
                    param.append(", ");
            }//while
        }else{
            while(it.hasNext()){
                Map.Entry pair=(Map.Entry) it.next();
                param.append(pair.getKey().toString());
                if(it.hasNext())
                    param.append(", ");
            }//while
        }//if_else
        return param.toString();
    }//getStringParam

    private String getStringTests(String[] types, boolean typed) {
        StringBuilder param=new StringBuilder();
        Iterator it=methodParam.entrySet().iterator();
        if(typed){
            while(it.hasNext()){
                Map.Entry pair=(Map.Entry) it.next();
                String value=pair.getValue().toString();
                boolean array=false;
                if(value.contains("[]"))
                    array=true;
                if(value.contains("integer"))
                    value=types[0];
                if(value.contains("float"))
                    value=types[1];
                if(value.contains("string"))
                    value=types[2];
                if(value.contains("boolean"))
                    value=types[3];
                if(array && !value.contains("[]"))
                    value=value+"[]";
                param.append(value);
                if(it.hasNext())
                    param.append(", ");
            }//while
        }else{
            while(it.hasNext()){
                Map.Entry pair=(Map.Entry) it.next();
                param.append(pair.getKey().toString());
                if(it.hasNext())
                    param.append(", ");
            }//while
        }//if_else
        return param.toString();
    }
}//Method
