package com.hexaTech.repo;

import com.hexaTech.entities.Document;

import java.util.List;

public interface RepoGherkinInterface extends RepoInterface {
    List<Document> getGherkin();
}


