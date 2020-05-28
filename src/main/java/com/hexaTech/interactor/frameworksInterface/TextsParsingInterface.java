package com.hexaTech.interactor.frameworksInterface;

import com.hexaTech.interactor.entities.DoubleStruct;
import com.hexaTech.interactor.entities.Gherkin;

import java.util.List;

public interface TextsParsingInterface {
    List<DoubleStruct> extractFromText(String content);
    Gherkin extractFromGherkin(String content);
}
