/**
 * @file RepoBALInterface
 * @version 1.0.0
 * @type java
 * @data 2020-05-13
 * @author Alessio Barbiero
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.domain.port.out.repository;

import com.hexaTech.domain.entity.BAL;

/**
 * RepoBAL class interface.
 */
public interface RepoBALInterface{

    /**
     * Fills API object with Swagger found arguments into the specified PLA.
     * @param bal BAL - BAL object.
     */
    void setBAL(BAL bal) throws IllegalArgumentException;

    /**
     * Returns BAL value.
     * @return BAL - object value.
     */
    BAL getBAL();

}//RepoBALInterface