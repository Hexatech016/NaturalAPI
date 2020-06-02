package com.hexaTech.frameworks;

import com.hexaTech.interactor.entities.DoubleStruct;
import com.hexaTech.interactor.entities.Gherkin;
import com.hexaTech.interactor.frameworksInterface.TextsParsingInterface;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.parser.nndep.DependencyParser;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

@Component
public class StanfordNLP implements TextsParsingInterface {

    /**
     * Fills a list with elements found while parsing the given text.
     *
     * @param content string - document's content to analyze.
     * @return List<DoubleStruct> - list of found elements.
     */
    public List<DoubleStruct> extractFromText(String content) {
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
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
                    doubleStructs.add(new DoubleStruct("obj", dep.gov().lemma() + " " + dep.dep().lemma()));
            }//for
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                if (token.tag().contains("VB") || token.tag().contains("NN"))
                    if (!isACommonVerb(token.lemma()))
                        doubleStructs.add(new DoubleStruct(token.tag(), token.lemma()));
            }//for
        }//for
        return doubleStructs;
    }//extract

    private boolean isACommonVerb(String verb) {
        String[] commonVerbs = {"be", "have", "do", "say", "go", "get", "make", "know", "think",
                "take", "see", "come", "want", "look", "use", "find", "give", "tell",
                "work"};
        for (String commonV : commonVerbs) {
            if (commonV.equalsIgnoreCase(verb))
                return true;
        }
        return false;
    }

    public List<Gherkin> extractFromGherkin(String text) {
        List<Gherkin> toRet = new ArrayList<>();
        String[] gherkinSplit = text.split("[\n]+[\n]");
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        DependencyParser depparser = DependencyParser.loadFromModelFile("edu/stanford/nlp/models/parser/nndep/english_UD.gz");
        for (String scenario : gherkinSplit) {
            String[] arr = scenario.split("[\n]+");
            Gherkin toAdd = new Gherkin();
            String sentinel = "";
            for (String str : arr) {
                CoreDocument documents = new CoreDocument(str);
                pipeline.annotate(documents);
                StringBuilder builder = new StringBuilder();
                String firstToken = documents.sentences().get(0).tokensAsStrings().get(0);
                if (firstToken.equalsIgnoreCase("AND")) {
                    firstToken = sentinel;
                }//if
                Annotation document = new Annotation(str);
                pipeline.annotate(document);
                GrammaticalStructure gStruct = depparser.predict(document);
                Collection<TypedDependency> dependencies = gStruct.typedDependencies();
                switch (firstToken.toLowerCase()) {
                    case ("scenario"):
                        for (int i = 2; i < documents.sentences().get(0).lemmas().size(); i++) {
                            if (i > 2)
                                builder.append(documents.sentences().get(0).lemmas().get(i).substring(0, 1).toUpperCase()).append(documents.sentences().get(0).lemmas().get(i).substring(1));
                            else
                                builder.append(documents.sentences().get(0).lemmas().get(i));
                        }//for
                        toAdd.setScenario(builder.toString());
                        break;
                    case ("given"):
                        toAdd.setGiven("given");
                        sentinel = "given";
                        break;
                    case ("when"):
                        for (TypedDependency dep : dependencies) {
                            if (dep.reln().getShortName().equalsIgnoreCase("obj"))
                                builder.append(dep.dep().lemma()).append(" ");
                        }//for
                        toAdd.getWhen().add(builder.toString());
                        sentinel = "when";
                        break;
                    case ("then"):
                        for (TypedDependency dep : dependencies) {
                            if (dep.reln().getShortName().equalsIgnoreCase("obj"))
                                builder.append(dep.dep().lemma()).append(" ");
                        }//for
                        toAdd.setThen(builder.toString());
                        sentinel = "then";
                        break;
                }//switch
            }//for
            toRet.add(toAdd);
        }//for
        return toRet;
    }//extractFromGherkin
}//StanfordNLP
