package com.hexaTech.repo;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface RepoInterface {
        void importDoc(String directory);
        void saveDoc(String title, String directory);
        boolean existsDoc(String path);
        boolean deleteDoc(String path);
        String getContentFromPath(String path) throws IOException;
        void loadBackup(String directory) throws FileNotFoundException;
}
