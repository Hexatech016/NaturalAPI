/**
 * @file CreateBAL
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Denis Salviato
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.domain.usecase.design;


import com.hexaTech.domain.entity.BDL;
import com.hexaTech.domain.port.out.repository.RepoBDLInterface;
import com.hexaTech.domain.entity.*;
import com.hexaTech.domain.port.out.framework.TextsParsingInterface;
import com.hexaTech.domain.port.in.CreateBALInputPort;
import com.hexaTech.domain.port.out.usecase.CreateBALOutputPort;
import com.hexaTech.domain.port.out.repository.RepoBALDocumentInterface;
import com.hexaTech.domain.port.out.repository.RepoBALInterface;
import com.hexaTech.domain.port.out.repository.RepoBOInterface;
import com.hexaTech.domain.port.out.repository.RepoGherkinInterface;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.CaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class used to manage a BAL creation.
 */
@Component
public class CreateBAL implements CreateBALInputPort {
    private final CreateBALOutputPort createBALOutputPort;
    private final RepoGherkinInterface repoGherkinInterface;
    private final RepoBALDocumentInterface repoBALDocumentInterface;
    private final RepoBALInterface repoBALInterface;
    private final RepoBOInterface repoBOInterface;
    private final TextsParsingInterface textsParsingInterface;

    /**
     * CreateBAL class constructor.
     * @param createBALOutputPort CreateBALOutputPort - used to send output notifications.
     * @param repoGherkinInterface RepoInterface - used to communicate with repo.
     */
    @Autowired
    public CreateBAL(CreateBALOutputPort createBALOutputPort, RepoGherkinInterface repoGherkinInterface,
                     RepoBALDocumentInterface repoBALDocumentInterface, RepoBALInterface repoBALInterface,
                     RepoBOInterface repoBOInterface, TextsParsingInterface textsParsingInterface) {
        this.createBALOutputPort = createBALOutputPort;
        this.repoGherkinInterface = repoGherkinInterface;
        this.repoBALDocumentInterface = repoBALDocumentInterface;
        this.repoBALInterface = repoBALInterface;
        this.repoBOInterface = repoBOInterface;
        this.textsParsingInterface = textsParsingInterface;
    }

    /**
     * Create a new BAL object.
     * @throws IOException if an error occurs during loading process.
     */
    @Override
    public void createBAL(String nameBAL) throws IOException {
        String path=repoGherkinInterface.getGherkin().getPath();
        String document = repoGherkinInterface.getContentFromPath(path);
        List<Gherkin> gherkins =textsParsingInterface.extractFromGherkin(document);
        BAL bal=repoBALDocumentInterface.setBALFromGherkin(gherkins, nameBAL);
        bal.joinBO(repoBOInterface.getBoOpenAPI().getOntologyObjects());
        repoBALInterface.setBAL(bal);
        repoBALDocumentInterface.saveBAL(bal,nameBAL);
        createBALOutputPort.showCreatedBAL("BAL created into folder: Design.");
    }//createBAL

    public void checkTypes(String nameBAL) throws IOException {
        BAL bal=repoBALInterface.getBAL();
        String suggestion,line;
        Scanner scan=new Scanner(System.in);
        boolean b;
        for(MethodBAL methodBAL:bal.getMethods()){
            b=true;
            suggestion="\nMethod name: "+methodBAL.getName();
            suggestion+="\nReturn type: "+methodBAL.getToReturn().getType();
            createBALOutputPort.showMessage(suggestion+"\n Do you want to change return type? (Y/N)");
            line=scan.nextLine();
            while(b){
                if(line.equalsIgnoreCase("y")){
                    b=false;
                    createBALOutputPort.showMessage("Please choose the correct type: \nV: void \nS: string\nI: integer\nF: float\nB: boolean\nC: complex object");
                    line=scan.nextLine();
                    while(!checkAnswer(line) && !line.equalsIgnoreCase("v")){
                        createBALOutputPort.showErrorBAL("Wrong character. Please retry.");
                        line=scan.nextLine();
                    }
                    if(line.equalsIgnoreCase("v"))
                        methodBAL.setToRet("void");
                    else if(getType(line.toUpperCase()).equalsIgnoreCase(""))
                        methodBAL.setToRet(getObj(bal));
                    else
                        methodBAL.setToRet(isAnArray(getType(line.toUpperCase())));
                }else if(line.equalsIgnoreCase("n")){
                    b=false;
                }else{
                    createBALOutputPort.showErrorBAL("Please type Y or N.");
                    line=scan.nextLine();
                }//if_else
            }//while
            for(Parameter parameter:methodBAL.getParameters()){
                b=true;
                suggestion="\nParameter name: "+parameter.getName();
                suggestion+="\nParameter type: "+parameter.getType();
                createBALOutputPort.showMessage(suggestion+"\n Do you want to change parameter type? (Y/N)");
                line=scan.nextLine();
                while(b){
                    if(line.equalsIgnoreCase("y")){
                        b=false;
                        createBALOutputPort.showMessage("Please choose the correct type: \nS: string\nI: integer\nF: float\nB: boolean\nC: complex object");
                        line=scan.nextLine();
                        while(!checkAnswer(line)){
                            createBALOutputPort.showErrorBAL("Wrong character. Please retry.");
                            line=scan.nextLine();
                        }
                        if(getType(line.toUpperCase()).equalsIgnoreCase(""))
                            parameter.setType(getObj(bal));
                        else
                            parameter.setType(isAnArray(getType(line.toUpperCase())));
                    }else if(line.equalsIgnoreCase("n")){
                        b=false;
                    }else{
                        createBALOutputPort.showErrorBAL("Please type Y or N.");
                        line=scan.nextLine();
                    }//if_else
                }//while
            }//for_parameters
        }//for_methods
        repoBALInterface.setBAL(bal);
        repoBALDocumentInterface.saveBAL(bal,nameBAL);
        createBALOutputPort.showCreatedBAL("BAL updated into folder Design.\n");
        repoGherkinInterface.deleteDoc("." + File.separator + "Design" + File.separator + "BackupGherkin.txt");
        repoBALDocumentInterface.openFile("." + File.separator + "Design" + File.separator + nameBAL + "BAL.json");
    }//checkTypes


