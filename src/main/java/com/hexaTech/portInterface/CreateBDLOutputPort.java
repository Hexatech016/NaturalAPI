/**
 * @file CreateBDLOutputPort
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Eduard Serban
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.portInterface;

/**
 * CreateBDL output interface.
 */
public interface CreateBDLOutputPort {

    /**
     * Notifies the message from creating BDL action.
     * @param result string - message text.
     */
    void showCreateBdl(String result);

}//CreateBDLOutputPort
