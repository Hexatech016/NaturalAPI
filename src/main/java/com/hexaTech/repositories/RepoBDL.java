package com.hexaTech.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexaTech.Main;
import com.hexaTech.interactor.entities.BDL;
import com.hexaTech.interactor.entities.DoubleStruct;
import com.hexaTech.interactor.repositoriesInterface.RepoBDLInterface;

import java.io.*;
import java.util.*;

public class RepoBDL implements RepoBDLInterface {

    BDL bdl= new BDL();

    public BDL createBDL(List<DoubleStruct> tagsForBDLConstruction) throws IOException {
        BDL bdlToReturn =new BDL();
        bdlToReturn.addSostFromDoubleStruct(tagsForBDLConstruction);
        bdlToReturn.addVerbFromDoubleStruct(tagsForBDLConstruction);
        bdlToReturn.addPredFromDoubleStruct(tagsForBDLConstruction);
        return bdlToReturn;
    }

    public int getTotalFrequency(Map<String,Integer> list) {
        int totalFrenquncy=0;
        for (Map.Entry<String, Integer> lista : list.entrySet()) {
            totalFrenquncy+=lista.getValue();
        }//for
        return totalFrenquncy;
    }//toString

    @Override
    public void setBDL(BDL bdl) {
        this.bdl=bdl;
    }
    public BDL getBDL() {
        return bdl;
    }
    public void saveBDL(BDL bdl, String BDLpath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(bdl);
        saveDocDiscover(jsonInString,".\\" + BDLpath + "Complete.bdl");
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
        /*JFrame dialog = new JFrame();
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
        }//if_else*/
        Scanner scanner=new Scanner(System.in);
        String document=scanner.nextLine();
        if(document.equals("") || !existsDoc(document))
            return "";
        if(!document.contains(".") || !document.substring(document.lastIndexOf(".")).equalsIgnoreCase(".json"))
            return "";
        return document;
    }//returnPath


    @Override
    public void saveDoc(String title, String directory) {

    }

    @Override
    public boolean existsDoc(String path){
        File file=new File(path);
        return file.exists();
    }//existsDoc

    @Override
    public boolean deleteDoc(String path){
        File temp=new File(path);
        return temp.delete();
    }


    @Override
    public void loadBackup(String directory) throws IOException {

    }
}