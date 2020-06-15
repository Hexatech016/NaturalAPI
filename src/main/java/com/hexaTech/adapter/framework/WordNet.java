package com.hexaTech.adapter.framework;

import com.hexaTech.domain.port.out.framework.WordParsingInterface;
import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.*;
import net.didion.jwnl.dictionary.Dictionary;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Component
public class WordNet implements WordParsingInterface {

    public boolean thisNounIsASynonymOf(String word, String target) throws FileNotFoundException, JWNLException {
        JWNL.initialize(new FileInputStream("."+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"properties.xml"));
        final Dictionary dictionary = Dictionary.getInstance();
        final IndexWord indexWord = dictionary.lookupIndexWord(POS.NOUN, target);
        if(indexWord==null)
            return false;
        final Synset[] senses = indexWord.getSenses();
        return check(senses,word);

    }

    public boolean thisVerbIsASynonymOf(String word, String target) throws FileNotFoundException, JWNLException {
        JWNL.initialize(new FileInputStream("."+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"properties.xml"));
        final Dictionary dictionary = Dictionary.getInstance();
        final IndexWord indexWord = dictionary.lookupIndexWord(POS.VERB, target);
        if(indexWord==null)
            return false;
        final Synset[] senses = indexWord.getSenses();
        return check(senses,word);
    }

    private boolean check(Synset[] senses, String word) {
        for (Synset synset : senses) {
            Word[] words = synset.getWords();
            for (Word w : words) {
                if (word.equalsIgnoreCase(w.getLemma())) {
                    return true;
                }
            }
        }
        return false;
    }
}
