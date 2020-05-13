package com.hexaTech.repo;

import com.hexaTech.Main;
import com.hexaTech.fileSystem.FileSystem;

import java.io.*;

/**
 * Class used to manage a generic file repository.
 */
abstract class RepoFile implements RepoInterface{

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

}//RepoFile
