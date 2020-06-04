package com.hexaTech.adapter.repository;

import com.hexaTech.domain.entity.BAL;
import com.hexaTech.domain.port.out.repository.RepoBALInterface;
import org.springframework.stereotype.Component;

@Component
public class RepoBAL implements RepoBALInterface {
    private BAL BAL;

    /**
     * Class empty constructor.
     */
    public RepoBAL(){
        BAL =new BAL();
    }

    /**
     * Fills API object with Swagger found arguments into the specified PLA.
     * @param bal BAL - BAL object.
     */
    @Override
    public void setBAL(BAL bal) throws IllegalArgumentException {
        this.BAL =bal;
    }

    /**
     * Returns BAL value.
     * @return BAL - object value.
     */
    @Override
    public BAL getBAL() {
        return BAL;
    }

}//RepoBAL
