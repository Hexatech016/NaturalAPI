package com.hexaTech.repointerface;

import com.hexaTech.entities.BDL;

import java.io.IOException;

public interface RepoBDLInterface extends RepoInterface {
    BDL extractBDL(String text) throws IOException;
    void saveBDL(BDL bdl) throws IOException;
}
