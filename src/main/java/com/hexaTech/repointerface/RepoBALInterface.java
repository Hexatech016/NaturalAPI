/**
 * @file RepoBALInterface
 * @version 1.0.0
 * @type java
 * @data 2020-05-13
 * @author Alessio Barbiero
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.repointerface;

import com.hexaTech.entities.BAL;
import com.hexaTech.entities.Document;

import java.io.IOException;

/**
 * RepoBAL class interface.
 */
public interface RepoBALInterface extends RepoInterface{
    /**
     * Returns BAL object.
     * @return BAL - BAL object.
     */
    Document getBAL();

    BAL setBALFromGherkin(String text);

    void saveBAL(BAL bal) throws IOException;
}//RepoBALInterface
