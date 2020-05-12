/**
 * @file CreateBALOutputPort
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Denis Salviato
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.HexaTech.portInterface;

/**
 * CreateBAL output interface.
 */
public interface CreateBALOutputPort {

    /**
     * Notifies the message from BAL creation.
     * @param result string - message text.
     */
    void showCreatedBAL(String result);

}//CreateBAL
