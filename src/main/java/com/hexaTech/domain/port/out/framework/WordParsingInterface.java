package com.hexaTech.domain.port.out.framework;

import net.didion.jwnl.JWNLException;

import java.io.FileNotFoundException;

public interface WordParsingInterface {
    boolean thisNounIsASynonymOf(String noun, String target) throws JWNLException;
    boolean thisVerbIsASynonymOf(String verb, String target) throws JWNLException;

}
