package com.hexaTech.domain.port.in;

import java.io.IOException;

public interface AddGherkinToDiscoverInputPort {
    /**
     * Loads a new Gherkin scenario.
     * @throws IOException if an error occurs during loading process.
     */
    void addGherkin(String directory,String document) throws IOException;
}
