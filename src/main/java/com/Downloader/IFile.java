package com.Downloader;


import java.io.File;

public interface IFile {

    char SEPARATOR_FORMAT_FILE = '.';

    void newFile(File path);

    File getFile();

    void setFile(File file);

    /**
     * create a unique file
     *
     * @param name file path
     * @return null or File
     */
    File notCopyName(String name);
}
