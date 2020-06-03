package com.hexaTech.interactor.frameworksInterface;

import com.hexaTech.entities.DoubleStruct;
import com.hexaTech.entities.Gherkin;

import java.util.List;

public interface TextsParsingInterface {
    List<DoubleStruct> extractFromText(String content);
    List<Gherkin> extractFromGherkin(String content);
    List<DoubleStruct> extractBDLFromGherkin(String content);
}
