/**
 * @file DoubleStruct
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Luca Marcon
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.HexaTech.entities;

/**
 * Class used to represent an object with two string parameters.
 */
public class DoubleStruct {
    String token;
    String lemma;

    /**
     * DoubleStruct class standard constructor.
     * @param token string - token's value.
     * @param lemma string - lemma's value.
     */
    public DoubleStruct(String token, String lemma){
        this.token=token;
        this.lemma=lemma;
    }

    /**
     * Returns lemma's value.
     * @return string - lemma.
     */
    public String getLemma() {
        return lemma;
    }

    /**
     * Returns token's value.
     * @return string - token.
     */
    public String getToken() {
        return token;
    }

    /**
     * Returns a string representative of DoubleStruct's content.
     * @return string - DoubleStruct content.
     */
    public String toString(){
        return token+ " " + lemma;
    }

}//DoubleStruct
