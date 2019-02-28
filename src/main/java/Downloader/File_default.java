package Downloader;


import java.io.File;
import java.io.IOException;

/**
 * Class file wrapper
 */
public class File_default implements IFile {

    private File file;

    @Override
    public File newFile(File path) {

        if (path == null) {
            path = new File("");
        }


        path = notCopeName(path.getAbsolutePath());

        {
            File parent;
            if (!(parent = new File(path.getParent())).exists()) {
                parent.mkdirs();
            }
        }

        try {
            path.createNewFile();
            setFile(path);
            return path;
        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }

    }

    @Override
    public boolean deleteFile(File f) {

        if (f == null) {
            return true;
        } else {

            setFile((File) null);
            return f.delete();
        }

    }

    @Override
    public File getFile() {
        return this.file;
    }

    @Override
    public void setFile(File file) {
        this.file = file;

    }
}