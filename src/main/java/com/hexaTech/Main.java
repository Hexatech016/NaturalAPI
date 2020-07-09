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

import com.hexaTech.adapter.interfaceadapter.discover.DiscoverController;
import com.hexaTech.adapter.interfaceadapter.discover.DiscoverPresenter;
import com.hexaTech.application.Cli;
import com.hexaTech.application.Gui.MainGui;
import com.hexaTech.application.config.SpringConfig;
import com.hexaTech.domain.port.in.*;
import net.didion.jwnl.JWNLException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

/**
 * Class used to manage the flow of program execution.
 */

public class Main{

    public static void main(String[] args) throws IOException, JWNLException {

        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(SpringConfig.class);
        //Cli client=context.getBean("cli",Cli.class);
        //client.useCaseNaturalAPI();
        MainGui test = context.getBean("gui",MainGui.class);

        context.close();
    }//main
}//MainDevelop
