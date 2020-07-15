package com.hexaTech.domain.port.out.usecase;

import java.io.File;
import java.io.IOException;

/**
 * ViewManual output interface
 */
public interface ViewManualOutputPort {

    /**
     * Notifies the message from opening the user manual.
     * @param result string - message text.
     */
    void showViewedManual(String result);

    void showDocument(File file) throws IOException;
}//ViewManualOutputPort
