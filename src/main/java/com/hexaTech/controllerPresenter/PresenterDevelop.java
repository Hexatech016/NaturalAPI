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

import com.hexaTech.portInterface.AddDocumentOutputPort;
import com.hexaTech.portInterface.CreateAPIOutputPort;

/**
 * Class used to manage different output messages from interactor's actions.
 */
public class PresenterDevelop extends SubjectDevelop implements AddDocumentOutputPort, CreateAPIOutputPort {
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

    /**
     * Sets message from document adding action.
     * @param result string - message.
     */
    @Override
    public void showAddedDocument(String result) {
        message=result;
        notifySubMe();
    }

    /**
     * Sets message from document removing action.
     * @param result string - message.
     */
    public void showRemovedDocument(String result){
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
     * Sets operation status.
     * @param b boolean - true if the operation has been completed without errors, false if not.
     */
    public void showDone(boolean b){
        done=b;
        notifySubMeDone();
    }

}//Presenter
