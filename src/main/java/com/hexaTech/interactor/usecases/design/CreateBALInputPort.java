/**
 * @file CreateBALInputPort
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Denis Salviato
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.interactor.usecases.design;

import java.io.IOException;

/**
 * CreateBAL input interface.
 */
public interface CreateBALInputPort {

    /**
     * Create a new BAL object.
     * @throws IOException if an error occurs during loading process.
     */
    void createBAL() throws IOException;

    void checkTypes() throws IOException;

}//CreateBALInputPort
