package com.Downloader;


import java.io.*;
import java.net.URL;
import java.net.URLConnection;


public class Downloader extends FileDefault implements IDownloader {

    private final String USER_AGENT; //  the keyword by which the request is known. Need for Adds a general request
    private final String PROPERTY;  // the value associated with it. Need for Adds a general request

    //..................................................................................................................

    public Downloader(String user_agent, String property) {
        USER_AGENT = user_agent;
        PROPERTY = property;
    }

    public Downloader() {
        USER_AGENT = "User-Agent";
        PROPERTY = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)";
    }

    //..................................................................................................................

    @Override
    public boolean download(URL url) {
        return download(url, null, null, true);
    }

    @Override
    public boolean download(URL url, String pathTo) {
        return download(url, pathTo, null, true);
    }

    @Override
    public boolean download(URL url, String pathTo, boolean replaceInExisting) {
        return download(url, pathTo, null, replaceInExisting);
    }

    @Override
    public boolean download(URL url, String pathTo, String nameFile) {
        return download(url, pathTo, nameFile, true);
    }

    @Override
    public boolean download(URL url, String pathTo, String nameFile, boolean replaceInExisting) {

        // default param
        if (url == null) {
            return false;
        }
        if (pathTo == null) {
            pathTo = "";
        }
        if (nameFile == null || nameFile.isEmpty()) {
            nameFile = url.getFile();
        }
        nameFile = getNameFile(nameFile) + getFormat(nameFile);

        createPathForDownload(pathTo, nameFile, replaceInExisting);

        try {
            final URLConnection URL_CONNECTION = url.openConnection();                     // create connection
            URL_CONNECTION.addRequestProperty(getUserAgent(), getProperty());

            final BufferedInputStream BUFFER_INPUT_STREAM = new BufferedInputStream(URL_CONNECTION.getInputStream());

            if (!getFile().exists()) {
                newFile(getFile());
            }

            final FileOutputStream OUTPUT_STREAM = new FileOutputStream(
                    getFile()
            );
            final byte[] DATA_BUFFER = new byte[1024];
            int bytesRead;
            while ((bytesRead = BUFFER_INPUT_STREAM.read(DATA_BUFFER, 0, 1024)) != -1) {             // reading and writhing
                OUTPUT_STREAM.write(DATA_BUFFER, 0, bytesRead);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    private void createPathForDownload(String pathTo, String nameFile, boolean replaceInExisting) {

        String fileSave = pathTo + File.separatorChar + nameFile;
        if (replaceInExisting) {
            setFile(new File(fileSave));
            if (getFile().exists() && getFile().isDirectory()) {
                setFile(notCopyName(getFile().getAbsolutePath()));
            }
        } else {
            setFile(notCopyName(fileSave));
        }


    }

    //..............................................................................................................

    private String getUserAgent() {
        return USER_AGENT;
    }

    private String getProperty() {
        return PROPERTY;
    }

    private String getDefaultNameFile() {
        return "file";
    }


    //..................................................................................................................


    /**
     * Searches for a format in the file name
     *
     * @param file name file. Possibly null
     * @return format file if not found return "" . firs symbol a 'SEPARATOR_FORMAT_FILE' then String format
     */
    private String getFormat(String file) {
        int separator;
        if (file == null || (separator = file.lastIndexOf(SEPARATOR_FORMAT_FILE)) < 0) {
            return "";
        }

        String format = file.substring(separator);

        return (format.length() > 1) ? (format.matches("[.]\\w+") ? format : "") : "";
    }


    private String getNameFile(String name) {

        if (name == null || name.isEmpty()) {
            return getDefaultNameFile();
        }
        int last = name.lastIndexOf(SEPARATOR_FORMAT_FILE);
        name = name.substring(0, (last < 1) ? name.length() : last);

        return (name.matches("[^|\\\\/?*:<>\"]+") ? name : getDefaultNameFile());
    }


}
