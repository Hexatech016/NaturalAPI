package com.hexaTech.adapter.repository;

import com.hexaTech.Main;
import com.hexaTech.domain.entity.Document;
import com.hexaTech.domain.port.out.repository.RepoGherkinInterface;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

@Component
public class RepoGherkin implements RepoGherkinInterface {
    private Document gherkin;

    public void setGherkin(Document gherkin){
        this.gherkin=gherkin;
    }

    public RepoGherkin() {
        this.gherkin = new Document();
    }

    public Document getGherkin() {
        return gherkin;
    }

    @Override
    public boolean importDoc(String directory,String document){
        if(document.equals("") || !existsDoc(document))
            return false;
        if(!document.contains(".") || !document.substring(document.lastIndexOf(".")).equalsIgnoreCase(".scenario"))
            return false;
        gherkin=new Document(document.substring(document.lastIndexOf("" + File.separator + "")+1),document);
        saveDoc("BackupGherkin.txt", directory);
        return true;
    }//returnPath

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
        if(RepoGherkin.class.getResourceAsStream(jarName)!=null)
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
    public void saveDoc(String title, String directory) {
        String path=gherkin.getPath();
        try {
            // Open given file in append mode.
            File file= new File(directory);
            if (!file.exists())
                file.mkdir();
            BufferedWriter out = new BufferedWriter(
                    new FileWriter("."+File.separator+directory + File.separator + title));
            out.write(path);
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

    public boolean deleteDoc(String path){
        File temp=new File(path);
        return temp.delete();
    }

    @Override
    public void loadBackup(String directory) throws IOException {
        Scanner s = new Scanner(new File("." + File.separator + "" + directory + "" + File.separator + "BackupGherkin.txt"));
        String path=s.nextLine();
        while (path!=null){
            gherkin=new Document((path.substring(path.lastIndexOf("" + File.separator + "")+1)), path);
            if(!s.hasNextLine())
                path=null;
            else
                path=s.nextLine();
        }//while
        s.close();
    }//loadBackUp
}
