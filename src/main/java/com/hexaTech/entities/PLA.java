package com.hexaTech.entities;

public class PLA {
    private String path;
    private String extension;

    /**
     * PLA class standard constructor.
     * @param path string - path to PLA file.
     */
    public PLA(String path,String extension){
        this.path=path;
        this.extension=extension;
    }

    /**
     * PLA class empty constructor.
     */
    public PLA(){}

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path=path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension=extension;
    }
}//PLA
