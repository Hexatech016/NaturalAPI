package com.hexaTech.adapter.interfaceadapter;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to synchronize actions and output messages.
 */
@Component
public class ViewManualSubject {

    private List<MyObserver> observers=new ArrayList<>();

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
            observer.notifyMeManual();
        }
    }//notifySubMe

}//ViewManualSubject
