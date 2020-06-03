package com.hexaTech.interactor.frameworksInterface;

import com.hexaTech.entities.DoubleStruct;
import com.hexaTech.entities.Gherkin;
import com.hexaTech.entities.StructureBAL;

import java.util.HashMap;
import java.util.List;

public interface TextsParsingInterface {
    HashMap<List<DoubleStruct>,List<StructureBAL>> extractFromText(String content);
    List<Gherkin> extractFromGherkin(String content);
    List<DoubleStruct> extractBDLFromGherkin(String content);
}
