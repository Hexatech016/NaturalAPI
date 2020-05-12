/**
 * @file Presenter
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Denis Salviato
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.HexaTech.controllerPresenter;


import com.HexaTech.portInterface.AddBDLOutputPort;
import com.HexaTech.portInterface.AddGherkinOutputPort;
import com.HexaTech.portInterface.CreateBALOutputPort;

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
