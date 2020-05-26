/**
 * @file CLI
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Alessio Barbiero
 * @email hexatech016@gmail.com
 * @license MIT
*/

package com.hexaTech.client;

import com.hexaTech.controllerPresenter.*;

import java.io.IOException;
import java.util.Scanner;

/**
 * Class used to show output messages to user and receive input actions.
 */
public class CLI implements MyObserver {

    private final ControllerDiscover controllerDiscover;
    private final ControllerDesign controllerDesign;
    private final ControllerDevelop controllerDevelop;
    private final Presenter presenter;

    /**
     * CLI class constructor.
     * @param controllerDevelop Controller - controllerDevelop class parameter value.
     * @param presenter Presenter - presenter class parameter value.
     */
    public CLI(ControllerDiscover controllerDiscover, ControllerDesign controllerDesign, ControllerDevelop controllerDevelop, Presenter presenter) {
        this.controllerDiscover = controllerDiscover;
        this.controllerDesign = controllerDesign;
        this.controllerDevelop = controllerDevelop;
        this.presenter = presenter;
        this.presenter.addObserver(this);
    }

    public void useCaseNaturalAPI() throws IOException {
        String temp;
        System.out.println("Use case: \n 1: Discover \n 2: Design \n 3: Develop \n 4: Exit");
        Scanner scan = new Scanner(System.in);
        temp = scan.nextLine();
        switch (temp) {
            case ("1"):
                useCaseDiscover();
                break;
            case ("2"):
                useCaseDesign();
                break;
            case ("3"):
                controllerDevelop.existsDocController(".\\Develop\\BackupBAL.txt");
                if(notifyMeDone())
                    useCaseDevelop(existsBackUpDevelop());
                else
                    useCaseDevelop(false);
                break;
            case ("4"):
                System.out.println("Bye!");
                System.exit(0);
            default:
                useCaseNaturalAPI();
        }//switch
    }

    /**
     * Asks to user if he wants to reload an existing backup file.
     * @return boolean - true if user decide to reload a backup file; false if user doesn't want to reload it.
     * @throws IOException if the document path specified in backup is not valid anymore.
     */
    public boolean existsBackUpDevelop() throws IOException {
        System.out.println("A document is already stored. Do you want to load it? (Y/N)");
        Scanner scan = new Scanner(System.in);
        String answer = scan.nextLine();
        if (answer.equalsIgnoreCase("y")) {
            controllerDevelop.restoreBackupController("Develop");
            return true;
        }else if (answer.equalsIgnoreCase("n")){
            controllerDevelop.deleteDocController(".\\Develop\\BackupBAL.txt");
            return false;
        }else{
            System.out.println("Please insert Y or N.");
            return existsBackUpDevelop();
        }//if_else
    }//existsBackup

    /**
     * Shows to user which actions he could do with this software.
     * @param condition boolean - true if there's a loaded BAL file, false if not.
     * @throws IOException if the specified file path doesn't exist.
     */
    public void useCaseDevelop(boolean condition) throws IOException {
        String temp;
        if (!condition) {
            System.out.println("Use case: \n 1: Add a new BAL \n 2: Exit");
            Scanner scan = new Scanner(System.in);
            temp = scan.nextLine();
            switch (temp) {
                case ("1"):
                    controllerDevelop.addBALController("Develop");
                    if(!notifyMeDone())
                        System.out.println("Please select a .json file.");
                    useCaseDevelop(notifyMeDone());
                    break;
                case ("2"):
                    useCaseNaturalAPI();
                default:
                    useCaseDevelop(condition);
            }//switch
        }else{
            System.out.println("Use case: \n 1: Generate API \n 2: Exit");
            Scanner scan = new Scanner(System.in);
            temp = scan.nextLine();
            switch (temp) {
                case ("1"):
                    useCasePLA();
                    break;
                case ("2"):
                    useCaseNaturalAPI();
                default:
                    useCaseDevelop(condition);
            }//switch
        }//if_else
    }//useCaseDevelop

