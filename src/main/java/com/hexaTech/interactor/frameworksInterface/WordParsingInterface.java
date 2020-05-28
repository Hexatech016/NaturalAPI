package com.hexaTech.interactor.frameworksInterface;

import net.didion.jwnl.JWNLException;

import java.io.FileNotFoundException;

public interface WordParsingInterface {
    boolean thisNounIsASynonymOf(String noun, String target) throws FileNotFoundException, JWNLException;
    boolean thisVerbIsASynonymOf(String verb, String target) throws FileNotFoundException, JWNLException;

}
