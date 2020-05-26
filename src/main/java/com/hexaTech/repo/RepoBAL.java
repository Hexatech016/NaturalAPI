package com.hexaTech.repo;

import com.hexaTech.entities.BAL;
import com.hexaTech.repointerface.RepoBALInterface;

public class RepoBAL implements RepoBALInterface {
    BAL bal;

    /**
     * Class empty constructor.
     */
    public RepoBAL(){
        bal=new BAL();
    }

    /**
     * Fills API object with Swagger found arguments into the specified PLA.
     * @param bal BAL - BAL object.
     */
    @Override
    public void setBAL(BAL bal) throws IllegalArgumentException {
        this.bal=bal;
    }

    /**
     * Returns BAL value.
     * @return BAL - object value.
     */
    @Override
    public BAL getBAL() {
        return bal;
    }

}//RepoBAL
