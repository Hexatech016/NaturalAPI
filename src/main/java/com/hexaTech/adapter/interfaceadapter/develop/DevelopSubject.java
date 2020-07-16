/**
 * @file DiscoverSubject
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Luca Marcon
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.adapter.interfaceadapter.develop;

import com.hexaTech.adapter.interfaceadapter.MyObserver;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to synchronize actions and output messages.
 */
@Component
public abstract class DevelopSubject {

    private final List<MyObserver> observers=new ArrayList<>();

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
        for(MyObserver observer: observers) {
            observer.notifyMeDevelop();
        }
    }//notifySubMe

    /**
     * Notifies all observers' errors code.
     */
    public void notifySubMeError(){
        for(MyObserver observer: observers) {
            observer.notifyMeErrorDevelop();
        }
    }//notifySubMeError

    /**
     * Notifies all observers' status.
     */
    public void notifySubMeDone(){
        for(MyObserver observer: observers) {
            observer.notifyMeDoneDevelop();
        }
    }//notifySubMeDone

}//DevelopSubject
