/**
 * @file CLI
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Alessio Barbiero
 * @email hexatech016@gmail.com
 * @license MIT
*/

package com.hexaTech.application.cli;

import com.hexaTech.adapter.interfaceadapter.Controller;
import com.hexaTech.adapter.interfaceadapter.Presenter;
import net.didion.jwnl.JWNLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;

/**
 * Class used to show output messages to user and receive input actions.
 */

@Component
public class Cli implements MyObserver {

    @Autowired
    private final Controller controller;
    @Autowired
    private final Presenter presenter;

    private final Scanner scanner;
    private String choice;

    public Cli(Controller controller, Presenter presenter) {
        this.controller = controller;
        this.presenter = presenter;
        this.presenter.addObserver(this);
        this.scanner=new Scanner(System.in);
        this.choice="";
    }

    /* ************************ MAIN ************************ */

    public void useCaseNaturalAPI() throws IOException, JWNLException {
        System.out.println("Use case: \n 1: Discover \n 2: Design \n 3: Develop \n 4: Exit");
        choice = scanner.nextLine();
        switch (choice) {
            case ("1"):
                useCaseDiscover();
                break;
            case ("2"):
                useCaseDesign();
                break;
            case ("3"):
                useCaseDevelop();
                break;
            case ("4"):
                System.out.println("Bye!");
                System.exit(0);
            default:
                System.out.println("Invalid choice. Please retry.");
                useCaseNaturalAPI();
        }//switch
    }//useCaseNaturalAPI

    /* ************************ DISCOVER ************************ */

    public void useCaseDiscover() throws IOException, JWNLException {
        System.out.println("Use Case: \n 1: Check if there are saved documents \n 2: Add a document (.txt)" +
                "\n 3: Check between BDL and Gherkin" + "\n 4: Back");
        choice = scanner.nextLine();
        switch (choice) {
            case("1"):
                controller.existsDoc(".\\Discover\\BackupDocument.txt");
                if(notifyMeDone()){
                    if(existsBackUpDocument())
                        useCaseBDL();
                    else
                        useCaseDiscover();
                }else {
                    System.out.println("There are no saved documents");
                    useCaseDiscover();
                }
            case ("2"):
                System.out.println("Insert document's path: (ex. C:\\Users\\User\\Desktop\\example.txt)");
                controller.addTextDoc("Discover",scanner.nextLine());
                if(!notifyMeDone()) {
                    System.out.println("The file is not a .txt or it doesn't exist. Please retry.");
                    useCaseDiscover();
                }else{
                    System.out.println("Document added.");
                    useCaseBDL();
                }//else
            case ("3"):
                System.out.println("Which BDL do you want to use for checking Gherkin?");
                choiceOfBdl();
                System.out.println("Add a gherkin scenario");
                choiceOfGherkin();
                controller.checkBetweenBDLAndGherkin("Discover");
            case ("4"):
                useCaseNaturalAPI();
            default:
                System.out.println("Invalid choice. Please retry.");
                useCaseDiscover();
        }//switch
    }//useCase

    public void choiceOfGherkin() throws IOException, JWNLException {
        System.out.println("Use Case: \n 1: Add a gherkin scenario" + "\n 2: Back");
        choice = scanner.nextLine();
        switch (choice) {
            case ("1"):
                System.out.println("Insert document's path: (ex. C:\\Users\\User\\Desktop\\example.scenario)");
                controller.addGherkin("Design", scanner.nextLine());
                if (notifyMeDone()) {
                    System.out.println("Scenario added.");
                    break;
                } else {
                    System.out.println("The file is not a .scenario or it doesn't exist. Please retry.");
                    choiceOfGherkin();
                }
            case ("2"):
              useCaseDiscover();
            default:
                System.out.println("Invalid choice. Please retry.");
                choiceOfGherkin();
        }
    }

    public void choiceOfBdl() throws IOException{
        System.out.println("Use Case: \n 1: Import an external BDL \n 2: Use a BDL extracted just before");
        choice = scanner.nextLine();
        switch (choice) {
            case ("1"):
                System.out.println("Insert document's path: (ex. C:\\Users\\User\\Desktop\\example.BDL)");
                controller.addBDL(scanner.nextLine());
                if(notifyMeDone()) {
                    System.out.println("BDL added.");
                    break;
                }else {
                    System.out.println("The file is not a .BDL or it doesn't exist. Please retry.");
                    choiceOfBdl();
                }//else
            case ("2"):
                controller.checkIfRepoBDLIsEmpty();
                if(notifyMeDone()) {
                    System.out.println("There is no BDL in memory. Please import an external one or extract one from document texts.");
                    choiceOfBdl();
                }else{
                    System.out.println("BDL is ready to be processed");
                    break;
                }//else
            default:
                System.out.println("Invalid choice. Please retry.");
                choiceOfBdl();
        }
    }

