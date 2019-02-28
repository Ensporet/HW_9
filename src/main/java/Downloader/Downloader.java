package Downloader;


import java.io.*;
import java.net.URL;
import java.net.URLConnection;


public class Downloader extends File_default implements IDownloader {

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

        {                                                                   // create File for download
            String fileSave = pathTo + File.separator + nameFile;
            if (replaceInExisting) {
                setFile(new File(fileSave));
                if (getFile().exists() && getFile().isDirectory()) {
                    setFile(notCopeName(getFile().getAbsolutePath()));
                }
            } else {
                setFile(notCopeName(fileSave));
            }
        }

        try {
            URLConnection urlConnection = url.openConnection();                     // create connection
            urlConnection.addRequestProperty(getUSER_AGENT(), getPROPERTY());

            BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());
            FileOutputStream fileOutputStream = new FileOutputStream(

                    getFile()

            );
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {             // reading and writhing
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }

        } catch (FileNotFoundException fileNotFoundException) {      // not correct name format for file download

            download(url, pathTo, (getDEFAULT_NAME_FILE() + getFormat(nameFile)), replaceInExisting);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //..............................................................................................................

    public String getUSER_AGENT() {
        return USER_AGENT;
    }

    public String getPROPERTY() {
        return PROPERTY;
    }

    public String getDEFAULT_NAME_FILE() {
        final String DEFAULT_NAME_FILE = "download file";
        return DEFAULT_NAME_FILE;
    }


    //..................................................................................................................


    /**
     * Searches for a format in the file name
     *
     * @param file name file. Possibly null
     * @return format file if not found return "" . firs symbol a 'SEPARATOR_FORMAT_FILE' then String format
     */
    public String getFormat(String file) {
        int separator;
        if (file == null || (separator = file.lastIndexOf(IFile.SEPARATOR_FORMAT_FILE)) < 0) {
            return "";
        }

        String format = file.substring(separator);

        return (format.length() > 1) ? (format.matches("[.]\\w+") ? format : "") : "";
    }


}
