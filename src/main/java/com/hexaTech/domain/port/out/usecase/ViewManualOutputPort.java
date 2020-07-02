package com.hexaTech.domain.port.out.usecase;

/**
 * ViewManual output interface
 */
public interface ViewManualOutputPort {

    /**
     * Notifies the message from opening the user manual.
     * @param result string - message text.
     */
    void showViewedManual(String result);
}//ViewManualOutputPort
