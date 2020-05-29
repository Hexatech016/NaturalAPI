package com.hexaTech.interactor.portInterface;

import net.didion.jwnl.JWNLException;

import java.io.IOException;

public interface CheckBetweenBDLAndGherkinInputPort {

    void check(String directory) throws IOException, JWNLException;
}
