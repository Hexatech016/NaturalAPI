/**
 * @file Presenter
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Luca Marcon
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.controllerPresenter;

import com.hexaTech.interactor.portInterface.*;
import com.hexaTech.interactor.portInterface.CheckBetweenBDLAndGherkinOutputPort;
import org.springframework.stereotype.Component;

/**
 * Class used to manage different output messages from interactor's actions.
 */
@Component
public class Presenter extends Subject implements AddDocumentOutputPort, CreateBDLOutputPort,
        AddBDLOutputPort, CheckBetweenBDLAndGherkinOutputPort, AddGherkinOutputPort, CreateBALOutputPort, AddBOOutputPort,
        AddPLAOutputPort, CreateAPIOutputPort, AddBALOutputPort{

    private String message;
    private boolean done;
    private int code;

    /**
     * Returns presenter message status.
     * @return string - message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns presenter error code.
     * @return integer - error code.
     */
    public int getCode() {
        return code;
    }

    /**
     * Returns presenter actions status.
     * @return boolean - true if the action has been correctly done, false if not.
     */
    public boolean isDone() {
        return done;
    }

    //Discover

    /**
     * Sets message from document adding action.
     * @param result string - message.
     */
    @Override
    public void showAddDocument(boolean result) {
        this.done=result;
        notifySubMe();
    }

    /**
     * Sets message from BDL creation.
     * @param result string - message.
     */
    @Override
    public void showCreateBdl(String result) {
        this.message=result;
        notifySubMe();
    }

    /**
     * Sets message from document deleting action.
     * @param result string - message.
     */
    @Override
    public void showDeletedDoc(String result){
        this.message=result;
        notifySubMe();
    }

    /**
     * Sets message from backup restoring action.
     * @param result string - message.
     */
    @Override
    public void showBackUpRestored(String result){
        this.message=result;
        notifySubMe();
    }

    //Design

    /**
     * Sets message from BDL addition.
     * @param result string - message.
     */
    @Override
    public void showAddedBDL(String result) {
        message=result;
        notifySubMe();
    }

    /**
     * Sets message from Gherkin addition.
     * @param result string - message.
     */
    public void showAddedGherkin(String result) {
        message=result;
        notifySubMe();
    }

    /**
     * Sets message from BAL creation.
     * @param result string - message.
     */
    public void showCreatedBAL(String result){
        message=result;
        notifySubMe();
    }

    public void showErrorBAL(String result){
        message=result;
        notifySubMe();
    }

    @Override
    public void showCreatedBO(String result) {
        message=result;
        notifySubMe();
    }

    public void showMessage(String result){
        message=result;
        notifySubMe();
    }

    //Develop


    /**
     * Sets message from document adding action.
     * @param result string - message.
     */
    @Override
    public void showAddedPLA(String result) {
        message=result;
        notifySubMe();
    }

    /**
     * Sets message from document removing action.
     * @param result string - message.
     */
    public void showRemovedPLA(String result){
        message=result;
        notifySubMe();
    }

    /**
     * Sets message from backup restoring action.
     * @param result string - message.
     */
    public void showRestoredBackUp(String result){
        message=result;
        notifySubMe();
    }

    /**
     * Sets message from API creation.
     * @param result string - message.
     */
    public void showCreatedAPI(String result){
        message=result;
        notifySubMe();
    }

    /**
     * Sets error code.
     * @param error integer - error code (0 is there's no error)
     */
    public void showErrorCodeAPI(int error){
        code=error;
        notifySubMeError();
    }

    /**
     * Notifies the message from removing document action.
     * @param result string - message text.
     */
    public void showErrorTextAPI(String result){
        message=result;
        notifySubMe();
    }

    /**
     * Notifies the message from adding document.
     *
     * @param result string - message text.
     */
    @Override
    public void showAddedBAL(String result) {
        message=result;
        notifySubMe();
    }

    /**
     * Notifies the message from removing document.
     *
     * @param result string - message text.
     */
    @Override
    public void showRemovedBAL(String result) {
        message=result;
        notifySubMe();
    }

    /**
     * Sets operation status.
     * @param b boolean - true if the operation has been completed without errors, false if not.
     */
    public void showDone(boolean b){
        done=b;
        notifySubMeDone();
    }

    @Override
    public void showCheck(String result) {
        message=result;
        notifySubMe();
    }
}//Presenter
