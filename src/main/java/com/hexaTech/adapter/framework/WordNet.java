package com.hexaTech.adapter.framework;

import com.hexaTech.domain.port.out.framework.WordParsingInterface;
import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.*;
import net.didion.jwnl.dictionary.Dictionary;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

@Component
public class WordNet implements WordParsingInterface {

    static String jarName="/properties.xml";

    public boolean thisNounIsASynonymOf(String word, String target) throws JWNLException {
        try{
            //replaceXML(jarName,"/dictio");
            JWNL.initialize(new FileInputStream(jarName));
        }catch(FileNotFoundException | NullPointerException e){
            replaceXML("."+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"properties.xml",
                    "./src/main/resources/dictio");
            JWNL.initialize(new FileInputStream("."+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"properties.xml"));
        }finally{
            final Dictionary dictionary = Dictionary.getInstance();
            final IndexWord indexWord = dictionary.lookupIndexWord(POS.NOUN, target);
            if (indexWord == null)
                return false;
            final Synset[] senses = indexWord.getSenses();
            return check(senses, word);
        }
    }//thisNounIsASynonymOf

    public boolean thisVerbIsASynonymOf(String word, String target) throws JWNLException {
        try{
            //replaceXML(jarName,"/dictio");
            JWNL.initialize(new FileInputStream(jarName));
        }catch(FileNotFoundException | NullPointerException e){
            replaceXML("."+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"properties.xml",
                    "./src/main/resources/dictio");
            JWNL.initialize(new FileInputStream("."+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"properties.xml"));
        }finally{
            final Dictionary dictionary = Dictionary.getInstance();
            final IndexWord indexWord = dictionary.lookupIndexWord(POS.VERB, target);
            if (indexWord == null)
                return false;
            final Synset[] senses = indexWord.getSenses();
            return check(senses, word);
        }
    }//thisVerbIsASynonymOf

    private boolean check(Synset[] senses, String word) {
        for (Synset synset : senses) {
            Word[] words = synset.getWords();
            for (Word w : words) {
                if (word.equalsIgnoreCase(w.getLemma()))
                    return true;
            }//for
        }//for
        return false;
    }//check

    private void replaceXML(String filepath,String toReplace) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(filepath);
        NodeList staff = doc.getElementsByTagName("param");
        for(int i=0;i<staff.getLength();i++){
            NamedNodeMap attr =staff.item(i).getAttributes();
            Node nodeAttr=attr.getNamedItem("name");
            if(nodeAttr.getTextContent().equalsIgnoreCase("dictionary_path")) {
                Node nodeVal=attr.getNamedItem("value");
                nodeVal.setTextContent(toReplace);
            }//if
        }//for
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filepath));
        transformer.transform(source, result);
    }//replaceXML

}//WordNet
