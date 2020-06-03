/**
 * @file AddGherkinOutputPort
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Gerardo Kokoshari
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.interactor.usecases.design;

/**
 * AddGherkin output interface.
 */
public interface AddGherkinOutputPort {

    /**
     * Notifies the message from Gherkin addiction.
     * @param result string - message text.
     */
    void showAddedGherkin(String result);

    void showDone(boolean done);

}//AddGherkinOutputPort
