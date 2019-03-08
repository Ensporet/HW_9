package com.Downloader;


import java.io.File;
import java.io.IOException;

/**
 * Class file wrapper
 */
public class FileDefault implements IFile {

    private File file;

    @Override
    public void newFile(File path) {

        if (path == null) {
            path = new File("");
        }

        path = notCopyName(path.getAbsolutePath());

        {
            File parent;
            if (!(parent = new File(path.getParent())).exists()) {
                parent.mkdirs();
            }
        }

        try {
            path.createNewFile();
            setFile(path);
        } catch (IOException e) {
            e.printStackTrace();
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

    @Override
    public File notCopyName(String name) {

        if (name == null) {
            return null;
        }
        if (!new File(name).exists()) {
            return new File(name);
        }

        final int POS = name.lastIndexOf(File.separator); // pack
        int pos1 = name.lastIndexOf(IFile.SEPARATOR_FORMAT_FILE); // .mod
        if (pos1 < 0) {
            pos1 = name.length();
        }
        final String MODIFY_FILE_NAME = getModifyFileName(name, POS, pos1);
        final int POS0 = MODIFY_FILE_NAME.lastIndexOf('(');
        String startPartName;
        int number = 0;

        try {
            number = Integer.valueOf(
                    (MODIFY_FILE_NAME.charAt(MODIFY_FILE_NAME.length() - 1) == ')' &&
                            POS0 > -1) ? MODIFY_FILE_NAME.substring(POS0 + 1, MODIFY_FILE_NAME.length() - 1) : ""
            );
            startPartName = name.substring(0, (POS + 1) + (MODIFY_FILE_NAME.lastIndexOf('(') + 1));

        } catch (NumberFormatException e) {
            startPartName = name.substring(0, pos1) + "(";
        }

        final String MOD = (pos1 > POS) ? ")" + name.substring(pos1) : "";
        while (true) {

            number++;
            File file = new File(startPartName + number + MOD);

            if (!file.exists()) {
                return file;
            }
        }
    }


    private String getModifyFileName(String name, int indexLastSep, int indexSepFormat) {
        if (indexSepFormat > indexLastSep) {
            return name.substring(indexLastSep + 1, indexSepFormat);
        } else {
            return name.substring(indexLastSep + 1);
        }
    }


}