    /**
     * Asks the user which programming language he want for the output file. He also can load a PLA file.
     * @throws IOException if the specified PLA file path doesn't exist.
     */
    public void useCasePLA() throws IOException {
        String temp;
        System.out.println("In which programming language do you want to generate API? \n 1: Java \n 2: JavaScript \n 3: Generate from an external PLA \n 4: Exit");
        Scanner scan = new Scanner(System.in);
        temp = scan.nextLine();
        switch(temp){
            case ("1"):
                controllerDevelop.refreshPLAController(".\\src\\main\\resources\\java.pla");
                controllerDevelop.createAPIController();
                checkUseCase(notifyMeError());
            case ("2"):
                controllerDevelop.refreshPLAController(".\\src\\main\\resources\\js.pla");
                controllerDevelop.createAPIController();
                checkUseCase(notifyMeError());
            case("3"):
                controllerDevelop.addPLAController("Develop");
                if(notifyMeDone()){
                    controllerDevelop.createAPIController();
                    checkUseCase(notifyMeError());
                }else
                    System.out.println("Please select a .pla file.");
                useCasePLA();
            case ("4"):
                useCaseNaturalAPI();
            default:
                useCasePLA();
        }//switch
    }//useCasePLA

    /**
     * Shows different errors messages in case of problems and redirect the user to the correct corrective action.
     * @param code integer - error code.
     * @throws IOException propagation of the exception.
     */
    public void checkUseCase(int code) throws IOException {
        switch(code){
            case(0):
                useCasePLA();
            case(1):
                System.out.println("Add a PLA or choose a programming language.");
                useCasePLA();
            case(2):
                System.out.println("Add a BAL.");
                useCaseDevelop(false);
            case(3):
                System.out.println("Add a valid BAL.");
                useCaseDevelop(false);
            case(4):
                System.out.println("Add a valid PLA.");
                useCasePLA();
        }//switch
    }//checkUseCase

    /**
     * Shows to user which action he could do with this software.
     * @throws IOException if the specified file path doesn't exist.
     */
    public void useCaseDesign() throws IOException{
        String temp;
        while (true){
            System.out.println("Use case: \n 1: Add a Gherkin file  \n 2: Extract BAL \n 3: Add a Business Ontology \n 4: Extract a BO \n 5: Exit ");
            Scanner scan = new Scanner(System.in);
            temp = scan.nextLine();
            switch (temp) {
                /*case ("1"):
                    controller.addBDLController();
                    break;*/
                case ("1"):
                    controllerDesign.addGherkinController("Design");
                    break;
                case ("2"):
                    controllerDesign.createBALController();
                    controllerDesign.checkSuggestions();
                    break;
                case ("5"):
                    System.out.println("Bye!");
                    System.exit(0);
                case ("3"):
                    controllerDesign.addBOController("Design");
                    break;
                case ("4"):
                    controllerDesign.createBOController();
                    break;
            }//switch
        }//while
    }//useCase

    /**
     * Shows to user which actions he could do with this software.
     * @throws IOException if the specified file path doesn't exist.
     */
    public void useCaseDiscover() throws IOException {
        String firstCase;
        while (true) {
            System.out.println("Use Case: \n 1: Check if there are saved documents \n 2: Add a document  " +
                    "\n 3: Extract BDL \n 4: Exit");
            Scanner firstScanner = new Scanner(System.in);
            firstCase = firstScanner.nextLine();
            switch (firstCase) {
                case ("1"):
                    controllerDiscover.checkThereAreDoc(".\\Discover\\BackupDocument.txt");
                    if(notifyMeDone()){
                        System.out.println("There's a saved document. Do you want to load it? (Y/N)");
                        Scanner secondScanner = new Scanner(System.in);
                        String secondCase = secondScanner.nextLine();
                        if(secondCase.equalsIgnoreCase("y")) {
                            controllerDiscover.restoreDocController("Discover");
                        }else if(secondCase.equalsIgnoreCase("n")){
                            controllerDiscover.deleteDocController(".\\Discover\\BackupDocument.txt");
                        }else{
                            System.out.println("Please type Y or N");
                            break;
                        }//if_else
                    }//if_else
                    else
                        System.out.println("There are no saved documents");
                    break;
                case ("2"):
                    controllerDiscover.addDocController("Discover");
                    if(!notifyMeDone())
                        System.out.println("Please select a .txt file.");
                    else
                        System.out.println("Document added.");
                    break;
                case ("3"):
                    /*controllerDiscover.checkThereAreDoc();
                    if(notifyMeDone())
                        controllerDiscover.createBDL();
                    else
                        System.out.println("There are no loaded documents to extract BDL");*/
                    controllerDiscover.createBDL();
                    break;
                case ("4"):
                    System.out.println("Bye!");
                    System.exit(0);
                    /*case ("5"):
                    System.out.println("Restore Backup");
                    controllerDiscover.restoreBackupController("Discover");*/
            }//switch
        }//while
    }//useCase

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
