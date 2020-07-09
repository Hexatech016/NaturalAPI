package com.hexaTech.domain.port.in;

import java.io.IOException;

/**
 * ViewManual input interface
 */
public interface ViewManualInputPort {

    /**
     * Opens the user manual.
     * @param title String - path to the user manual.
     */
    void openManual(String title) throws IOException;

}//ViewManualInputPort
