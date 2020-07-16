package com.hexaTech.domain.usecase.discover;

import com.hexaTech.domain.port.in.AddGherkinToDiscoverInputPort;
import com.hexaTech.domain.port.out.repository.RepoGherkinInterface;
import com.hexaTech.domain.port.out.usecase.AddGherkinToDiscoverOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AddGherkinToDiscover implements AddGherkinToDiscoverInputPort {
    private final AddGherkinToDiscoverOutputPort addGherkinToDiscoverOutputPort;

    private final RepoGherkinInterface repoGherkinInterface;



    /**
     * AddGherkin class constructor.
     * @param addGherkinToDiscoverOutputPort AddGherkinOutputPort - used to send output notifications.
     * @param repoGherkinInterface RepoInterface - used to communicate with repo.
     */
    @Autowired
    public AddGherkinToDiscover(AddGherkinToDiscoverOutputPort addGherkinToDiscoverOutputPort, RepoGherkinInterface repoGherkinInterface) {
        this.addGherkinToDiscoverOutputPort = addGherkinToDiscoverOutputPort;
        this.repoGherkinInterface = repoGherkinInterface;
    }

    /**
     * Loads a new Gherkin scenario.
     * @throws IOException if an error occurs during loading process.
     */
    @Override
    public void addGherkin(String directory,String document) throws IOException {
        //if_else
        addGherkinToDiscoverOutputPort.showDone(repoGherkinInterface.importDoc(directory, document));
    }//addGherkin
}
