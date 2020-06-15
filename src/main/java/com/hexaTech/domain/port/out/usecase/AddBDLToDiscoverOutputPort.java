package com.hexaTech.domain.port.out.usecase;

public interface AddBDLToDiscoverOutputPort {

    /**
     * Notifies the message from BDL addiction.
     * @param result string - message text.
     */
    void showAddedBDL(String result);

    void showDone(boolean done);
}
