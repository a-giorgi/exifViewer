package it.hci2020.utils;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class FileUtils {
    public static String getFileExtension(File file){//File does not provide a method to detect the extension so I have to make one
        String filename = file.getName();
        int index = filename.lastIndexOf('.');
        return (index == -1) ? "" : filename.substring(index + 1);
    }
    public static FileFilter getJpgFileFilter(){
        return new FileFilter() {

            public String getDescription() {
                return "JPG Images (*.jpg)";
            }

            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    String filename = f.getName().toLowerCase();
                    return filename.endsWith(".jpg") || filename.endsWith(".jpeg") ;
                }
            }
        };
    }
}
