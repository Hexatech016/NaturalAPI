/**
 * @file RepoBALDocument
 * @version 1.0.0
 * @type java
 * @data 2020-05-14
 * @author Alessio Barbiero
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.adapter.repository;

import com.hexaTech.Main;
import com.hexaTech.domain.entity.*;
import com.hexaTech.domain.port.out.repository.RepoBALDocumentInterface;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * RepoBALDocument class.
 */
@Component
public class RepoBALDocument implements RepoBALDocumentInterface {
    private Document BAL;

    /**
     * RepoBALDocument empty constructor.
     */
    public RepoBALDocument() {
        BAL=new Document();
    }

    /**
     * Fills a BAL object with the given text parsed elements.
     * @return BAL - filled BAL object.
     */
    public BAL setBALFromGherkin(List<Gherkin> gherkins, String nameBal){
        BAL BAL = new BAL(nameBal);
        ArrayList<MethodBAL> methods = new ArrayList<MethodBAL>();
        for (Gherkin gherkin: gherkins) {
            MethodBAL meth = new MethodBAL();
            meth.setName(gherkin.getScenario());
            meth.setDescription(gherkin.getDescription());
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
        BAL.setMethods(methods);
        return BAL;
    }//setBALFromGherkin

    /**
     * Returns BAL object.
     * @return BAL - BAL object.
     */
    public Document getBAL() {
        return BAL;
    }

    public void setBAL(Document newBAL){
        this.BAL=newBAL;
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
        String jarName="/"+path.substring(path.lastIndexOf("" + File.separator + "")+1);
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
    public boolean importDoc(String directory,String document){
        //GUI INSERTION
        /*String temp;
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
            BAL=new Document(temp.substring(temp.lastIndexOf("" + File.separator + "")+1),temp);
            saveDoc("." + File.separator + "BackupBAL.txt", directory);
            return true;
        }else
            return false;*/
        if(document.equals("") || !existsDoc(document))
            return false;
        if(!document.contains(".") || !document.substring(document.lastIndexOf(".")).equalsIgnoreCase(".json"))
            return false;
        BAL=new Document(document.substring(document.lastIndexOf("" + File.separator + "")+1),document);
        saveDoc("BackupBAL.txt", directory);
        return true;
    }//importDoc

    /**
     * Loads content from a backup file and restore it.
     * @param directory string - directory used to search the file in.
     * @throws FileNotFoundException if the backup file doesn't exist.
     */
    @Override
    public void loadBackup(String directory) throws FileNotFoundException {
        Scanner s = new Scanner(new File("." + File.separator + "" + directory + "" + File.separator + "BackupBAL.txt"));
        String temp=s.nextLine();
        BAL=new Document((temp.substring(temp.lastIndexOf("" + File.separator + "")+1)), temp);
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
                    new FileWriter("."+File.separator+directory + File.separator + title));
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
    public void saveBAL(BAL bal, String name) throws IOException {
        saveDocDesign(bal.toString(),"." + File.separator + name +"BAL.json");
    }

    public void openFile(String path) throws IOException{
        Desktop.getDesktop().open(new File(path));
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

    public boolean isEmpty(){
        try{
            return BAL.getPath().equals("") || BAL.getPath() == null;
        }catch(NullPointerException e){
            return true;
        }//try_catch
    }//isEmpty

}//RepoBALDocument