    private boolean checkAnswer(String line){
        return line.equalsIgnoreCase("s") || line.equalsIgnoreCase("i")
                || line.equalsIgnoreCase("f") || line.equalsIgnoreCase("b") || line.equalsIgnoreCase("c");
    }//checkAnswer

    private String getType(String line){
        switch(line){
            case("S"):
                return "string";
            case("I"):
                return "integer";
            case("F"):
                return "float";
            case("B"):
                return "boolean";
            default:
                return "";
        }//switch
    }//getType

    private String getObj(BAL bal){
        Scanner scan=new Scanner(System.in);
        StringBuilder message=new StringBuilder("Please select your choice: \n0: create a new object");
        int count=1;
        for(StructureBAL structure:bal.getStructures()){
            message.append("\n").append(count).append(": ").append(structure.getName());
            for(Parameter parameter:structure.getParameters())
                message.append("\n\t").append(parameter.getType()).append(" ").append(parameter.getName());
            count++;
        }//for
        createBALOutputPort.showMessage(message.toString());
        String line=scan.nextLine();
        while(!StringUtils.isNumeric(line) || !(0<=Integer.parseInt(line) && Integer.parseInt(line)<=count-1)){
            createBALOutputPort.showErrorBAL("Wrong character. Please retry.");
            line=scan.nextLine();
        }//while
        if(line.equals("0"))
            return isAnArray(addObj(bal));
        else
            return isAnArray(bal.getStructures().get(Integer.parseInt(line)-1).getName());
    }//getObj

    private String addObj(BAL bal){
        createBALOutputPort.showMessage("\nPlease insert the new object's name: ");
        Scanner scanner=new Scanner(System.in);
        String line=scanner.nextLine(),name;
        name=notNumericCheck(line);
        name=CaseUtils.toCamelCase(name,false, ' ');
        List<Parameter> parameterList=new ArrayList<>();
        createBALOutputPort.showMessage("Please insert the new object's parameters. Type EXIT instead of name to stop.\n\tParameter name: ");
        line=scanner.nextLine();
        while(!line.equals("EXIT")){
            String paramName,paramType;
            paramName=notNumericCheck(line);
            paramName=CaseUtils.toCamelCase(paramName,false,' ');
            createBALOutputPort.showMessage("\tParameter type (S: string/I: integer/F: float/B: boolean):");
            line=scanner.nextLine();
            while(!checkAnswer(line)){
                createBALOutputPort.showErrorBAL("Wrong character. Please retry.");
                line=scanner.nextLine();
            }//while
            paramType=isAnArray(getType(line.toUpperCase()));
            parameterList.add(new Parameter("description",paramName,paramType));
            createBALOutputPort.showMessage("Type EXIT to stop. \n\tParameter name: ");
            line=scanner.nextLine();
        }//while
        bal.addStructure(new StructureBAL(name,parameterList));
        return name;
    }//addObj

    private String notNumericCheck(String line){
        Scanner scanner=new Scanner(System.in);
        while(StringUtils.isNumeric(line)){
            createBALOutputPort.showErrorBAL("The value can't be numeric. Please retry.");
            line=scanner.nextLine();
        }//while
        return line;
    }//notNumericCheck

    private String isAnArray(String name){
        Scanner scanner=new Scanner(System.in);
        String line;
        createBALOutputPort.showMessage("Is it an array? (Y/N)");
        line=scanner.nextLine();
        while(!line.equalsIgnoreCase("n") && !line.equalsIgnoreCase("y")){
            createBALOutputPort.showErrorBAL("Please type Y or N");
            line=scanner.nextLine();
        }//while
        if(line.equalsIgnoreCase("y"))
            name+="[]";
        return name;
    }//isAnArray

}//CreateBAL
