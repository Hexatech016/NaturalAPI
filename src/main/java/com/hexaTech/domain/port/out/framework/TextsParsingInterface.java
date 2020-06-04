package com.hexaTech.domain.port.out.framework;

import com.hexaTech.domain.entity.DoubleStruct;
import com.hexaTech.domain.entity.Gherkin;
import com.hexaTech.domain.entity.StructureBAL;

import java.util.HashMap;
import java.util.List;

public interface TextsParsingInterface {
    HashMap<List<DoubleStruct>,List<StructureBAL>> extractFromText(String content);
    List<Gherkin> extractFromGherkin(String content);
    List<DoubleStruct> extractBDLFromGherkin(String content);
}