    public void useCaseBDL() throws IOException, JWNLException {
        System.out.println("Use Case: \n 1: Extract BDL \n 2: Add another document (.txt) \n 3: Back");
        choice=scanner.nextLine();
        switch(choice){
            case ("1"):
                System.out.println("Choose a name for BDL.");
                choice=scanner.nextLine();
                if(!choice.equals("")) {
                    controller.createBDL(choice);
                    useCaseDiscover();
                }else{
                    System.out.println("Please insert a valid name.");
                    useCaseBDL();
                }//if_else
            case ("2"):
                System.out.println("Insert document's path: (ex. C:\\Users\\User\\Desktop\\example.txt)");
                controller.addTextDoc("Discover",scanner.nextLine());
                if(!notifyMeDone()) {
                    System.out.println("The file is not a .txt or it doesn't exist. Please retry.");
                }else{
                    System.out.println("Document added.");
                }//else
                useCaseBDL();
            case ("3"):
                useCaseDiscover();
            default:
                System.out.println("Invalid choice. Please retry.");
                useCaseBDL();
        }//switch
    }//useCaseBDL

    /**
     * Asks to user if he wants to reload an existing backup file.
     * @return boolean - true if user decide to reload a backup file; false if user doesn't want to reload it.
     * @throws IOException if the document path specified in backup is not valid anymore.
     */
    public boolean existsBackUpDocument() throws IOException {
        System.out.println("Some documents are already stored. Do you want to load them? (Y/N)");
        choice = scanner.nextLine();
        if(choice.equalsIgnoreCase("y")) {
            controller.restoreTextDoc("Discover");
            return true;
        }else if (choice.equalsIgnoreCase("n")){
            controller.deleteTextDoc(".\\Discover\\BackupDocument.txt");
            return false;
        }else{
            System.out.println("Please insert Y or N.");
            return existsBackUpDocument();
        }//if_else
    }//existsBackupDocument

    /* ************************ DESIGN ************************ */

    /**
     * Shows to user which action he could do with this software.
     * @throws IOException if the specified file path doesn't exist.
     */
    public void useCaseDesign() throws IOException, JWNLException {
        System.out.println("Use case:\n 1: Check if there are saved documents\n 2: Add a Gherkin file (.scenario) \n 3: Back");
        choice = scanner.nextLine();
        switch (choice){
            case ("1"):
                controller.existsGherkin(".\\Design\\BackupGherkin.txt");
                if(notifyMeDone()) {
                    if(existsBackUpGherkin())
                        useCaseBDLDesign();
                    else
                        useCaseDesign();
                }else{
                    System.out.println("There are no saved documents");
                    useCaseDesign();
                }//if_else
            case ("2"):
                System.out.println("Insert document's path: (ex. C:\\Users\\User\\Desktop\\example.scenario)");
                controller.addGherkin("Design", scanner.nextLine());
                if(notifyMeDone()) {
                    System.out.println("Scenario added.");
                    useCaseBDLDesign();
                }else {
                    System.out.println("The file is not a .scenario or it doesn't exist. Please retry.");
                    useCaseDesign();
                }
            case ("3"):
                useCaseNaturalAPI();
            default:
                System.out.println("Invalid choice. Please retry.");
                useCaseDesign();
        }//switch
    }//useCase

    private void useCaseBDLDesign() throws IOException, JWNLException {
        System.out.println("Use case:\n 1: Add a BDL (.BDL) \n 2: Back ");
        choice = scanner.nextLine();
        switch (choice){
            case ("1"):
                System.out.println("Insert document's path: (ex. C:\\Users\\User\\Desktop\\example.BDL)");
                controller.addBDL(scanner.nextLine());
                if(notifyMeDone()) {
                    System.out.println("BDL added.");
                    useCaseBO();
                }else {
                    System.out.println("The file is not a .BDL or it doesn't exist. Please retry.");
                    useCaseBDLDesign();
                }
            case ("2"):
                useCaseDesign();
            default:
                System.out.println("Invalid choice. Please retry.");
                useCaseBDLDesign();
        }//switch
    }//useCaseBDLDesign

    private void useCaseBO() throws IOException, JWNLException {
        System.out.println("Use case:\n 1: Add a Business Ontology (.json) [optional] \n 2: Extract BAL \n 3: Back ");
        choice = scanner.nextLine();
        switch (choice){
            case ("1"):
                System.out.println("Insert document's path: (ex. C:\\Users\\User\\Desktop\\example.json)");
                controller.createBO("Design", scanner.nextLine());
                if(notifyMeDone()){
                    System.out.println("Now a BAL document will be extracted.");
                    controller.createBAL();
                    controller.checkSuggestions();
                    useCaseDesign();
                }else{
                    System.out.println("The file is not a .json or it doesn't exist. Please retry.");
                    useCaseBO();
                }
            case ("2"):
                controller.createBAL();
                controller.checkSuggestions();
                useCaseDesign();
            case ("3"):
                useCaseDesign();
            default:
                System.out.println("Invalid choice. Please retry.");
                useCaseBO();
        }//switch
    }//useCaseBO

