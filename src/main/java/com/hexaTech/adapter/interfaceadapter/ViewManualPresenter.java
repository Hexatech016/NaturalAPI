package com.hexaTech.adapter.interfaceadapter;

import com.hexaTech.domain.port.out.usecase.ViewManualOutputPort;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.File;
import java.io.IOException;

@Component
public class ViewManualPresenter extends ViewManualSubject implements ViewManualOutputPort {
    private String message;

    /**
     * Shows the manual section.
     * @param result string - message text.
     */
    @Override
    public void showViewedManual(String result) {
        message=result;
        notifySubMe();
    }

    @Override
    public void showDocument(File file) throws IOException {
        Desktop.getDesktop().open(file);
    }

    /**
     * Returns the presenter's status.
     * @return String - presenter's status.
     */
    public String getMessage(){
        return message;
    }

}//ViewManualPresenter
