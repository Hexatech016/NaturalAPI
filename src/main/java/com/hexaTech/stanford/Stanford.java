/**
 * @file Stanford
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Matteo Brosolo
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.stanford;

import com.hexaTech.entities.Gherkin;
import com.hexaTech.entities.DoubleStruct;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.parser.nndep.DependencyParser;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.CoreMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * Class used to apply Stanford methods into the project.
 */
public class Stanford implements StanfordInterface {

    private final StanfordCoreNLP pipeline;
    private final DependencyParser depparser;

    /**
     * Stanford class constructor.
     */
    public Stanford() {
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma");
        this.pipeline = new StanfordCoreNLP(props);
        this.depparser = DependencyParser.loadFromModelFile("edu/stanford/nlp/models/parser/nndep/english_UD.gz");
    }

    /**
     * Fills a list with elements found while parsing the given text.
     * @param content string - document's content to analyze.
     * @return List<DoubleStruct> - list of found elements.
     */
    @Override
    public List<DoubleStruct> extract(String content) {
        List<DoubleStruct> doubleStructs = new ArrayList<>();
        Annotation document = new Annotation(content);
        this.pipeline.annotate(document);
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
                    doubleStructs.add(new DoubleStruct(token.tag(), token.lemma()));
            }//for
        }//for
        return doubleStructs;
    }//extract

    /**
     * Extracts Gherkin elements from given text.
     * @param text string - text to be analyzed.
     * @return Gherkin - content extracted by the method.
     */
    @Override
    public Gherkin extractGherkin(String text) {
        String delimiter = "[\n]+";
        String[] arr= text.split(delimiter);
        Gherkin toRit = new Gherkin();
        String sentinel="";
        for (String str : arr) {
            CoreDocument documents = new CoreDocument(str);
            this.pipeline.annotate(documents);
            StringBuilder builder = new StringBuilder();
            String firstToken = documents.sentences().get(0).tokensAsStrings().get(0);
            if (firstToken.equalsIgnoreCase("AND")){
                firstToken=sentinel;
            }//if
            Annotation document = new Annotation(str);
            this.pipeline.annotate(document);
            GrammaticalStructure gStruct = depparser.predict(document);
            Collection<TypedDependency> dependencies = gStruct.typedDependencies();
            switch (firstToken.toLowerCase()) {
                case ("scenario"):
                    for (int i = 2; i < documents.sentences().get(0).lemmas().size(); i++) {
                        if(i>2)
                            builder.append(documents.sentences().get(0).lemmas().get(i).substring(0, 1).toUpperCase()).append(documents.sentences().get(0).lemmas().get(i).substring(1));
                        else
                            builder.append(documents.sentences().get(0).lemmas().get(i));
                    }//for
                    toRit.setScenario(builder.toString());
                    break;
                case ("given"):
                    toRit.setGiven("given");
                    sentinel="given";
                    break;
                case ("when"):
                    for (TypedDependency dep : dependencies) {
                        if (dep.reln().getShortName().equalsIgnoreCase("obj"))
                            builder.append(dep.dep().lemma()).append(" ");
                    }//for
                    toRit.getWhen().add(builder.toString());
                    sentinel="when";
                    break;
                case ("then"):
                    for (TypedDependency dep : dependencies) {
                        if (dep.reln().getShortName().equalsIgnoreCase("obj"))
                            builder.append(dep.dep().lemma()).append(" ");
                    }//for
                    toRit.setThen(builder.toString());
                    sentinel="then";
                    break;
            }//switch
        }//for
        return toRit;
    }//extractGherkin

}//Stanford
