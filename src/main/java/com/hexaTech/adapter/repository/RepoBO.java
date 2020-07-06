/**
 * @file RepoBAL
 * @version 1.0.0
 * @type java
 * @data 2020-05-19
 * @author Matteo Brosolo
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.adapter.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexaTech.Main;
import com.hexaTech.domain.entity.*;
import com.hexaTech.domain.port.out.repository.RepoBOInterface;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

/**
 * RepoBAL class.
 * implementata l'interfaccia
 */
@Component
public class RepoBO implements RepoBOInterface{
    private Document BO;
    private BO boOpenAPI;

    public RepoBO() {
        BO=new Document();
        boOpenAPI=new BO();
    }

    public void setBoOpenAPI(BO boOpenAPI) {
        this.boOpenAPI = boOpenAPI;
    }

    public BO getBoOpenAPI() {
        return boOpenAPI;
    }

    /**
     * RepoBO empty constructor.
     */


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
    public boolean importDoc(String directory,String document){
        /*String temp;
        JFrame dialog = new JFrame();
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text file", "txt");
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
            BO=new Document(temp.substring(temp.lastIndexOf("" + File.separator + "")+1),temp);
            saveDoc("." + File.separator + "BackupBO.txt", directory);
            return true;
        }else
            return false;*/
        if(document.equals("") || !existsDoc(document))
            return false;
        if(!document.contains(".") || !document.substring(document.lastIndexOf(".")).equalsIgnoreCase(".json"))
            return false;
        BO=new Document(document.substring(document.lastIndexOf("" + File.separator + "")+1),document);
        //saveDoc("BackupBO.txt", directory);
        return true;
    }//importDoc

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
                    new FileWriter("."+File.separator+directory + File.separator + title));
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
        String jarName="/"+path.substring(path.lastIndexOf("" + File.separator + "")+1);
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
        Scanner s = new Scanner(new File("." + File.separator + "" + directory + "" + File.separator + "BackupBO.txt"));
        String temp=s.nextLine();
        BO=new Document((temp.substring(temp.lastIndexOf("" + File.separator + "")+1)), temp);
        s.close();
    }

    public BO setBOFromJSON(String text) throws JsonProcessingException{
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode node = objectMapper.readTree(text);
            BO bo = new BO();
            bo.setOntologyName(node.get("ontologyName").asText());
            JsonNode objlist = node.get("ontologyObjects");
            List<JsonNode> objects = new ArrayList<JsonNode>();
            if (objlist.isArray()) {
                for (JsonNode tmp : objlist) {
                    objects.add(tmp);
                }//for
            }//if
            for (JsonNode tmp : objects) {
                StructureBAL bobj = new StructureBAL();
                List<Parameter> parameterList = new ArrayList<>();
                if (tmp.get("parameters").isArray()) {
                    for (JsonNode iter : tmp.get("parameters")) {
                        parameterList.add(new Parameter(iter.get("description").toString().replace("\"", ""),
                                iter.get("name").toString().replace("\"", ""), iter.get("type").toString().replace("\"", "")));
                    }//for
                }//if
                bobj.setName(tmp.get("name").asText());
                bobj.setParameters(parameterList);
                bo.setBOObjects(bobj);
            }//for
            return bo;
        }catch(JsonProcessingException | NullPointerException e){
            return null;
        }
    }//setBOFromJSON

    @Override
    public void setBO(BO bo){
        boOpenAPI=bo;
    }

    @Override
    public void saveBO(String BOpath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(boOpenAPI);
        saveDocDiscover(jsonInString,"." + File.separator + BOpath + "BO.json");
    }

    public void saveDocDiscover(String doc, String path){
        try {
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

}//RepoBO
