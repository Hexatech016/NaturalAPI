package com.hexaTech.repo;

import com.hexaTech.entities.BDL;
import com.hexaTech.entities.DoubleStruct;
import com.hexaTech.repointerface.RepoBDLInterface;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.parser.nndep.DependencyParser;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.CoreMap;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RepoBDL implements RepoBDLInterface {



    public BDL extractBDL(String text) throws IOException {
        BDL bdlToReturn =new BDL();
        List<DoubleStruct> result = extract(text);
        bdlToReturn.addSostFromDoubleStruct(result);
        bdlToReturn.addVerbFromDoubleStruct(result);
        bdlToReturn.addPredFromDoubleStruct(result);
        return bdlToReturn;
    }

    /**
     * Fills a list with elements found while parsing the given text.
     * @param content string - document's content to analyze.
     * @return List<DoubleStruct> - list of found elements.
     */
    private List<DoubleStruct> extract(String content) {
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
                    doubleStructs.add(new DoubleStruct(token.tag(), token.lemma()));
            }//for
        }//for
        return doubleStructs;
    }//extract

    public void saveBDL(BDL bdl) throws IOException {
        saveDocDiscover(bdl.toString(),".\\BDLreadble.txt");
        saveDocDiscover(bdl.sostToCVS(),".\\BDLsost.csv");
        saveDocDiscover(bdl.verbToCVS(),".\\BDLverbs.csv");
        saveDocDiscover(bdl.predToCVS(),".\\BDLpred.csv");
    }

    private void saveDocDiscover(String doc, String path) throws IOException {
        try {
            // Open given file in append mode.
            File directory = new File("Discover");
            if (! directory.exists())
                directory.mkdir();
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(directory + "/" +path));
            String[] rows=doc.split("\n");
            for(String row: rows){
                out.write(row);
                out.newLine();
            }//for
            out.close();
        }catch (IOException e) {
            System.out.println("exception occurred " + e);
        }//try_catch
    }//saveDocDiscover

    /**
     * Loads BDL content from different files and store a new BDL object.
     * @throws IOException if an error occurs while loading one or more file.
     */
    /* DA IMPLEMENTARE
    public void getBDLFromContentPath() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String noun=fileSystem.getContentFromPath(".\\Discover\\BDL_nouns.txt");
        String verb=fileSystem.getContentFromPath(".\\Discover\\BDL_verbs.txt");
        String pred=fileSystem.getContentFromPath(".\\Discover\\BDL_predicates.txt");
        Map<String,Integer> nouns=mapper.readValue(noun, HashMap.class);
        Map<String,Integer> verbs=mapper.readValue(verb, HashMap.class);
        Map<String,Integer> predicates=mapper.readValue(pred, HashMap.class);
        BDL BDLtoGet=new BDL(nouns,verbs,predicates);
        System.out.println(BDLtoGet.toString());
    }//getBDLFromContentPath*/



    @Override
    public boolean importDoc(String directory) throws IOException {
        return false;
    }

    @Override
    public void saveDoc(String title, String directory) {

    }

    @Override
    public boolean existsDoc(String path) {
        return false;
    }

    @Override
    public boolean deleteDoc(String path) {
        return false;
    }

    @Override
    public String getContentFromPath(String path) throws IOException {
        return null;
    }

    @Override
    public void loadBackup(String directory) throws IOException {

    }
}
