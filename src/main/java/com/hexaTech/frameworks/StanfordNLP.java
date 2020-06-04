package com.hexaTech.frameworks;

import com.hexaTech.entities.DoubleStruct;
import com.hexaTech.entities.Gherkin;
import com.hexaTech.entities.Parameter;
import com.hexaTech.entities.StructureBAL;
import com.hexaTech.interactor.frameworksInterface.TextsParsingInterface;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.parser.nndep.DependencyParser;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphFactory;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class StanfordNLP implements TextsParsingInterface {

    /**
     * Fills a list with elements found while parsing the given text.
     *
     * @param content string - document's content to analyze.
     * @return List<DoubleStruct> - list of found elements.
     */
    public HashMap<List<DoubleStruct>,List<StructureBAL>> extractFromText(String content) {
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        DependencyParser depparser = DependencyParser.loadFromModelFile("edu/stanford/nlp/models/parser/nndep/english_UD.gz");
        HashMap<List<DoubleStruct>,List<StructureBAL>> toReturn=new HashMap<>();
        List<DoubleStruct> doubleStructs = new ArrayList<>();
        List<StructureBAL> structureBALList=new ArrayList<>();
        Annotation document = new Annotation(content);
        pipeline.annotate(document);
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            GrammaticalStructure gStruct = depparser.predict(sentence);
            Collection<TypedDependency> dependencies = gStruct.typedDependencies();
            for (TypedDependency dep : dependencies) {
                if (dep.reln().getShortName().equalsIgnoreCase("obj")) {
                    doubleStructs.add(new DoubleStruct("obj", dep.gov().lemma()+" "+dep.dep().lemma()));
                    if(dep.gov().lemma().equalsIgnoreCase("have"))
                        structureBALList.add(extractBO(props,sentence));
                }
            }//for
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                if (token.tag().contains("VB") || token.tag().contains("NN"))
                    if (!isACommonVerb(token.lemma()))
                        doubleStructs.add(new DoubleStruct(token.tag(), token.lemma()));
            }//for
        }//for
        toReturn.put(doubleStructs,structureBALList);
        return toReturn;
    }//extract

    private StructureBAL extractBO(Properties properties, CoreMap sentence){
        Sentence phrase=new Sentence(sentence.toString());
        SemanticGraph semanticGraph=phrase.dependencyGraph(properties, SemanticGraphFactory.Mode.ENHANCED);
        Collection<TypedDependency> dependencies = semanticGraph.typedDependencies();
        StructureBAL bo=new StructureBAL();
        for (TypedDependency dep : dependencies) {
            if(dep.reln().getShortName().equalsIgnoreCase("nsubj"))
                bo.setName(getLemma(dep.dep().toString()).substring(0,1).toUpperCase()+getLemma(dep.dep().toString()).substring(1));
            if(dep.reln().getShortName().equalsIgnoreCase("obj"))
                bo.setParameters(new Parameter("", getLemma(dep.dep().toString()), "string"));
        }//for
        return bo;
    }//extractBO

    private String getLemma(String word){
        return word.substring(0,word.indexOf("/"));
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

    public List<DoubleStruct> extractBDLFromGherkin(String content){
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
                if (dep.reln().getShortName().equalsIgnoreCase("obj") &&
                        !dep.dep().lemma().equalsIgnoreCase("feature") &&
                        !dep.dep().lemma().equalsIgnoreCase("scenario"))
                    doubleStructs.add(new DoubleStruct("obj", dep.gov().lemma() + " " + dep.dep().lemma()));
            }//for
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                if (token.tag().contains("VB") || token.tag().contains("NN") &&
                        !token.lemma().equalsIgnoreCase("feature")&&
                        !token.lemma().equalsIgnoreCase("scenario"))
                    if (!isACommonVerb(token.lemma()))
                        doubleStructs.add(new DoubleStruct(token.tag(), token.lemma()));
            }//for
        }//for
        return doubleStructs;

    }

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
}//StanfordNLP
