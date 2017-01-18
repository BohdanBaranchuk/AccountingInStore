package com.atom.hibernateSwing.main;

import javax.swing.*;
import com.atom.hibernateSwing.MVC.*;

/**
 * <h1>Package com.atom.hibernateSwing.main</h1>
 *
 * The main class from program begins.
 *
 *
 * @author  atom Earth
 * @version 1.2
 * @since   2017-01-13
 *
 */
public class StartPoint {

    /**
     * The entry point of the program
     *
     * @param args default parameter.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createAndShowGUI();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * Create Model, View, Controller and binding them together.
     * @throws Exception if impossible to run the app.
     */
    public static void createAndShowGUI() throws Exception {
        View view = new View();
        Model model = new Model();
        Controller controller = new Controller(view,model);

        controller.control();
    }

}
