/**
 * @file Presenter
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Eduard Serban
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.controllerPresenter;


import com.hexaTech.portInterface.AddDocToParseOutputPort;
import com.hexaTech.portInterface.CheckThereAreDocOutputPort;
import com.hexaTech.portInterface.CreateBDLOutputPort;
import com.hexaTech.portInterface.DeleteDocOutputPort;

/**
 * Class used to manage different output messages from interactor's actions.
 */
public class PresenterDiscover extends SubjectDiscover implements AddDocToParseOutputPort, CreateBDLOutputPort,
        DeleteDocOutputPort, CheckThereAreDocOutputPort {
    private String presenter;
    private boolean flag;

    /**
     * Returns presenter message status.
     * @return string - message.
     */
    public String getPresenter() {
        return presenter;
    }

    /**
     * Returns presenter flag status.
     * @return boolean - true if the action has been correctly done, false if not.
     */
    public boolean isFlag() {
        return flag;
    }

    /**
     * Sets message from document adding action.
     * @param result string - message.
     */
    @Override
    public void showAddDocument(String result) {
        this.presenter=result;
        notifySubMe();
    }

    /**
     * Sets message from BDL creation.
     * @param result string - message.
     */
    @Override
    public void showCreateBdl(String result) {
        this.presenter=result;
        notifySubMe();
    }

    /**
     * Sets message from document deleting action.
     * @param result string - message.
     */
    @Override
    public void showDeletedDoc(String result){
        this.presenter=result;
        notifySubMe();
    }

    /**
     * Sets message from backup restoring action.
     * @param result string - message.
     */
    @Override
    public void showBackUpRestored(String result){
        this.presenter=result;
        notifySubMe();
    }

    /**
     * Sets flag from document searching action.
     * @param flag boolean - search result.
     */
    @Override
    public void thereAreDoc(boolean flag){
        this.flag=flag;
        notifyFlagMe();
    }

}//Presenter
