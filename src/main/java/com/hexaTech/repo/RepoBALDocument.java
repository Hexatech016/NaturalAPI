/**
 * @file RepoBALDocument
 * @version 1.0.0
 * @type java
 * @data 2020-05-14
 * @author Alessio Barbiero
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.repo;

import com.google.common.io.Files;
import com.hexaTech.Main;
import com.hexaTech.entities.*;
import com.hexaTech.repointerface.RepoBALDocumentInterface;
import edu.stanford.nlp.parser.nndep.DependencyParser;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.TypedDependency;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.Scanner;

/**
 * RepoBALDocument class.
 */
public class RepoBALDocument implements RepoBALDocumentInterface {
    Document BAL;

    /**
     * RepoBALDocument empty constructor.
     */
    public RepoBALDocument() {
        super();
    }
    /**
     * Splits the given text into an array of strings.
     * @param text string - text.
     * @return string[] - array of strings.
     */
    private String[] getTextSplit(String text){
        String[] gherkinSplit;
        String delimiter = "[\n]+[\n]";
        gherkinSplit = text.split(delimiter);
        String part ="";
        return gherkinSplit;
    }

    /**
     * Fills a BAL object with the given text parsed elements.
     * @param text string - text to be parsed.
     * @return BAL - filled BAL object.
     */
    public BAL setBALFromGherkin(String text,String text2){
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma");
        StanfordCoreNLP pipeline=new StanfordCoreNLP(props);
        DependencyParser depparser = DependencyParser.loadFromModelFile("edu/stanford/nlp/models/parser/nndep/english_UD.gz");
        BAL baLjSon = new BAL();
        ArrayList<MethodBAL> methods = new ArrayList<MethodBAL>();
        String[] gherkinSplit = getTextSplit(text);
        for (String temp: gherkinSplit) {
            MethodBAL meth = new MethodBAL();
            Gherkin gherkin = extractGherkin(temp,pipeline,depparser);
            meth.setName(gherkin.getScenario());
            meth.setDescription(gherkin.getDescription());
            meth.setTags(text2);
            ToReturn toRet=new ToReturn();
            toRet.setDescription(gherkin.getThen());
            meth.setToRet(toRet);
            ArrayList<Parameter> params = new ArrayList<Parameter>();
            for(String parameter : gherkin.getWhen()){
                Parameter param = new Parameter();
                param.setDescription("Default");
                param.setName(parameter);
                param.setType("string");
                params.add(param);
            }//for
            meth.setParameters(params);
            methods.add(meth);
        }//for
        baLjSon.setMethods(methods);
        return baLjSon;
    }//setBALFromGherkin

    private Gherkin extractGherkin(String text,StanfordCoreNLP pipeline,DependencyParser depparser) {
        String delimiter = "[\n]+";
        String[] arr= text.split(delimiter);
        Gherkin toRit = new Gherkin();
        String sentinel="";
        for (String str : arr) {
            CoreDocument documents = new CoreDocument(str);
            pipeline.annotate(documents);
            StringBuilder builder = new StringBuilder();
            String firstToken = documents.sentences().get(0).tokensAsStrings().get(0);
            if (firstToken.equalsIgnoreCase("AND")){
                firstToken=sentinel;
            }//if
            Annotation document = new Annotation(str);
            pipeline.annotate(document);
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



    /**
     * Returns BAL object.
     * @return BAL - BAL object.
     */
    public Document getBAL() {
        return BAL;
    }

    /**
     * Verifies if the specified document exists.
     * @param path string - path to the document to be searched.
     * @return boolean - true if the document exists, false if not.
     */
    public boolean existsDoc(String path){
        File file=new File(path);
        return file.exists();
    }//existsDoc

    /**
     * Deletes the specified document.
     * @param path string - path to the document to be deleted.
     * @return boolean - false if an error occurs, true if not.
     */
    public boolean deleteDoc(String path){
        File temp=new File(path);
        return temp.delete();
    }

    /**
     * Extrapolates content from a document.
     * @param path string - document's path.
     * @return string - document's content.
     * @throws IOException if the specified document doesn't exist.
     */
    public String getContentFromPath(String path) throws IOException {
        String jarName="/"+path.substring(path.lastIndexOf("\\")+1);
        InputStream input=null;
        BufferedReader br;
        if(RepoBALDocument.class.getResourceAsStream(jarName)!=null)
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

    /**
     * Loads a new document from file system.
     * @param directory string - directory used to save the file.
     * @return boolean - false if something goes wrong, true if not.
     */
    @Override
    public boolean importDoc(String directory){
        String temp;
        JFrame dialog = new JFrame();
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("File JSON", "json");
        chooser.setFileFilter(filter);
        dialog.getContentPane().add(chooser);
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(false);
        dialog.dispose();
        int returnVal = chooser.showOpenDialog(dialog);
        if (returnVal == JFileChooser.APPROVE_OPTION && Files.getFileExtension(chooser.getSelectedFile().getAbsolutePath()).equals("json") ){
            temp=chooser.getSelectedFile().getAbsolutePath();
        }else{
            temp="";
        }//if_else
        if(!temp.equalsIgnoreCase("")) {
            BAL=new Document(temp.substring(temp.lastIndexOf("\\")+1),temp);
            saveDoc(".\\BackupBAL.txt", directory);
            return true;
        }else
            return false;
    }//importDoc

    /**
     * Loads content from a backup file and restore it.
     * @param directory string - directory used to search the file in.
     * @throws FileNotFoundException if the backup file doesn't exist.
     */
    @Override
    public void loadBackup(String directory) throws FileNotFoundException {
        Scanner s = new Scanner(new File(".\\" + directory + "\\BackupBAL.txt"));
        String temp=s.nextLine();
        BAL=new Document((temp.substring(temp.lastIndexOf("\\")+1)), temp);
        s.close();
    }

    /**
     * Saves a backup file.
     * @param title string - document title.
     * @param directory string - document directory.
     */
    @Override
    public void saveDoc(String title, String directory) {
        try {
            // Open given file in append mode.
            File file= new File(directory);
            if (!file.exists())
                file.mkdir();
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(directory + "/" + title));
            out.write(BAL.getPath());
            out.close();
        }catch (IOException e) {
            System.out.println("exception occurred " + e);
        }//try_catch
    }//saveDoc

    /**
     * Saves the BAL object into a new file.
     * @param bal BAL - BAL object.
     * @throws IOException if an error occurs during saving process.
     */
    @Override
    public void saveBAL(BAL bal) throws IOException {
        saveDocDesign(bal.toString(),".\\BAL.json");
    }

    /**
     * Saves the specified content into Design folder.
     * @param doc string - new document's content.
     * @param path string - new document's path.
     * @throws IOException if occurs an error while creating the file or writing into it.
     */
    private void saveDocDesign(String doc, String path) throws IOException {
        File directory = new File("Design");
        if (! directory.exists())
            directory.mkdir();
        // Open given file in append mode.
        BufferedWriter out = new BufferedWriter(
                new FileWriter(directory + "/" + path));
        String[] rows=doc.split("\n");
        for(String riga: rows){
            out.write(riga);
            out.newLine();
        }//for
        out.close();
    }//saveDocDesign

}//RepoBALDocument
