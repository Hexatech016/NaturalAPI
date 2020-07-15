package com.hexaTech.adapter.interfaceadapter.design;

import com.hexaTech.domain.port.out.usecase.AddBDLOutputPort;
import com.hexaTech.domain.port.out.usecase.AddBOOutputPort;
import com.hexaTech.domain.port.out.usecase.AddGherkinOutputPort;
import com.hexaTech.domain.port.out.usecase.CreateBALOutputPort;
import org.springframework.stereotype.Component;

@Component
public class DesignPresenter extends DesignSubject implements AddBDLOutputPort, AddBOOutputPort,
        AddGherkinOutputPort, CreateBALOutputPort {
    private String message;

    private boolean done;

    /**
     * Returns presenter message status.
     * @return string - message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns presenter actions status.
     * @return boolean - true if the action has been correctly done, false if not.
     */
    public boolean isDone() {
        return done;
    }

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
     * Sets operation status.
     * @param b boolean - true if the operation has been completed without errors, false if not.
     */
    public void showDone(boolean b){
        done=b;
        notifySubMeDone();
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

    /*public void showErrorBAL(String result){
        message=result;
        notifySubMe();
    }*/

    @Override
    public void showCreatedBO(String result) {
        message=result;
        notifySubMe();
    }

    /*public void showMessage(String result){
        message=result;
        notifySubMe();
    }*/
}
