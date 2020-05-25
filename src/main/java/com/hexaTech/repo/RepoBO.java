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
        if (returnVal == JFileChooser.APPROVE_OPTION && Files.getFileExtension(chooser.getSelectedFile().getAbsolutePath()).equals("txt") ){
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
    public void saveBO(BO2 bo) throws IOException {
        saveDocDesign(bo.toOpenAPI(),".\\BO.json");

    }

    public void saveBO(com.hexaTech.entities.BO bo) throws IOException {
        saveDocDesign(bo.toOpenAPI(),".\\BO.json");
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
            out.write(BO.getPath());
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
        Scanner s = new Scanner(new File(".\\" + directory + "\\BackupBO.txt"));
        String temp=s.nextLine();
        BO=new Document((temp.substring(temp.lastIndexOf("\\")+1)), temp);
        s.close();
    }

    public BO setBOFromJSON(String text) throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        JsonNode node = objectMapper.readTree(text); /*json visto come json e non come text*/
        BO bo= new BO();
        bo.setOntologyName(node.get("nomeOntologia").asText());

        JsonNode objlist=node.get("Oggetti");
        List<JsonNode> objects=new ArrayList<JsonNode>();
        if(objlist.isArray()){
            for(JsonNode tmp: objlist){
                objects.add(tmp);
            }
        }

        for(JsonNode tmp: objects) {
            BOObject bobj = new BOObject();
            /*Parameter boparam= new Parameter();*/
            List<String> params= new ArrayList<String>();
            List<String> types=new ArrayList<String>();

            if(tmp.get("parametri").isArray()){
                for(JsonNode iter: tmp.get("parametri")){
                    params.add(iter.toString());
                }
            }
            if(tmp.get("TipoValori").isArray()){
                for(JsonNode iter: tmp.get("TipoValori")){
                    types.add(iter.toString());
                }
            }
            /*ciclo for che ciclando sulle due liste crea un oggetto parameter con la coppia nome-tipovalore*/
            bobj.setBOParams(params);/*inizializazione BOObject-Param con i parametri*/
            bobj.setBOValueTypes(types);/*inizializazione BOObject-Tipi con i tipi*/
            bobj.setNome(tmp.get("name").asText());/*estrazione ed inizializzazione BOObject-Nome con il nome*/

            bo.setBOObjects(bobj);
        }
        System.out.println(bo.toString());
        return bo;
    }

    public BO2 setBOFromJSON2(String text) throws JsonProcessingException{
        ObjectMapper objectMapper=new ObjectMapper();
        JsonNode node = objectMapper.readTree(text); /*json visto come json e non come text*/
        BO2 bo= new BO2();
        bo.setOntologyName(node.get("nomeOntologia").asText());

        JsonNode objlist=node.get("Oggetti");
        List<JsonNode> objects=new ArrayList<JsonNode>();
        if(objlist.isArray()){
            for(JsonNode tmp: objlist){
                objects.add(tmp);
            }
        }

        for(JsonNode tmp: objects) {
            StructureBAL bobj = new StructureBAL();
            List<String> params = new ArrayList<String>();
            List<String> types = new ArrayList<String>();

            if (tmp.get("parametri").isArray()) {
                for (JsonNode iter : tmp.get("parametri")) {
                    params.add(iter.toString());
                }
            }
            if (tmp.get("TipoValori").isArray()) {
                for (JsonNode iter : tmp.get("TipoValori")) {
                    types.add(iter.toString());
                }
            }
            bobj.setName(tmp.get("name").asText());
            /*ciclo for che ciclando sulle due liste crea un oggetto parameter con la coppia nome-tipovalore*/
            for(int i=0; i<params.size(); i++){
                Parameter par= new Parameter();
                par.setName(params.get(i));
                par.setType(types.get(i));
                bobj.setParameters(par);
            }
            bo.setBOObjects(bobj);
        }
    return bo;


    }

    @Override
    public void setBO(com.hexaTech.entities.BO bo) throws IOException {}
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

    @Override
    public void saveBo(com.hexaTech.entities.BO bo) {}

}
