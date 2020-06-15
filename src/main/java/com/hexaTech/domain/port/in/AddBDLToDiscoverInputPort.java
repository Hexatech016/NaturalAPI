package com.hexaTech.domain.port.in;

import java.io.IOException;

public interface AddBDLToDiscoverInputPort {

    /**
     * Loads a new BDL.
     * @throws IOException if an error occurs during loading process.
     * @param document
     */
    void addBDL(String document) throws IOException;

    void checkIfRepoBDLIsEmpty();
}
