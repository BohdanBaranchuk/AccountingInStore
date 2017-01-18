package com.atom.hibernateSwing.MVC.Components;

import java.io.File;
import javax.swing.ImageIcon;

/**
 * <h1>Package com.atom.hibernateSwing.MVC.Components</h1>
 *
 * Utils to help work with Image files. All methods are static. All fields are static and final. The fields
 * define the valid extensions of images.
 *
 *
 * @author  atom Earth
 * @version 1.2
 * @since   2017-01-13
 *
 */
public class Utils {
    /** The image file extension */
    public final static String jpeg = "jpeg";
    /** The image file extension */
    public final static String jpg = "jpg";
    /** The image file extension */
    public final static String gif = "gif";
    /** The image file extension */
    public final static String tiff = "tiff";
    /** The image file extension */
    public final static String tif = "tif";
    /** The image file extension */
    public final static String png = "png";

    /**
     * Get the extension of the incoming file
     * @param f incoming file
     * @return the extension of the file
     */
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }

    /**
     * Get the imageIcon from the local path
     *
     * @param path - the address of the image
     * @return imageIcon from the local path
     */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = Utils.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}

