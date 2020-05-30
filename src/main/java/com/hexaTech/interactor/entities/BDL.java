/**
 * @file BDL
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Alessio Barbiero
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.interactor.entities;

import java.util.*;

/**
 * Class used to represent a BDL object.
 */
public class BDL {
    private Map<String, Integer> nouns= new HashMap<>();
    private Map<String, Integer> verbs = new HashMap<>();
    private Map<String, Integer> predicates = new HashMap<>();

    /**
     * BDL class empty constructor.
     */
    public BDL(){}

    /**
     * BDL class standard constructor.
     * @param nouns Map<String, Integer> - map with BDL nouns and their frequency.
     * @param verbs Map<String, Integer> - map with BDL verbs and their frequency.
     * @param predicates Map<String, Integer> - map with BDL predicates and their frequency.
     */
    public BDL(Map<String, Integer> nouns, Map<String, Integer> verbs, Map<String, Integer> predicates) {
        this.nouns=sortMap((HashMap<String, Integer>) nouns);
        this.verbs =sortMap((HashMap<String, Integer>) verbs);
        this.predicates =sortMap((HashMap<String, Integer>) predicates);
    }

    /**
     * Shows BDL's nouns with their frequency.
     * @return Map<String, Integer> - map with BDL nouns and frequency.
     */
    public Map<String, Integer> getNouns() {
        return nouns;
    }

    /**
     * Shows BDL's verbs with their frequency.
     * @return Map<String, Integer> - map with BDL verbs and frequency.
     */
    public Map<String, Integer> getVerbs() {
        return verbs;
    }

    /**
     * Shows BDL's predicates with their frequency.
     * @return Map<String, Integer> - map with BDL predicates and frequency.
     */
    public Map<String, Integer> getPredicates() {
        return predicates;
    }

    /**
     * Adds a noun and it's frequency to BDL's list.
     * @param sosts string - noun to be added.
     * @param frequency integer - noun's frequency to be added.
     */
    public void addNoun(String sosts, int frequency) {
        boolean found = false;
        for (Map.Entry<String, Integer> nameList : nouns.entrySet()) {
            if (nameList.getKey().equalsIgnoreCase(sosts)) {
                nameList.setValue(nameList.getValue() + frequency);
                found = true;
                break;
            }//if
        }//for
        if (!found)
            this.nouns.put(sosts, frequency);
        nouns=sortMap((HashMap<String, Integer>) this.nouns);
    }//addNoun

    /**
     * Adds a verb and it's frequency to BDL's list.
     * @param verbs string - verb to be added.
     * @param frequency integer - verb's frequency to be added.
     */
    public void addVerb(String verbs, int frequency) {
        boolean found = false;
        for (Map.Entry<String, Integer> verb : this.verbs.entrySet()) {
            if (verb.getKey().equalsIgnoreCase(verbs)) {
                verb.setValue(verb.getValue() + frequency);
                found = true;
                break;
            }//if
        }//for
        if (!found)
            this.verbs.put(verbs, frequency);
        this.verbs =sortMap((HashMap<String, Integer>) this.verbs);
    }//addVerb

    /**
     * Adds a predicate and it's frequency to BDL's list.
     * @param preds string - predicate to be added.
     * @param frequency integer - predicate's frequency to be added.
     */
    public void addPredicate(String preds, int frequency) {
        boolean found = false;
        for (Map.Entry<String, Integer> pred : predicates.entrySet()) {
            if (pred.getKey().equalsIgnoreCase(preds)) {
                pred.setValue(pred.getValue() + frequency);
                found = true;
                break;
            }//if
        }//for
        if (!found)
            this.predicates.put(preds, frequency);
        predicates =sortMap((HashMap<String, Integer>) this.predicates);
    }//addPredicate

    /**
     * Returns a string representative of BDL's content.
     * @return string - BDL's string content.
     */
    public String toString() {
        StringBuilder toReturn =new StringBuilder("Nouns: frequency\n\n");
        for (Map.Entry<String, Integer> sost : nouns.entrySet()) {
            toReturn.append(sost.getKey().substring(0, 1).toUpperCase()).append(sost.getKey().substring(1)).append(": ").append(sost.getValue()).append("\n");
        }//for
        toReturn.append("\nVerbs: frequency\n\n");
        for (Map.Entry<String, Integer> verb : verbs.entrySet()) {
            toReturn.append(verb.getKey().substring(0, 1).toUpperCase()).append(verb.getKey().substring(1)).append(": ").append(verb.getValue()).append("\n");
        }//for
        toReturn.append("\nPredicates: frequency\n\n");
        for (Map.Entry<String, Integer> pred : predicates.entrySet()) {
            toReturn.append(pred.getKey().substring(0, 1).toUpperCase()).append(pred.getKey().substring(1)).append(": ").append(pred.getValue()).append("\n");
        }//for
        return toReturn.toString();
    }//toString

