package Downloader;

import java.net.URL;

public interface IDownloader {


    boolean download(URL url);

    boolean download(URL url, String pathTo);

    boolean download(URL url, String pathTo, boolean replaceInExisting);

    boolean download(URL url, String pathTo, String nameFile);

    /**
     * @param url
     * @param pathTo            catalog for download
     * @param nameFile          new name file download
     * @param replaceInExisting false - always create new file
     * @return
     */
    boolean download(URL url, String pathTo, String nameFile, boolean replaceInExisting);


}
