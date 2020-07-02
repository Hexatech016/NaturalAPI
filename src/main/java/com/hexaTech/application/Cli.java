/**
 * @file CLI
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Alessio Barbiero
 * @email hexatech016@gmail.com
 * @license MIT
*/

package com.hexaTech.application;

import com.hexaTech.adapter.interfaceadapter.MyObserver;
import com.hexaTech.adapter.interfaceadapter.ViewManualController;
import com.hexaTech.adapter.interfaceadapter.ViewManualPresenter;
import com.hexaTech.adapter.interfaceadapter.design.DesignController;
import com.hexaTech.adapter.interfaceadapter.design.DesignPresenter;
import com.hexaTech.adapter.interfaceadapter.develop.DevelopController;
import com.hexaTech.adapter.interfaceadapter.develop.DevelopPresenter;
import com.hexaTech.adapter.interfaceadapter.discover.DiscoverController;
import com.hexaTech.adapter.interfaceadapter.discover.DiscoverPresenter;
import net.didion.jwnl.JWNLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Class used to show output messages to user and receive input actions.
 */

@Component
public class Cli implements MyObserver {

    private final DiscoverController discoverController;
    private final DesignController designController;
    private final DevelopController developController;
    private final DiscoverPresenter discoverPresenter;
    private final DesignPresenter designPresenter;
    private final DevelopPresenter developPresenter;
    private final ViewManualController viewManualController;
    private final ViewManualPresenter viewManualPresenter;
    private final Scanner scanner;

    private String choice;

    @Autowired
    public Cli(DiscoverController discoverController, DesignController designController,
               DevelopController developController, ViewManualController viewManualController,
               DiscoverPresenter discoverPresenter, DesignPresenter designPresenter,
               DevelopPresenter developPresenter, ViewManualPresenter viewManualPresenter) {
        this.discoverController = discoverController;
        this.designController = designController;
        this.developController = developController;
        this.viewManualController = viewManualController;

        this.discoverPresenter = discoverPresenter;
        this.designPresenter = designPresenter;
        this.developPresenter = developPresenter;
        this.viewManualPresenter = viewManualPresenter;

        this.discoverPresenter.addObserver(this);
        this.designPresenter.addObserver(this);
        this.developPresenter.addObserver(this);
        this.viewManualPresenter.addObserver(this);

        this.scanner=new Scanner(System.in);
        this.choice="";
    }

    /**
     * Receives presenterDiscover's message status and show it to user.
     */
    public void notifyMeDiscover(){
        System.out.println(discoverPresenter.getMessage());
    }

    /**
     * Receives presenterDiscover's boolean status.
     * @return boolean - presenterDevelop status.
     */
    public boolean notifyMeDoneDiscover(){
        return discoverPresenter.isDone();
    }

    /**
     * Receives presenterDesign's message status and show it to user.
     */
    public void notifyMeDesign(){
        System.out.println(designPresenter.getMessage());
    }

    /**
     * Receives presenterDesign's boolean status.
     * @return boolean - presenterDevelop status.
     */
    public boolean notifyMeDoneDesign(){
        return designPresenter.isDone();
    }

    /**
     * Receives presenterDevelop's message status and show it to user.
     */
    public void notifyMeDevelop(){
        System.out.println(developPresenter.getMessage());
    }

    /**
     * Receives presenterDevelop's error code status.
     * @return integer - error code.
     */
    public int notifyMeErrorDevelop(){
        return developPresenter.getCode();
    }

    /**
     * Receives presenterDevelop's boolean status.
     * @return boolean - presenterDevelop status.
     */
    public boolean notifyMeDoneDevelop(){
        return developPresenter.isDone();
    }

    public void notifyMeManual(){System.out.println(viewManualPresenter.getMessage());}

    /* ************************ MAIN ************************ */

