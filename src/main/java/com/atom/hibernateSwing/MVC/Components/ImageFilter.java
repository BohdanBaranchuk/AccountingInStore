package com.atom.hibernateSwing.MVC.Components;

import java.io.File;
import javax.swing.filechooser.*;

/**
 * <h1>Package com.atom.hibernateSwing.MVC.Components</h1>
 *
 * The image filter to show only image files in the fileChooser window
 *
 *
 * @author  atom Earth
 * @version 1.2
 * @since   2017-01-13
 *
 */
public class ImageFilter extends FileFilter {


    /**
     * Accept all directories and all image extensions:
     * <ul>
     *     <li> gif
     *     <li> jpg
     *     <li> jpeg
     *     <li> tiff
     *     <li> tif
     *     <li> png
     * </ul>
     *
     * @param f file what we want to select
     * @return the result of the comparison the extension of the incoming file with valid extensions
     */
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = Utils.getExtension(f);
        if (extension != null) {
            if (extension.equals(Utils.tiff) ||
                    extension.equals(Utils.tif) ||
                    extension.equals(Utils.gif) ||
                    extension.equals(Utils.jpeg) ||
                    extension.equals(Utils.jpg) ||
                    extension.equals(Utils.png)) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }


    /**
     * The description of this filter
     * @return the description of the purpose of this filter
     */
    public String getDescription() {
        return "Just Images";
    }
}
