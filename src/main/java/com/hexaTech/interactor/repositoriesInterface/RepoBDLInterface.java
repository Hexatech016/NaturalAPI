package com.hexaTech.interactor.repositoriesInterface;

import com.hexaTech.interactor.entities.BDL;
import com.hexaTech.interactor.entities.DoubleStruct;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface RepoBDLInterface {

    BDL createBDL(List<DoubleStruct> tagsForBDLConstruction) throws IOException;
    /**
     * Saves the doucment's path into a backup file.
     * @param title string - title of the file to be saved.
     * @param directory string - directory of the file to be saved
     * @throws IOException if occurs an error while creating the file or writing into it.
     */
    void saveDoc(String title, String directory);

    /**
     * Verifies if the documents exists.
     * @param path string - document's path.
     * @return boolean - true if document exists, false if not.
     */
    boolean existsDoc(String path);

    /**
     * Delete the specified document.
     * @param path string - path to the document to be deleted.
     * @return bool - true if the file exists, false if not.
     */
    boolean deleteDoc(String path);

    /**
     * Gets document's content.
     * @param path string - document path.
     * @return string - document content.
     * @throws IOException if the document doesn't exist.
     */
    String getContentFromPath(String path) throws IOException;

    BDL loadBDLFromJsonFile(String path) throws IOException;

    /**
     * Loads content from a backup file and restore it.
     * @param directory string - directory used to search the file in.
     * @throws IOException if the backup file doesn't exist.
     */
    void loadBackup(String directory) throws IOException;
    void saveBDL(BDL bdl, String BDLpath) throws IOException;

    String importPathOfBDL();
    int getTotalFrequency(Map<String,Integer> list);
    BDL getBDL();
    void setBDL(BDL bdl);
}
