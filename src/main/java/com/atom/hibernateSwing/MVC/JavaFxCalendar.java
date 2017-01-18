package com.atom.hibernateSwing.MVC;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DateCell;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateStringConverter;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;
import java.awt.Point;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

/**
 * <h1>Package com.atom.hibernateSwing.MVC</h1>
 *
 * It is the wrapper class for the datePicker element. That element is displayed on View and it is used for
 * selecting the purchase and sale dates. {@link JFXPanel} is the container for the {@link DatePicker} element.
 *
 *
 *
 * @author  atom Earth
 * @version 1.2
 * @since   2017-01-13
 *
 * @see View
 * @see ViewAddFrame
 * @see ViewEditFrame
 */
public class JavaFxCalendar {

    private DatePicker datePicker;

    final JFXPanel fxPanel = new JFXPanel();

    public JavaFxCalendar()
    {
        initAndShowGUI();
    }


    /*
     * Builds and displays the JFrame with the JFXPanel.
     * This method is invoked on the Swing Event Dispatch Thread.
     */
    public void initAndShowGUI() {

        Platform.runLater(new Runnable() {
                              public void run() {
                                  fxPanel.setScene(createScene());
                              }
                          }
        );
    }

    public JFXPanel getFxPanel()
    {return fxPanel;}

    private Scene createScene() {

        HBox hBox = new HBox();
        datePicker = new DatePicker(LocalDate.now());
        datePicker.setEditable(false);
        hBox.getChildren().add(datePicker);
        //datePicker.addEventHandler(this);

        return new Scene(hBox);
    }


    /**
     * Validate button's action listener routine.
     */
    private void buttonActionListenerRoutine() {

        if (datePicker.getValue() != null) {
            //System.out.println("DATE picker getValue: "+datePicker.getValue());
        }
    }

    public LocalDate getValue()
    {
        return datePicker.getValue();
    }

    public void setValue(LocalDate ld)
    {
        datePicker.setValue(ld);
    }

}
