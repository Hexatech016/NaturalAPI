/**
 * @file RepoDocumentInterface
 * @version 1.0.0
 * @type java
 * @data 2020-05-13
 * @author Eduard Serban
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.repointerface;

import com.hexaTech.entities.Document;

import java.util.List;

/**
 * RepoDocument class interface.
 */
public interface RepoDocumentInterface extends RepoInterface{
    List<Document> getDocuments();

}//RepoDocumentInterface
