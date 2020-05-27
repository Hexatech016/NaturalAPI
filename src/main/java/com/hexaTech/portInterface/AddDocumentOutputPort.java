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
 * AddPDocToParse output interface.
 */
public interface AddDocumentOutputPort {

    /**
     * Notifies the flag from document search action.
     * @param flag boolean - true if backup was found, false if not.
     */
    void showDone(boolean flag);

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

    /**
     * Notifies the message from deleting document action.
     * @param result string - message text.
     */
    void showDeletedDoc(String result);

}//AddDocToParseOutputPort
