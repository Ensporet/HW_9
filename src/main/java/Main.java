import Downloader.Downloader;

import java.net.MalformedURLException;
import java.net.URL;


public class Main {

    /**
     * The directory where the file will be downloaded
     */
    private static final String catalogForSave = "Download";
    private static final String url =
            "https://files.adme.ru/files/news/part_101/1017310/19069465-" +
                    "f686ec9a6b0714f716d28ed9692f8e82-1479299993-1000-32e9147584-1479300464.jpg";

    public static void main(String[] args) {

        try {
            if (new Downloader().download(
                    new URL(url),
                    catalogForSave,
                    false
            )) {
                System.out.println("Download completed");
            } else {
                System.out.println("Failed to upload file");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
