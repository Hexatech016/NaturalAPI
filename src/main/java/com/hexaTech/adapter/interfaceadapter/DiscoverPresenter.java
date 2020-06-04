/**
 * @file Presenter
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Luca Marcon
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.adapter.interfaceadapter;

import com.hexaTech.domain.port.out.usecase.*;
import org.springframework.stereotype.Component;

/**
 * Class used to manage different output messages from interactor's actions.
 */
@Component
public class DiscoverPresenter extends DiscoverSubject implements AddDocumentOutputPort, CreateBDLOutputPort,
        CheckBetweenBDLAndGherkinOutputPort {

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

    @Override
    public void showCheck(String result) {
        message=result;
        notifySubMe();
    }
}//Presenter
