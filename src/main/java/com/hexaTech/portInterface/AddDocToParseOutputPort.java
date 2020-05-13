/**
 * @file AddDocToParseOutputPort
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Eduard Serban
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.portInterface;

/**
 * AddDocument output interface.
 */
public interface AddDocToParseOutputPort {

    /**
     * Notifies the message from adding document action.
     * @param result string - message text.
     */
    void showAddDocument(boolean result);

    /**
     * Notifies the message from restoring backup action.
     * @param result string - message text.
     */
    void showBackUpRestored(String result);

}//AddDocToParseOutputPort
