/**
 * @file Presenter
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Denis Salviato
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.controllerPresenter;


import com.hexaTech.portInterface.AddBDLOutputPort;
import com.hexaTech.portInterface.AddGherkinOutputPort;
import com.hexaTech.portInterface.CreateBALOutputPort;

/**
 * Class used to manage different output messages from interactor's actions.
 */
public class PresenterDesign extends SubjectDesign implements AddBDLOutputPort, AddGherkinOutputPort, CreateBALOutputPort {
    String s;

    public String getS() {
        return s;
    }

    /**
     * Sets message from BDL addition.
     * @param result string - message.
     */
    @Override
    public void showAddedBDL(String result) {
        s=result;
        notifySubMe();
    }

    /**
     * Sets message from Gherkin addition.
     * @param result string - message.
     */
    public void showAddedGherkin(String result) {
        s=result;
        notifySubMe();
    }

    /**
     * Sets message from BAL creation.
     * @param result string - message.
     */
    public void showCreatedBAL(String result){
        s=result;
        notifySubMe();
    }

}//Presenter
