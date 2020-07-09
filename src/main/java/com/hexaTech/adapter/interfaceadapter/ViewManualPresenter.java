package com.hexaTech.adapter.interfaceadapter;

import com.hexaTech.domain.port.out.usecase.ViewManualOutputPort;
import org.springframework.stereotype.Component;

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

    /**
     * Returns the presenter's status.
     * @return String - presenter's status.
     */
    public String getMessage(){
        return message;
    }

}//ViewManualPresenter