    public void useCaseNaturalAPI() throws IOException, JWNLException {
        System.out.println("\t\t--NATURAL API--");
        while(true){
            System.out.println("Use case: \n 1: Discover \n 2: Design \n 3: Develop \n 4: Guide \n 5: Exit");
            choice = scanner.nextLine();
            switch(choice){
                case("1"):
                    useCaseDiscover();
                    break;
                case("2"):
                    useCaseDesign();
                    break;
                case("3"):
                    useCaseDevelop();
                    break;
                case("4"):
                    viewManualController.openManualSection("MAIN:");
                    break;
                case ("5"):
                    System.out.println("\t\t--BYE!--");
                    System.exit(0);
                default:
                    System.out.println("\tInvalid choice. Please retry.\n");
            }//switch
        }//while
    }//useCaseNaturalAPI

    /* ************************ DISCOVER ************************ */

    private void useCaseDiscover() throws IOException, JWNLException {
        System.out.println("\t\t--DISCOVER--");
        while(true){
            System.out.println("Use case: \n 1: Check for saved documents \n 2: Add a document (.txt)" +
                    "\n 3: Check between BDL and Gherkin \n 4: Guide \n 5: Back");
            choice = scanner.nextLine();
            switch (choice) {
                case("1"):
                    discoverController.existsDoc("." + File.separator + "Discover" + File.separator + "BackupDocument.txt");
                    if(notifyMeDoneDiscover()){
                        System.out.println("The following document(s) are already stored: ");
                        discoverController.showBackup("Discover");
                        if (existsBackUpDocument())
                            useCaseBDL();
                    }else
                        System.out.println("\tThere are no saved documents\n");
                    break;
                case ("2"):
                    System.out.println("Insert document's path: (ex. C:Users\\User\\Desktop\\example.txt or /home/User/example.txt)");
                    discoverController.addTextDoc("Discover",scanner.nextLine());
                    if(!notifyMeDoneDiscover())
                        System.out.println("\tThe file is not a .txt or it doesn't exist. Please retry.\n");
                    else{
                        System.out.println("\tDocument added.\n");
                        useCaseBDL();
                    }//else
                    break;
                case ("3"):
                    System.out.println("Which BDL do you want to use for checking Gherkin?");
                    if(choiceOfBDL() && choiceOfGherkin())
                        discoverController.checkBetweenBDLAndGherkin("Discover");
                    break;
                case ("4"):
                    viewManualController.openManualSection("DISCOVER:");
                    viewManualController.openManualSection("BDL:");
                    viewManualController.openManualSection("GHERKIN:");
                    break;
                case ("5"):
                    return;
                default:
                    System.out.println("\tInvalid choice. Please retry.\n");
            }//switch
        }//while
    }//useCaseDiscover

