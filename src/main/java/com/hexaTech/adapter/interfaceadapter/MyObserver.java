/**
 * @file MyObserver
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Jacopo Battilana
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.adapter.interfaceadapter;

/**
 * Client interface
 */
public interface MyObserver {
    /**
     * Shows presenter message.
     */
    void notifyMeDiscover();

    /**
     * Notifies presenter status.
     * @return boolean - status.
     */
    boolean notifyMeDoneDiscover();

    /**
     * Shows presenter message.
     */
    void notifyMeDesign();


    /**
     * Notifies presenter status.
     * @return boolean - status.
     */
    boolean notifyMeDoneDesign();

    /**
     * Shows presenter message.
     */
    void notifyMeDevelop();

    /**
     * Notifies presenter error code.
     * @return integer - error code.
     */
    int notifyMeErrorDevelop();

    /**
     * Notifies presenter status.
     * @return boolean - status.
     */
    boolean notifyMeDoneDevelop();

    void notifyMeManual();

}//MyObserver
