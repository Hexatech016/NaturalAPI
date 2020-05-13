/**
 * @file DeleteDocOutputPort
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Eduard Serban
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.portInterface;

/**
 * DeleteDoc output interface
 */
public interface DeleteDocOutputPort {

    /**
     * Notifies the message from deleting document action.
     * @param result string - message text.
     */
    void showDeletedDoc(String result);

}//DeleteDocOutputPort
