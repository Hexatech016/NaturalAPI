package com.hexaTech.repo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;
import com.hexaTech.Main;
import com.hexaTech.entities.BDL;
import com.hexaTech.entities.Document;
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

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.*;

public class RepoBDL implements RepoBDLInterface {

    private BDL bdl;

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

    public int getTotalFrequency(Map<String,Integer> list) {
        int totalFrenquncy=0;
        for (Map.Entry<String, Integer> lista : list.entrySet()) {
            totalFrenquncy+=lista.getValue();
        }//for
        return totalFrenquncy;
    }//toString

    public void saveBDL(BDL bdl, String BDLpath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(bdl);
        saveDocDiscover(jsonInString,".\\" + BDLpath + "CompleteBDL.json");
        saveDocDiscover(bdl.sostToCSV(),".\\" + BDLpath + "BDLsost.csv");
        saveDocDiscover(bdl.verbToCSV(),".\\" + BDLpath + "BDLverbs.csv");
        saveDocDiscover(bdl.predToCSV(),".\\" + BDLpath + "BDLpred.csv");
    }

    public BDL loadBDLFromJsonFile(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString=getContentFromPath(path);
        return mapper.readValue(jsonInString, BDL.class);
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

    public String getContentFromPath(String path) throws IOException {
        String jarName="/"+path.substring(path.lastIndexOf("\\")+1);
        InputStream input=null;
        BufferedReader br;
        if(RepoBDL.class.getResourceAsStream(jarName)!=null)
            input = Main.class.getResourceAsStream(jarName);
        if(input==null){
            File file=new File(path);
            br=new BufferedReader(new FileReader(file));
        }else{
            br = new BufferedReader(new InputStreamReader(input));
        }//if_else
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
            sb.append(line);
            sb.append("\n");
            line = br.readLine();
        }//while
        return sb.toString();
    }//getContentFromPath

    @Override
    public String importPathOfBDL(){
        JFrame dialog = new JFrame();
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Json file", "json");
        chooser.setFileFilter(filter);
        dialog.getContentPane().add(chooser);
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(false);
        dialog.dispose();
        int returnVal = chooser.showOpenDialog(dialog);
        if (returnVal == JFileChooser.APPROVE_OPTION && Files.getFileExtension(chooser.getSelectedFile().getAbsolutePath()).equals("json")){
            return chooser.getSelectedFile().getAbsolutePath();
        }else{
            return "";
        }//if_else
    }//returnPath


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
    public void loadBackup(String directory) throws IOException {

    }
}