    // Ordina in modo crescente il BDL aggiunto su design
    public static <String, Integer extends Comparable<Integer>> Map<String, Integer> order (final Map<String, Integer> map) {
        Comparator<String> valueComparator = (k1, k2) -> {
            int compare = map.get(k2).compareTo(map.get(k1));
            if (compare == 0) return 1;
            else return compare;
        };
        Map<String, Integer> sortedByValues = new TreeMap<String, Integer>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }
    // Funzione per la stampa dei campi dati sul campo tag

    public String BDLtotag(Map<String,Integer> text)
    {
        StringBuilder toReturn =new StringBuilder();
        int count=0;
        int valore=0;
        Map <String, Integer> stringtag=order(text);
        for (Map.Entry<String, Integer> s : stringtag.entrySet()) {
            if(count==0)
            {    toReturn.append(s.getKey());
            valore=s.getValue();
            count=count+1; }

            if (count<5 && s.getValue()!=valore){
                toReturn.append(",").append(s.getKey());
                valore=s.getValue();
                count=count+1;
            }

        }//for
        return toReturn.toString();

    }

    /**
     * Sorts BDL's content alphabetically.
     * @param hm HashMap<String, Integer> - map to be sorted.
     * @return HashMap<String, Integer> - map's sorted clone.
     */
    public HashMap<String,Integer> sortMap(HashMap<String, Integer> hm) {
        List<Map.Entry<String, Integer>> list =
                new LinkedList<>(hm.entrySet());
        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }//compare
        });
        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }//for
        return temp;
    }//sortMap

    /**
     * Merges BDL parameter with object.
     * @param bdl BDL - BDL to be merged.
     */
    public void mergeBDL(BDL bdl) {
        for (Map.Entry<String, Integer> sost : bdl.getNouns().entrySet()) {
            this.addNoun(sost.getKey(),sost.getValue());
        }//for
        for (Map.Entry<String, Integer> verb : bdl.getVerbs().entrySet()) {
            this.addVerb(verb.getKey(),verb.getValue());
        }//for
        for (Map.Entry<String, Integer> pred : bdl.getPredicates().entrySet()) {
            this.addPredicate(pred.getKey(),pred.getValue());
        }//for
    }//mergeBDL

    /**
     * Returns a string representative of BDL's nouns in CVS format.
     * @return string - BDL nouns.
     */
    public String sostToCSV(){
        StringBuilder toReturn = new StringBuilder();
        for (Map.Entry<String, Integer> sost : nouns.entrySet()) {
            toReturn.append(sost.getKey().substring(0, 1).toUpperCase()).append(sost.getKey().substring(1)).append(",").append(sost.getValue()).append("\n");
        }//for
        return toReturn.toString();
    }//sostToCVS

    /**
     * Returns a string representative of BDL's verbs in CVS format.
     * @return string - BDL verbs.
     */
    public String verbToCSV(){
        StringBuilder toReturn = new StringBuilder();
        for (Map.Entry<String, Integer> verb : verbs.entrySet()) {
            toReturn.append(verb.getKey().substring(0, 1).toUpperCase()).append(verb.getKey().substring(1)).append(",").append(verb.getValue()).append("\n");
        }//for
        return toReturn.toString();
    }//verbToCVS

    /**
     * Returns a string representative of BDL's predicates in CVS format.
     * @return string - BDL predicates.
     */
    public String predToCSV(){
        StringBuilder toReturn = new StringBuilder();
        for (Map.Entry<String, Integer> pred : predicates.entrySet()) {
            toReturn.append(pred.getKey().substring(0, 1).toUpperCase()).append(pred.getKey().substring(1)).append(",").append(pred.getValue()).append("\n");
        }//for
        return toReturn.toString();
    }//predToCVS

    public String BDLtoCSV(){
        StringBuilder toReturn=new StringBuilder();

        toReturn.append("%\n"+ "SOSTS\n"+ sostToCSV() + "\n");
        toReturn.append("%\n"+ "VERBS\n" + verbToCSV() + "\n");
        toReturn.append("%\n"+ "PREDS\n" + predToCSV());

        return toReturn.toString();
    }

    /**
     * Fills BDL's nouns list with found elements.
     * @param list List<DoubleStruct> -  extracted elements' list.
     */
    public void addSostFromDoubleStruct(List<DoubleStruct> list){
        for(DoubleStruct doubleStruct : list){
            if(doubleStruct.getToken().contains("NN"))
               addNoun(doubleStruct.getLemma(),1);
        }//for
    }//addSost

    /**
     * Fills BDL's verbs list with found elements.
     * @param list List<DoubleStruct> -  extracted elements' list.
     */
    public void addVerbFromDoubleStruct(List<DoubleStruct> list){
        for(DoubleStruct doubleStruct : list){
            if(doubleStruct.getToken().contains("VB"))
                addVerb(doubleStruct.getLemma(),1);
        }//for
    }//addVerb

    /**
     * Fills BDL's predicates list with found elements.
     * @param list List<DoubleStruct> -  extracted elements' list.
     */
    public void addPredFromDoubleStruct(List<DoubleStruct> list){
        for(DoubleStruct doubleStruct : list){
            if(doubleStruct.getToken().contains("obj"))
                addPredicate(doubleStruct.getLemma(),1);
        }//for
    }//addPred

}//BDL
