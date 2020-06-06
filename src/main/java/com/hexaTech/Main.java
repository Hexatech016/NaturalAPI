/**
 * @file main
 * @version 1.0.0
 * @type java
 * @data 2020-05-01
 * @author Jacopo Battilana
 * @email hexatech016@gmail.com
 * @license
 * @changeLog
 */

package com.hexaTech;

import com.hexaTech.application.config.SpringConfig;
import com.hexaTech.application.Cli;
import net.didion.jwnl.JWNLException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

/**
 * Class used to manage the flow of program execution.
 */

public class Main{
    public static void main(String[] args) throws IOException, JWNLException {
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(SpringConfig.class);
        Cli client=context.getBean("cli",Cli.class);
        client.useCaseNaturalAPI();
        context.close();
    }//main
}//MainDevelop
