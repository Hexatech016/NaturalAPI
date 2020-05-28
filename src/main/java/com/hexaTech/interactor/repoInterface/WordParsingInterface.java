package com.hexaTech.interactor.repoInterface;

import net.didion.jwnl.JWNLException;

import java.io.FileNotFoundException;

public interface WordParsingInterface {
    boolean thisNounIsASynonymOf(String word, String target) throws FileNotFoundException, JWNLException;
    boolean thisVerbIsASynonymOf(String word, String target) throws FileNotFoundException, JWNLException;

}
