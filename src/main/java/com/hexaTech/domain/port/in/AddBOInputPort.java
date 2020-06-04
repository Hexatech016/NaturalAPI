package com.hexaTech.domain.port.in;

import java.io.IOException;

public interface AddBOInputPort {

    void addBO(String directory, String document) throws IOException;
}
