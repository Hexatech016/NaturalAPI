package com.hexaTech.frameworks;

import com.hexaTech.interactor.entities.DoubleStruct;
import com.hexaTech.interactor.frameworksInterface.TextsParsingInterface;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.parser.nndep.DependencyParser;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.CoreMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class StanfordNLP implements TextsParsingInterface {

    /**
     * Fills a list with elements found while parsing the given text.
     * @param content string - document's content to analyze.
     * @return List<DoubleStruct> - list of found elements.
     */
    public List<DoubleStruct> extract(String content) {
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma");
        StanfordCoreNLP pipeline=new StanfordCoreNLP(props);
        DependencyParser depparser = DependencyParser.loadFromModelFile("edu/stanford/nlp/models/parser/nndep/english_UD.gz");
        List<DoubleStruct> doubleStructs = new ArrayList<>();
        Annotation document = new Annotation(content);
        pipeline.annotate(document);
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            GrammaticalStructure gStruct = depparser.predict(sentence);
            Collection<TypedDependency> dependencies = gStruct.typedDependencies();
            for (TypedDependency dep : dependencies) {
                if (dep.reln().getShortName().equalsIgnoreCase("obj"))
                    doubleStructs.add(new DoubleStruct("obj",dep.gov().lemma()+ " "+ dep.dep().lemma()));
            }//for
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                if (token.tag().contains("VB") || token.tag().contains("NN"))
                    if(!isACommonVerb(token.lemma()))
                        doubleStructs.add(new DoubleStruct(token.tag(), token.lemma()));
            }//for
        }//for
        return doubleStructs;
    }//extract

    private boolean isACommonVerb(String verb){
        String [] commonVerbs= {"be", "have", "do", "say", "go", "get", "make", "know", "think",
                "take", "see", "come", "want", "look", "use", "find", "give", "tell",
                "work"};
        for(String commonV: commonVerbs) {
            if (commonV.equalsIgnoreCase(verb))
                return true;
        }
        return false;
    }
}
