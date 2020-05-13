/**
 * @file CheckThereAreDoc
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Luca Marcon
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.interactor;


import com.hexaTech.portInterface.CheckThereAreDocInputPort;
import com.hexaTech.portInterface.CheckThereAreDocOutputPort;
import com.hexaTech.repo.RepoDiscoverInterface;

/**
 * Class used to manage a file search.
 */
public class CheckThereAreDoc implements CheckThereAreDocInputPort {
    CheckThereAreDocOutputPort checkThereAreDocOP;
    RepoDiscoverInterface repoDiscoverInterface;

    /**
     * CheckThereAreDoc standard constructor.
     * @param checkThereAreDocOP CheckThereAreDocOutputPort - used to send output notifications.
     * @param repo RepoInterface - used to communicate with Repo.
     */
    public CheckThereAreDoc(CheckThereAreDocOutputPort checkThereAreDocOP, RepoDiscoverInterface repo) {
        this.checkThereAreDocOP = checkThereAreDocOP;
        this.repoDiscoverInterface= repo;
    }

    /**
     * Verifies if there are any loaded documents.
     */
    @Override
    public void checkThereAreDoc() {
        checkThereAreDocOP.thereAreDoc(repoDiscoverInterface.checkThereAreDoc());
    }

}//CheckThereAreDoc
