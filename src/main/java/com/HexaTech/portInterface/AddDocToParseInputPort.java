/**
 * @file AddDocToParseInputPort
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Eduard Serban
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.HexaTech.portInterface;

import java.io.IOException;

/**
 * AddDocToParse input interface.
 */
public interface AddDocToParseInputPort {

    /**
     * Loads a new document.
     * @throws IOException if an error occurs during loading process.
     */
    void addDocument() throws IOException;

    /**
     * Load a backup file.
     * @throws IOException if the file doesn't exist.
     */
    void loadBackUp() throws IOException;

}//AddDocToParseInputPort
