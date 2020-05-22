/**
 * @file RepoBAL
 * @version 1.0.0
 * @type java
 * @data 2020-05-19
 * @author Matteo Brosolo
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.repo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;
import com.hexaTech.Main;
import com.hexaTech.entities.*;
import com.hexaTech.repointerface.RepoBOInterface;
import edu.stanford.nlp.parser.nndep.DependencyParser;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.TypedDependency;

import javax.json.JsonArray;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.*;

/**
 * RepoBAL class.
 * implementata l'interfaccia
 */
public class RepoBO implements RepoBOInterface{
    Document BO;
    /**
     * RepoBO empty constructor.
     */
    public RepoBO() {
        super();
    }
    /**
     * Returns BO object.
     * @return BO - BO object.
     */
    public Document getBO() {
        return BO;
    }
    /**
     * Loads a new document from file system.
     * @param directory string - directory used to save the file.
     * @return boolean - false if something goes wrong, true if not.
     */
    public boolean importDoc(String directory){
        String temp;
        System.out.println("brr brr");
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
            BO=new Document(temp.substring(temp.lastIndexOf("\\")+1),temp);
            saveDoc(".\\BackupBO.txt", directory);
            return true;
        }else
            return false;
    }//importDoc

    @Override
    public void saveBO(com.hexaTech.entities.BO bo) throws IOException {

    }

    /**
     * Saves a backup file.
     * @param title string - document title.
     * @param directory string - document directory.
     */
    public void saveDoc(String title, String directory) {
        try {
            // Open given file in append mode.
            File file= new File(directory);
            if (!file.exists())
                file.mkdir();
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(directory + "/" + title));
            //out.write(BO.getPath());
            out.close();
        }catch (IOException e) {
            System.out.println("exception occurred " + e);
        }//try_catch
    }//saveDoc
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
        if(RepoBAL.class.getResourceAsStream(jarName)!=null)
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
     * Loads content from a backup file and restore it.
     * @param directory string - directory used to search the file in.
     * @throws FileNotFoundException if the backup file doesn't exist.
     */
    public void loadBackup(String directory) throws FileNotFoundException {
        Scanner s = new Scanner(new File(".\\" + directory + "\\BackupBAL.txt"));
        String temp=s.nextLine();
        BO=new Document((temp.substring(temp.lastIndexOf("\\")+1)), temp);
        s.close();
    }

    public BO setBOFromJSON(String text) throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        JsonNode node = objectMapper.readTree(text); /*json visto come json e non come text*/
        BO bo= new BO();

        node.get("nomeOntologia").asText();/*estrazione nome BO(string)*/
        List<JsonNode> objects=objectMapper.convertValue(node.get("Oggetti"), ArrayList.class);/*estrazione lista oggetti BO (JSON)*/

        for(JsonNode tmp: objects) {
            BOObject bobj = new BOObject();/*creazione BOObject*/

            List<String> params=objectMapper.convertValue(tmp.get("parametri"), ArrayList.class);/*estrazione parametri*/
            bobj.setBOParams(params);/*inizializazione BOObject-Param con i parametri*/
            List<String> types=objectMapper.convertValue(tmp.get("TipoValori"), ArrayList.class);/*estrazione tipi*/
            bobj.setBOValueTypes(types);/*inizializazione BOObject-Tipi con i tipi*/
            bobj.setNome(tmp.get("name").asText());/*estrazione ed inizializzazione BOObject-Nome con il nome*/

            bo.setBOObjects(bobj);

        }
        System.out.println(bo.toString());
        return bo;
    }

    @Override
    public void setBO(com.hexaTech.entities.BO bo) {

    }

    @Override
    public void saveBo(com.hexaTech.entities.BO bo) {

    }

}
