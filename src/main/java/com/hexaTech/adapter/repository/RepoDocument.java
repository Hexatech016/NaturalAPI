/**
 * @file RepoDocument
 * @version 1.0.0
 * @type java
 * @data 2020-05-13
 * @author Eduard Serban
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.adapter.repository;

import com.hexaTech.Main;
import com.hexaTech.domain.entity.Document;
import com.hexaTech.domain.port.out.repository.RepoDocumentInterface;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * RepoDocument class.
 */
@Component
public class RepoDocument implements RepoDocumentInterface {
    private List<Document> documents;

    /**
     * RepoDocument empty constructor.
     */
    public RepoDocument() {
        this.documents=new ArrayList<>();
    }

    /**
     * Returns documents' list.
     * @return List<Document> - list of documents stored.
     */
    public List<Document> getDocuments() {
        return documents;
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
        if(RepoDocument.class.getResourceAsStream(jarName)!=null)
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
        if (returnVal == JFileChooser.APPROVE_OPTION && Files.getFileExtension(chooser.getSelectedFile().getAbsolutePath()).equals("txt")){
            temp=chooser.getSelectedFile().getAbsolutePath();
        }else{
            temp= "";
        }//if_else
        if(!temp.equalsIgnoreCase("")) {
            documents.add(new Document(temp.substring(temp.lastIndexOf("" + File.separator + "")+1),temp));
            saveDoc("." + File.separator + "BackupDocument.txt", directory);
            return true;
        }else
            return false;*/
        if(document.equals("") || !existsDoc(document))
            return false;
        if(!document.contains(".") || !document.substring(document.lastIndexOf(".")).equalsIgnoreCase(".txt"))
            return false;
        if(!alreadyLoaded(document)){
            documents.add(new Document(document.substring(document.lastIndexOf(File.separator)+1), document));
            saveDoc("BackupDocument.txt", directory);
            return true;
        }else
            return false;
    }//returnPath

    /**
     * Saves a backup file.
     * @param title string - document title.
     * @param directory string - document directory.
     */
    @Override
    public void saveDoc(String title, String directory) {
        StringBuilder temp= new StringBuilder();
        for(Document document: documents)
            temp.append(document.getPath()).append("\n");
        try {
            // Open given file in append mode.
            File file= new File(directory);
            if (!file.exists())
                file.mkdir();
            BufferedWriter out = new BufferedWriter(
                    new FileWriter("."+File.separator+directory + File.separator + title));
            String[] rows=temp.toString().split("\n");
            for(String row: rows){
                out.write(row);
                out.newLine();
            }//for
            out.close();
        }catch (IOException e) {
            System.out.println("exception occurred " + e);
        }//try_catch
    }//saveDoc

    /**
     * Loads content from a backup file and restore it.
     * @param directory string - directory used to search the file in.
     * @throws IOException if the backup file doesn't exist.
     */

    @Override
    public void loadBackup(String directory) throws IOException {
        Scanner s = new Scanner(new File("." + File.separator + "" + directory + "" + File.separator + "BackupDocument.txt"));
        makeEmpty();
        String temp=s.nextLine();
        while (temp!=null){
            documents.add(new Document((temp.substring(temp.lastIndexOf("" + File.separator + "")+1)), temp));
            if(!s.hasNextLine())
                temp=null;
            else
                temp=s.nextLine();
        }//while
        s.close();
    }//loadBackUp

    public String getBackup(String path) throws FileNotFoundException {
        StringBuilder toReturn= new StringBuilder();
        int i=1;
        Scanner s = new Scanner(new File("." + File.separator + "" + path + "" + File.separator + "BackupDocument.txt"));
        String temp=s.nextLine();
        while (temp!=null){
            toReturn.append(i).append(". ").append(temp).append("\n");
            i++;
            if(!s.hasNextLine())
                temp=null;
            else
                temp=s.nextLine();
        }//while
        s.close();
        return toReturn.toString();
    }//showBackup

    @Override
    public boolean removeDoc(int position) {
        if(position>=0 && position < documents.size()) {
            documents.remove(position);
            saveDoc("BackupDocument.txt", "Discover");
        }else
            return false;
        return true;
    }//removeDoc

    private boolean alreadyLoaded(String newDoc) {
        for (Document document : documents)
            if (document.getPath().equalsIgnoreCase(newDoc))
                return true;
        return false;
    }//alreadyLoaded

    @Override
    public String toString(){
        StringBuilder toReturn=new StringBuilder();
        int i=1;
        for(Document document:documents) {
            toReturn.append(i).append(". ").append(document.getPath()).append("\n");
            i++;
        }
        return toReturn.toString();
    }

    public void makeEmpty(){
        documents.clear();
    }

    public boolean isEmpty(){
        return documents.isEmpty();
    }

}//RepoDocument