    public boolean existsBackUpGherkin() throws IOException {
        System.out.println("A Gherkin Scenario is already stored. Do you want to load it? (Y/N)");
        choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("y")) {
            controller.restoreBackup("Design");
            return true;
        }else if (choice.equalsIgnoreCase("n")){
            controller.deleteGherkin(".\\Design\\BackupGherkin.txt");
            return false;
        }else{
            System.out.println("Please insert Y or N.");
            return existsBackUpGherkin();
        }//if_else
    }//existsBackupBAL

    /* ************************ DEVELOP ************************ */

    /**
     * Shows to user which actions he could do with this software.
     * @throws IOException if the specified file path doesn't exist.
     */
    public void useCaseDevelop() throws IOException, JWNLException {
        System.out.println("Use case: \n 1: Check if there are saved documents\n 2: Add a new BAL (.json) \n 3: Back");
        choice = scanner.nextLine();
        switch(choice) {
            case ("1"):
                controller.existsBAL(".\\Develop\\BackupBAL.txt");
                if(notifyMeDone()) {
                    if(existsBackUpBAL())
                        useCaseBAL();
                    else
                        useCaseDevelop();
                }else{
                    System.out.println("There are no saved documents");
                    useCaseDevelop();
                }//if_else
            case ("2"):
                System.out.println("Insert document's path: (ex. C:\\Users\\User\\Desktop\\example.json)");
                controller.addBAL("Develop", scanner.nextLine());
                if(!notifyMeDone()) {
                    System.out.println("The file is not a .json or it doesn't exist. Please retry.");
                    useCaseDevelop();
                }else
                    useCasePLA();
            case ("3"):
                useCaseNaturalAPI();
            default:
                System.out.println("Invalid choice. Please retry.");
                useCaseDevelop();
        }//switch
    }//useCaseDevelop

    private void useCaseBAL() throws IOException, JWNLException {
        System.out.println("Use case: \n 1: Generate API \n 2: Back");
        choice = scanner.nextLine();
        switch (choice) {
            case ("1"):
                useCasePLA();
            case ("2"):
                useCaseDevelop();
            default:
                System.out.println("Invalid choice. Please retry.");
                useCaseBAL();
        }//switch
    }//useCaseBAL

    /**
     * Asks the user which programming language he want for the output file. He also can load a PLA file.
     * @throws IOException if the specified PLA file path doesn't exist.
     */
    public void useCasePLA() throws IOException, JWNLException {
        System.out.println("In which programming language do you want to generate API? \n 1: Java \n 2: JavaScript \n 3: Generate from an external PLA (.pla) \n 4: Back");
        choice = scanner.nextLine();
        switch(choice){
            case ("1"):
                controller.refreshPLA(".\\src\\main\\resources\\java.pla");
                controller.createAPI();
                checkUseCase(notifyMeError());
            case ("2"):
                controller.refreshPLA(".\\src\\main\\resources\\js.pla");
                controller.createAPI();
                checkUseCase(notifyMeError());
            case("3"):
                System.out.println("Insert document's path: (ex. C:\\Users\\User\\Desktop\\example.pla)");
                controller.addPLA("Develop", scanner.nextLine());
                if(notifyMeDone()){
                    controller.createAPI();
                    checkUseCase(notifyMeError());
                }else {
                    System.out.println("Please select a .pla file.");
                    useCasePLA();
                }
            case ("4"):
                useCaseDevelop();
            default:
                System.out.println("Invalid choice. Please retry.");
                useCasePLA();
        }//switch
    }//useCasePLA

    /**
     * Asks to user if he wants to reload an existing backup file.
     * @return boolean - true if user decide to reload a backup file; false if user doesn't want to reload it.
     * @throws IOException if the document path specified in backup is not valid anymore.
     */
    public boolean existsBackUpBAL() throws IOException {
        System.out.println("A BAL is already stored. Do you want to load it? (Y/N)");
        choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("y")) {
            controller.restoreBAL("Develop");
            return true;
        }else if (choice.equalsIgnoreCase("n")){
            controller.deleteBAL(".\\Develop\\BackupBAL.txt");
            return false;
        }else{
            System.out.println("Please insert Y or N.");
            return existsBackUpBAL();
        }//if_else
    }//existsBackupBAL

    /**
     * Shows different errors messages in case of problems and redirect the user to the correct corrective action.
     * @param code integer - error code.
     * @throws IOException propagation of the exception.
     */
    public void checkUseCase(int code) throws IOException, JWNLException {
        switch(code){
            case(0):
                useCasePLA();
            case(1):
                System.out.println("Add a PLA or choose a programming language.");
                useCasePLA();
            case(2):
                System.out.println("Add a BAL.");
                useCaseDevelop();
            case(3):
                System.out.println("Add a valid BAL.");
                useCaseDevelop();
            case(4):
                System.out.println("Add a valid PLA.");
                useCasePLA();
        }//switch
    }//checkUseCase

    /**
     * Receives presenterDevelop's message status and show it to user.
     */
    @Override
    public void notifyMe(){
        System.out.println(presenter.getMessage());
    }

    /**
     * Receives presenterDevelop's error code status.
     * @return integer - error code.
     */
    public int notifyMeError(){
        return presenter.getCode();
    }

    /**
     * Receives presenterDevelop's boolean status.
     * @return boolean - presenterDevelop status.
     */
    public boolean notifyMeDone(){
        return presenter.isDone();
    }

}//CLI
