/**
 * @file Subject
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Eduard Serban
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.controllerPresenter;


import com.hexaTech.client.MyObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to synchronize actions and output messages.
 */
public class SubjectDiscover {

    List<MyObserver> observers=new ArrayList<>();

    /**
     * Adds an observer to subject's observers list.
     * @param observer MyObserver - observer to add.
     */
    public void addObserver(MyObserver observer){
        observers.add(observer);
    }

    /**
     * Notifies all observers' message change.
     */
    public void notifySubMe(){
        for(MyObserver observer: this.observers) {
            observer.notifyMe();
        }//for
    }//notifySubMe

    /**
     * Notifies all observers' flag status.
     */
    public void notifyFlagMe(){
        for(MyObserver observer: this.observers) {
            observer.notifyMeDone();
        }//for
    }//notifyFlagMe

}//Subject
