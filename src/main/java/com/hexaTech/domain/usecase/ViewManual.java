package com.hexaTech.domain.usecase;

import com.hexaTech.domain.port.in.ViewManualInputPort;
import com.hexaTech.domain.port.out.usecase.ViewManualOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
/**
 * Class used to manage user manual's opening.
 */
public class ViewManual implements ViewManualInputPort{

    ViewManualOutputPort viewManualOutputPort;

    ViewManual(ViewManualOutputPort viewManualOutputPort){
        this.viewManualOutputPort=viewManualOutputPort;
    }

    /**
     * Opens the user manual.
     * @param path String - path to the user manual.
     */
    public void openManual(String path) throws IOException {
        try {
            Desktop.getDesktop().open(new File(path));
        }catch (Exception e){
            File file = new File("userManual.pdf");
            if (!file.exists()) {
                InputStream link = (getClass().getResourceAsStream(path));
                Files.copy(link, file.getAbsoluteFile().toPath());
            }
            Desktop.getDesktop().open(file);
        }//try_catch
    }//openManual

}//ViewManual
