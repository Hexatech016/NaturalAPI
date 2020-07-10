/**
 * @file CreateBALInputPort
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Denis Salviato
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.domain.port.in;

import java.io.IOException;

/**
 * CreateBAL input interface.
 */
public interface CreateBALInputPort {

    /**
     * Create a new BAL object.
     * @throws IOException if an error occurs during loading process.
     */
    void createBAL(String name) throws IOException;

    void hasMethod(int sentinel);

    void showMethod(int sentinel);

    void alterMethod(int sentinel,String type,boolean isArray,boolean isObject);

    void hasParameter(int sentinel,int identifier);

    void showParameter(int sentinel,int identifier);

    void alterParameter(int sentinel,int identifier,String type,boolean isArray,boolean isObject);

    void updateBAL(String nameBAL) throws IOException;

    void showObjects();

    void chooseObject(int position);

}//CreateBALInputPort
