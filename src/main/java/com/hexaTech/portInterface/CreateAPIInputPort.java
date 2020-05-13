/**
 * @file CreateAPIInputPort
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Alessio Barbiero
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.portInterface;

import java.io.IOException;

/**
 * CreateAPI input interface.
 */
public interface CreateAPIInputPort{

    /**
     * Crates a new API.
     * @throws IOException if an error occurs during API creation process.
     */
    void createAPI() throws IOException;

}//CreateAPIInputPort
