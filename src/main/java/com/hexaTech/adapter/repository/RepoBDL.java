package com.hexaTech.adapter.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexaTech.Main;
import com.hexaTech.domain.port.out.repository.RepoBDLInterface;
import com.hexaTech.domain.entity.BDL;
import com.hexaTech.domain.entity.DoubleStruct;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

@Component
public class RepoBDL implements RepoBDLInterface {
    private BDL BDL;

    public RepoBDL(){
        BDL=new BDL();
    }

    public BDL createBDL(List<DoubleStruct> tagsForBDLConstruction) throws IOException {
        BDL toRet=new BDL();
        toRet.addSostFromDoubleStruct(tagsForBDLConstruction);
        toRet.addVerbFromDoubleStruct(tagsForBDLConstruction);
        toRet.addPredFromDoubleStruct(tagsForBDLConstruction);
        return toRet;
    }

    public int getTotalFrequency(Map<String,Integer> list) {
        int totalFrenquncy=0;
        for (Map.Entry<String, Integer> lista : list.entrySet()) {
            totalFrenquncy+=lista.getValue();
        }//for
        return totalFrenquncy;
    }//toString

    @Override
    public void setBDL(com.hexaTech.domain.entity.BDL bdl) {
        this.BDL =bdl;
    }

    public BDL getBDL() {
        return BDL;
    }

    public void saveBDL(String BDLpath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(BDL);
        saveDocDiscover(jsonInString,".\\" + BDLpath + " BDL.BDL");
        saveDocDiscover(BDL.sostToCSV(),".\\" + BDLpath + " BDL_nouns.csv");
        saveDocDiscover(BDL.verbToCSV(),".\\" + BDLpath + " BDL_verbs.csv");
        saveDocDiscover(BDL.predToCSV(),".\\" + BDLpath + " BDL_preds.csv");
    }

    public BDL loadBDLFromJsonFile(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString=getContentFromPath(path);
        return mapper.readValue(jsonInString, BDL.class);
    }

    public void saveDocDiscover(String doc, String path) throws IOException {
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
    public String importPathOfBDL(String document){
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
        if(document.equals("") || !existsDoc(document))
            return "";
        if(!document.contains(".") || !document.substring(document.lastIndexOf(".")).equalsIgnoreCase(".BDL"))
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

    public boolean isRepoBDLEmpty(){
        return BDL.getNouns().isEmpty() && BDL.getVerbs().isEmpty() && BDL.getPredicates().isEmpty();
    }

    public void openFile(String path) throws IOException{
        Desktop.getDesktop().open(new File(path));
    }
}