    /**
     * Asks to user if he wants to reload an existing backup file.
     * @return boolean - true if user decide to reload a backup file; false if user doesn't want to reload it.
     * @throws IOException if the document path specified in backup is not valid anymore.
     */
    private boolean existsBackUpDocument() throws IOException {
        System.out.println("Do you want to load them? (Y/N)");
        while(true) {
            choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("y")) {
                discoverController.restoreTextDoc("Discover");
                return true;
            } else if (choice.equalsIgnoreCase("n")) {
                discoverController.deleteTextDoc("." + File.separator + "Discover" + File.separator + "BackupDocument.txt");
                return false;
            } else {
                System.out.println("\tPlease insert Y or N.\n");
            }//if_else
        }
    }//existsBackupDocument

    private boolean choiceOfBDL() throws IOException{
        while(true) {
            System.out.println("Use case: \n 1: Import an external BDL \n 2: Use a BDL extracted just before \n 3: Back");
            choice = scanner.nextLine();
            switch (choice) {
                case ("1"):
                    System.out.println("Insert document's path: (ex. C:\\Users\\User\\Desktop\\example.BDL or /home/User/example.BDL)");
                    discoverController.addBDL(scanner.nextLine());
                    if (notifyMeDoneDiscover()) {
                        System.out.println("\tBDL added.\n");
                        return true;
                    }else
                        System.out.println("\tThe file is not a .BDL or it doesn't exist. Please retry.\n");
                    break;
                case ("2"):
                    discoverController.checkIfRepoBDLIsEmpty();
                    if (notifyMeDoneDiscover())
                        System.out.println("\tThere is no BDL in memory. Please import an external one or extract one from document texts.\n");
                    else{
                        System.out.println("\tBDL is ready to be processed\n");
                        return true;
                    }//else
                    break;
                case ("3"):
                    return false;
                default:
                    System.out.println("\tInvalid choice. Please retry.\n");
            }//switch
        }//while
    }//choiceOfBDL

    private boolean choiceOfGherkin() throws IOException{
        while(true) {
            System.out.println("Use case: \n 1: Add a gherkin scenario \n 2: Back");
            choice = scanner.nextLine();
            switch (choice) {
                case ("1"):
                    System.out.println("Insert document's path: (ex. C:\\Users\\User\\Desktop\\example.scenario or /home/User/example.scenario)");
                    discoverController.addGherkin("Design", scanner.nextLine());
                    if (notifyMeDoneDiscover()) {
                        System.out.println("\tScenario added.\n");
                        return true;
                    } else
                        System.out.println("\tThe file is not a .scenario or it doesn't exist. Please retry.\n");
                    break;
                case ("2"):
                    return false;
                default:
                    System.out.println("\tInvalid choice. Please retry.\n");
            }//switch
        }//while
    }//choiceOfGherkin

    private void useCaseBDL() throws IOException{
        while(true) {
            System.out.println("Use case: \n 1: Extract BDL \n 2: Add another document (.txt) \n 3: Guide \n 4: Back");
            choice = scanner.nextLine();
            switch (choice) {
                case ("1"):
                    System.out.println("Choose a name for BDL.");
                    choice = scanner.nextLine();
                    if (!choice.equals("")) {
                        discoverController.createBDL(choice);
                        return;
                    } else
                        System.out.println("\tPlease insert a valid name.\n");
                    break;
                case ("2"):
                    System.out.println("Insert document's path: (ex. C:\\Users\\User\\Desktop\\example.txt or /home/User/example.txt)");
                    discoverController.addTextDoc("Discover", scanner.nextLine());
                    if (!notifyMeDoneDiscover()) {
                        System.out.println("\tThe file is not a .txt, it doesn't exist or it's already loaded. Please retry.\n");
                    } else
                        System.out.println("\tDocument added.\n");
                    break;
                case ("3"):
                    viewManualController.openManualSection("BDL:");
                    break;
                case ("4"):
                    return;
                default:
                    System.out.println("\tInvalid choice. Please retry.\n");
            }//switch
        }//while
    }//useCaseBDL

    /* ************************ DESIGN ************************ */

    private void useCaseDesign() throws IOException{
        System.out.println("\t\t--DESIGN--");
        while(true) {
            System.out.println("Use case:\n 1: Check for saved documents \n 2: Add a Gherkin file (.scenario) \n 3: Guide \n 4: Back");
            choice = scanner.nextLine();
            switch (choice) {
                case ("1"):
                    designController.existsGherkin("." + File.separator + "Design" + File.separator + "BackupGherkin.txt");
                    if (notifyMeDoneDesign()) {
                        if (existsBackUpGherkin())
                            useCaseBDLDesign();
                    } else
                        System.out.println("\tThere are no saved documents\n");
                    break;
                case ("2"):
                    System.out.println("Insert document's path: (ex. C:\\Users\\User\\Desktop\\example.scenario or /home/User/example.scenario)");
                    designController.addGherkin("Design", scanner.nextLine());
                    if (notifyMeDoneDesign()) {
                        System.out.println("\tScenario added.\n");
                        useCaseBDLDesign();
                    } else
                        System.out.println("\tThe file is not a .scenario or it doesn't exist. Please retry.\n");
                    break;
                case ("3"):
                    viewManualController.openManualSection("DESIGN:");
                    viewManualController.openManualSection("GHERKIN:");
                    break;
                case ("4"):
                    return;
                default:
                    System.out.println("\tInvalid choice. Please retry.\n");
            }//switch
        }//while
    }//useCaseDesign

    private boolean existsBackUpGherkin() throws IOException {
        System.out.println("A Gherkin Scenario is already stored. Do you want to load it? (Y/N)");
        while(true) {
            choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("y")) {
                designController.restoreBackup("Design");
                return true;
            } else if (choice.equalsIgnoreCase("n")) {
                designController.deleteGherkin("." + File.separator + "Design" + File.separator + "BackupGherkin.txt");
                return false;
            } else
                System.out.println("\tPlease insert Y or N.\n");
        }
    }//existsBackupGherkin

    private void useCaseBDLDesign() throws IOException {
        while(true) {
            System.out.println("Use case:\n 1: Add a BDL (.BDL) \n 2: Guide \n 3: Back ");
            choice = scanner.nextLine();
            switch (choice) {
                case ("1"):
                    System.out.println("Insert document's path: (ex. C:\\Users\\User\\Desktop\\example.BDL or /home/User/example.BDL)");
                    designController.addBDL(scanner.nextLine());
                    if (notifyMeDoneDesign()) {
                        System.out.println("\tBDL added.\n");
                        useCaseBO();
                        return;
                    } else
                        System.out.println("\tThe file is not a .BDL or it doesn't exist. Please retry.\n");
                    break;
                case ("2"):
                    viewManualController.openManualSection("BDL:");
                    break;
                case ("3"):
                    return;
                default:
                    System.out.println("\tInvalid choice. Please retry.\n");
            }//switch
        }//while
    }//useCaseBDLDesign

    private void useCaseBO() throws IOException {
        while(true) {
            System.out.println("Use case:\n 1: Add a Business Ontology (.json) [optional] \n 2: Extract BAL \n 3: Guide \n 4: Back ");
            choice = scanner.nextLine();
            switch (choice) {
                case ("1"):
                    System.out.println("Insert document's path: (ex. C:\\Users\\User\\Desktop\\example.json or /home/User/example.json)");
                    designController.createBO("Design", scanner.nextLine());
                    if (notifyMeDoneDesign()) {
                        useCaseBAL();
                        return;
                    }else
                        System.out.println("\tThe file is not a .json or it doesn't exist. Please retry.\n");
                    break;
                case ("2"):
                    useCaseBAL();
                    return;
                case ("3"):
                    viewManualController.openManualSection("BO:");
                    viewManualController.openManualSection("BAL:");
                    break;
                case ("4"):
                    return;
                default:
                    System.out.println("\tInvalid choice. Please retry.\n");
            }//switch
        }//while
    }//useCaseBO

    private void useCaseBAL() throws IOException {
        System.out.println("Choose a name for BAL.");
        while(true) {
            choice = scanner.nextLine();
            if (!choice.equals("")) {
                designController.createBAL(choice);
                designController.checkSuggestions(choice);
                return;
            } else
                System.out.println("\tPlease insert a valid name.\n");
        }//while
    }//useCaseBAL

    /* ************************ DEVELOP ************************ */

    /**
     * Shows to user which actions he could do with this software.
     * @throws IOException if the specified file path doesn't exist.
     */
    private void useCaseDevelop() throws IOException{
        System.out.println("\t\t--DEVELOP--");
        while(true) {
            System.out.println("Use case: \n 1: Check for saved documents\n 2: Add a new BAL (.json) \n 3: Guide \n 4: Back");
            choice = scanner.nextLine();
            switch (choice) {
                case ("1"):
                    developController.existsBAL("." + File.separator + "Develop" + File.separator + "BackupBAL.txt");
                    if (notifyMeDoneDevelop()) {
                        if (existsBackUpBAL())
                            useCasePLA();
                    } else
                        System.out.println("\tThere are no saved documents\n");
                    break;
                case ("2"):
                    System.out.println("Insert document's path: (ex. C:\\Users\\User\\Desktop\\example.json or /home/User/example.json)");
                    developController.addBAL("Develop", scanner.nextLine());
                    if (!notifyMeDoneDevelop())
                        System.out.println("\tThe file is not a .json or it doesn't exist. Please retry.\n");
                    else
                        useCasePLA();
                    break;
                case ("3"):
                    viewManualController.openManualSection("DEVELOP:");
                    viewManualController.openManualSection("BAL:");
                    break;
                case ("4"):
                    return;
                default:
                    System.out.println("\tInvalid choice. Please retry.\n");
            }//switch
        }//while
    }//useCaseDevelop

    /**
     * Asks to user if he wants to reload an existing backup file.
     * @return boolean - true if user decide to reload a backup file; false if user doesn't want to reload it.
     * @throws IOException if the document path specified in backup is not valid anymore.
     */
    private boolean existsBackUpBAL() throws IOException {
        System.out.println("A BAL is already stored. Do you want to load it? (Y/N)");
        while (true) {
            choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("y")) {
                developController.restoreBAL("Develop");
                return true;
            } else if (choice.equalsIgnoreCase("n")) {
                developController.deleteBAL("." + File.separator + "Develop" + File.separator + "BackupBAL.txt");
                return false;
            } else
                System.out.println("\tPlease insert Y or N.\n");
        }//while
    }//existsBackupBAL

    /**
     * Asks the user which programming language he want for the output file. He also can load a PLA file.
     * @throws IOException if the specified PLA file path doesn't exist.
     */
    private void useCasePLA() throws IOException{
        while(true) {
            System.out.println("\nIn which programming language do you want to generate API? \n 1: Java \n 2: JavaScript \n" +
                                "3: Generate from an external PLA (.pla) \n 4: Guide \n 5: Back");
            choice = scanner.nextLine();
            switch (choice) {
                case ("1"):
                    developController.refreshPLA("." + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "java.pla");
                    developController.createAPI();
                    if(!checkUseCase(notifyMeErrorDevelop()))
                        return;
                    break;
                case ("2"):
                    developController.refreshPLA("." + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "js.pla");
                    developController.createAPI();
                    if(!checkUseCase(notifyMeErrorDevelop()))
                        return;
                    break;
                case ("3"):
                    System.out.println("Insert document's path: (ex. C:\\Users\\User\\Desktop\\example.pla or /home/User/example.pla)");
                    developController.addPLA("Develop", scanner.nextLine());
                    if (notifyMeDoneDevelop()) {
                        developController.createAPI();
                        if(!checkUseCase(notifyMeErrorDevelop()))
                            return;
                    } else
                        System.out.println("\tPlease select a .pla file.\n");
                    break;
                case ("4"):
                    viewManualController.openManualSection("PLA:");
                    break;
                case ("5"):
                    return;
                default:
                    System.out.println("\tInvalid choice. Please retry.\n");
            }//switch
        }//while
    }//useCasePLA

    /**
     * Shows different errors messages in case of problems and redirect the user to the correct corrective action.
     * @param code integer - error code.
     */
    private boolean checkUseCase(int code){
        switch(code){
            case(0):
                return true;
            case(1):
                System.out.println("\tAdd a PLA or choose a programming language.\n");
                return true;
            case(2):
                System.out.println("\tAdd a BAL.\n");
                return false;
            case(3):
                System.out.println("\tAdd a valid BAL.\n");
                return false;
            case(4):
                System.out.println("\tAdd a valid PLA.\n");
                return true;
        }//switch
        return false;
    }//checkUseCase

}//CLI


