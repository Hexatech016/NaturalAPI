/**
 * @file AddGherkinInputPort
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Gerardo Kokoshari
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.portInterface;

import java.io.IOException;

/**
 * AddGherkin input interface.
 */
public interface AddGherkinInputPort {

    /**
     * Loads a new Gherkin scenario.
     * @throws IOException if an error occurs during loading process.
     */
    void addGherkin() throws IOException;

}//AddGherkinInputInterface
