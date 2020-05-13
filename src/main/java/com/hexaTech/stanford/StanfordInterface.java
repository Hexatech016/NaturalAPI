/**
 * @file StanfordInterface
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Matteo Brosolo
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.stanford;

import com.hexaTech.entities.DoubleStruct;
import com.hexaTech.entities.Gherkin;

import java.util.List;

/**
 * Stanford class interface.
 */
public interface StanfordInterface{

   /**
    * Fills a list with elements found while parsing the given text.
    * @param content string - document's content to analyze.
    * @return List<DoubleStruct> - list of found elements.
    */
   List<DoubleStruct> extract(String content);

   /**
    * Extracts Gherkin elements from given text.
    * @param text string - text to be analyzed.
    * @return Gherkin - content extracted by the method.
    */
   Gherkin extractGherkin(String text);

}//StandardInterface
