package com.hexaTech.repo;

import com.google.common.io.Files;
import com.hexaTech.Main;
import com.hexaTech.entities.Document;
import com.hexaTech.fileSystem.FileSystem;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RepoDocument implements RepoInterface{
    private List<Document> documents;

    public RepoDocument() {
        this.documents=new ArrayList<>();
    }

    public List<Document> getDocuments() {
        return documents;
    }

    @Override
    public boolean importDoc(String directory) {
        String temp;
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
            documents.add(new Document(temp.substring(temp.lastIndexOf("\\")+1),temp));
            saveDoc(".\\BackupDocument.txt", directory);
            return true;
        }else
            return false;
    }//returnPath

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
                    new FileWriter(directory + "/" + title));
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

    @Override
    public boolean existsDoc(String path){
        File file=new File(path);
        return file.exists();
    }//existsDoc

    public boolean deleteDoc(String path){
        File temp=new File(path);
        return temp.delete();
    }

    public String getContentFromPath(String path) throws IOException{
        String jarName="/"+path.substring(path.lastIndexOf("\\")+1);
        InputStream input=null;
        BufferedReader br;
        if(FileSystem.class.getResourceAsStream(jarName)!=null)
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
    public void loadBackup(String directory) throws FileNotFoundException {
        Scanner s = new Scanner(new File(".\\" + directory + "\\BackupDocument.txt"));
        while (s.hasNextLine()){
            documents.add(new Document((s.nextLine().substring(s.nextLine().lastIndexOf("\\")+1)), s.nextLine()));
        }//while
        s.close();
        System.out.println(directory);
    }//loadBackUp

}//RepoDocument
