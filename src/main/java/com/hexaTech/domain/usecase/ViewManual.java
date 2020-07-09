package com.hexaTech.domain.usecase;

import com.hexaTech.domain.port.in.ViewManualInputPort;
import com.hexaTech.domain.port.out.repository.RepoDocumentInterface;
import com.hexaTech.domain.port.out.usecase.ViewManualOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * Class used to manage user manual's opening.
 */
@Component
public class ViewManual implements ViewManualInputPort{

    ViewManualOutputPort viewManualOutputPort;
    RepoDocumentInterface repoDocumentInterface;

    @Autowired
    ViewManual(ViewManualOutputPort viewManualOutputPort,RepoDocumentInterface repoDocumentInterface){
        this.viewManualOutputPort=viewManualOutputPort;
        this.repoDocumentInterface=repoDocumentInterface;
    }

    /**
     * Opens the user manual.
     * @param title String - path to the user manual.
     */
    public void openManual(String title) throws IOException {
        String content=repoDocumentInterface.getContentFromPath("." + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "manual.txt");
        viewManualOutputPort.showViewedManual(getManualSection(content,title));
    }

    private String getManualSection(String content,String title){
        String[] split=content.split("\n");
        boolean found=false;
        StringBuilder toReturn= new StringBuilder("\n");
        for(String sentence:split){
            if(sentence.equals(title))
                found=true;
            else if(found){
                if(sentence.isEmpty())
                    return toReturn.append("\n").toString();
                else
                    toReturn.append("\t").append(sentence).append("\n");
            }//if
        }//for
        return toReturn.toString();
    }//getManualSection

}//ViewManual
