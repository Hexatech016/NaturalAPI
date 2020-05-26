/**
 * @file CreateBAL
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Denis Salviato
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.interactor;


import com.hexaTech.entities.*;
import com.hexaTech.portInterface.CreateBALInputPort;
import com.hexaTech.portInterface.CreateBALOutputPort;
import com.hexaTech.repointerface.RepoBALDocumentInterface;
import com.hexaTech.repointerface.RepoBALInterface;
import com.hexaTech.repointerface.RepoBDLInterface;
import com.hexaTech.repointerface.RepoGherkinInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class used to manage a BAL creation.
 */
public class CreateBAL implements CreateBALInputPort {
    CreateBALOutputPort createBALOutputPort;
    RepoGherkinInterface repoGherkinInterface;
    RepoBALDocumentInterface repoBALDocumentInterface;
    RepoBALInterface repoBALInterface;
    RepoBDLInterface repoBDLInterface;

    /**
     * CreateBAL class constructor.
     * @param createBALOutputPort CreateBALOutputPort - used to send output notifications.
     * @param repoGherkinInterface RepoInterface - used to communicate with repo.
     */

    public CreateBAL(CreateBALOutputPort createBALOutputPort, RepoGherkinInterface repoGherkinInterface,
                     RepoBALDocumentInterface repoBALDocumentInterface, RepoBALInterface repoBALInterface, RepoBDLInterface repoBDLInterface) {
        this.createBALOutputPort = createBALOutputPort;
        this.repoGherkinInterface = repoGherkinInterface;
        this.repoBALDocumentInterface = repoBALDocumentInterface;
        this.repoBALInterface = repoBALInterface;
        this.repoBDLInterface = repoBDLInterface;
    }

    /**
     * Create a new BAL object.
     * @throws IOException if an error occurs during loading process.
     */
    @Override
    public void createBAL() throws IOException {
        for (Document doc: repoGherkinInterface.getGherkin()) {
            String path=doc.getPath();
            String document = repoGherkinInterface.getContentFromPath(path);
            List<String> document2 = new ArrayList<>();
            if (this.repoBDLInterface.getbdl() != null) {
                new BDL();
                BDL bdl = this.repoBDLInterface.getbdl();
                document2.add(bdl.BDLtotag(bdl.getNouns()));
                document2.add(bdl.BDLtotag(bdl.getVerbs()));
                document2.add(bdl.BDLtotag(bdl.getPredicates()));
            }else{
                document2.add(null);
                document2.add(null);
                document2.add(null);
            }
            BAL bal=repoBALDocumentInterface.setBALFromGherkin(document,document2);
            repoBALInterface.setBAL(bal);
            repoBALDocumentInterface.saveBAL(bal);
        }//for
        createBALOutputPort.showCreatedBAL("BAL created into folder: Design.");
    }//createBAL

    public void checkTypes() throws IOException {
        BAL bal=repoBALInterface.getBAL();

        String suggestion,line;
        Scanner scan=new Scanner(System.in);
        boolean b;
        for(MethodBAL methodBAL:bal.getMethods()){
            b=true;
            suggestion="\nMethod name: "+methodBAL.getName();
            //suggestion+="\nDescription: "+methodBAL.getDescription();
            suggestion+="\nReturn type: "+methodBAL.getToReturn().getType();
            createBALOutputPort.showCreatedBAL(suggestion+"\n Do you want to change return type? (Y/N)");
            line=scan.nextLine();
            while(b){
                if(line.equalsIgnoreCase("y")){
                    b=false;
                    createBALOutputPort.showCreatedBAL("Please choose the correct type: \nS: string\nI: integer\nF: float\nB: boolean");
                    line=scan.nextLine();
                    while(!checkAnswer(line)){
                        createBALOutputPort.showErrorBAL("Wrong character. Please retry.");
                        line=scan.nextLine();
                    }
                    methodBAL.setToRet(returnType(line.toUpperCase()));
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
                createBALOutputPort.showCreatedBAL(suggestion+"\n Do you want to change parameter type? (Y/N)");
                line=scan.nextLine();
                while(b){
                    if(line.equalsIgnoreCase("y")){
                        b=false;
                        createBALOutputPort.showCreatedBAL("Please choose the correct type: \nS: string\nI: integer\nF: float\nB: boolean");
                        line=scan.nextLine();
                        while(!checkAnswer(line)){
                            createBALOutputPort.showErrorBAL("Wrong character. Please retry.");
                            line=scan.nextLine();
                        }
                        parameter.setType(returnType(line.toUpperCase()));
                    }else if(line.equalsIgnoreCase("n")){
                        b=false;
                    }else{
                        createBALOutputPort.showErrorBAL("Please type Y or N.");
                        line=scan.nextLine();
                    }//if_else
                }//while
            }//for_parameters
        }//for_methods
        createBALOutputPort.showCreatedBAL("BAL updated into folder Design.\n");
        repoBALDocumentInterface.saveBAL(bal);
    }//checkTypes

    public boolean checkAnswer(String line){
        return line.equalsIgnoreCase("s") || line.equalsIgnoreCase("i") || line.equalsIgnoreCase("f") || line.equalsIgnoreCase("b");
    }

    public String returnType(String line){
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
    }//returnType

}//CreateBAL
