package com.atom.hibernateSwing.MVC;


import com.atom.hibernateSwing.MVC.Components.ImageFileView;
import com.atom.hibernateSwing.MVC.Components.ImagePreview;
import com.atom.hibernateSwing.ProjectEnums.Available;
import com.atom.hibernateSwing.dao.ProductsDAOImpl;
import com.atom.hibernateSwing.model.*;
import com.atom.hibernateSwing.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * <h1>Package com.atom.hibernateSwing.MVC</h1>
 *
 * This is the class that controls all program's logic.
 *
 *
 * @author  atom Earth
 * @version 1.2
 * @since   2017-01-13
 *
 */
public class Controller{

    /**
     * Static initializer for configure the log4j2 using the XML file.
     */
    static {
        new DOMConfigurator().doConfigure("log4j2.xml", LogManager.getLoggerRepository());
    }

    // create a logger Object
    static Logger logger = Logger.getLogger(Controller.class);

    // main elements for Controller (MVC)
    private View view;
    private ViewAddFrame viewAddFrame;
    private ViewEditFrame viewEditFrame;
    private Model model;

    // all actionListener for graphic elements (main view)
    private ActionListener jbSearch;    // button search
    private ActionListener jbAdd;       // button add Entity
    private ActionListener jbChange;    // button change Entity
    private ActionListener jbDelete;    // button delete Entity
    private ActionListener jbNextImage; // button show next image for selected row
    private ActionListener jbPrevImage; // button show previous image for selected row
    private ListSelectionListener jtSelectRow; // select row in table
    private WindowListener viewClosing; // when close main View

    // all actionListeners for graphic elements (add view)
    private ActionListener add_jcbSupplier;     // comboBox select supplier from list or add new
    private ActionListener add_addImage1;
    private ActionListener add_removeImage1;
    private ActionListener add_addImage2;
    private ActionListener add_removeImage2;
    private ActionListener add_addImage3;
    private ActionListener add_removeImage3;
    private ActionListener add_addImage4;
    private ActionListener add_removeImage4;
    private ActionListener add_addImage5;
    private ActionListener add_removeImage5;
    private ActionListener add_saveChanges;     // press button Add
    private ActionListener add_discardChanges;  // press button Cancel
    private WindowListener add_openWindow;      // when open AddView


    // all actionListeners for graphic elements (edit view)
    private ActionListener edit_jcbSupplier;    // comboBox select supplier from list or change information about current Supplier
    private ActionListener edit_addChangeImage1;
    private ActionListener edit_removeImage1;
    private ActionListener edit_addChangeImage2;
    private ActionListener edit_removeImage2;
    private ActionListener edit_addChangeImage3;
    private ActionListener edit_removeImage3;
    private ActionListener edit_addChangeImage4;
    private ActionListener edit_removeImage4;
    private ActionListener edit_addChangeImage5;
    private ActionListener edit_removeImage5;
    private ActionListener edit_save;           // save button
    private ActionListener edit_cancel;         // cancel button


    // save current selected ID (for delete and edit operations)
    Object selectedID = null;           // selected row's id in the data table

    // Dao object for manipulate records in database
    ProductsDAOImpl productsDAO;
    // All products reading from DB
    List<Products> gettedPr;
    // products reading from DB with criteria
    List<Products> gettedPrCr;
    // All suppliers reading from DB
    List<Supplier> gettedSupplier;
    // current selected object
    Products activePr;
    // active index of Image
    int indexImage =0;
    // read product by ID
    Products prId;
    // count for periodically release the memory
    int countReleaseMemory =0;
    int actionsQuantity=20;

    // properties of Add View
    private JFileChooser fc1;       // fileChooser for first image
    private File file1;             // path of first image
    private JFileChooser fc2;
    private File file2;
    private JFileChooser fc3;
    private File file3;
    private JFileChooser fc4;
    private File file4;
    private JFileChooser fc5;
    private File file5;

    // properties of Edit View
    private JFileChooser e_fc1;       // fileChooser for first image
    private File e_file1;             // path of first image
    private JFileChooser e_fc2;
    private File e_file2;
    private JFileChooser e_fc3;
    private File e_file3;
    private JFileChooser e_fc4;
    private File e_file4;
    private JFileChooser e_fc5;
    private File e_file5;

    // flag if remove image in edit Form
    private boolean removeImage1 = false;
    private boolean removeImage2 = false;
    private boolean removeImage3 = false;
    private boolean removeImage4 = false;
    private boolean removeImage5 = false;


    /**
     * The default constructor. Initialize the fields with param's values.
     *
     * @param view view form
     * @param model model for table
     */
    public Controller(View view, Model model) {
        super();
        this.view = view;
        this.model = model;
    }

    /**
     * The main method in the constructor. The execution of the app begins from this point.
     *
     */
    public void control()
    {
        //LOGGER.info("Run application");
        logger.debug("Run application");
        // create all actionListeners for main View
        createActionListeners();

        // add actionListeners to buttons
        view.getButton_Add().addActionListener(jbAdd);
        view.getButton_Change().addActionListener(jbChange);
        view.getButton_Delete().addActionListener(jbDelete);
        view.getButton_Search().addActionListener(jbSearch);
        view.getButton_prevImage().addActionListener(jbPrevImage);
        view.getButton_nextImage().addActionListener(jbNextImage);

        // add actionListener for selected row in table with data
        ListSelectionModel cellSelectionModel = view.getTable().getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cellSelectionModel.addListSelectionListener(jtSelectRow);

        // add windowListener for main View (save data when application closing)
        view.getFrame().addWindowListener(viewClosing);

        // set model for main View
        view.getTable().setModel(model);

        // open database and read records
        try{
            productsDAO = new ProductsDAOImpl();
            productsDAO.openCurrentSessionWithTransaction();

            retrieveObjects();

            updateUI(gettedPrCr,gettedSupplier);

            computeAdditionalInform(gettedPrCr);

        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(view.getFrame(),
                    "Проверьте соединение с базой данных",
                    "Уведомление",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            logger.error("System exit with error",e);
            System.exit(1);
        }
        finally {
            //System.exit(0);
        }
    }


    /**
     * Create actionListeners for main {@link View} components.
     */
    private void createActionListeners()
    {
        jbSearch = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    logger.debug("Press button Search");
                    gettedPrCr = productsDAO.getProducts_HQL_custom(searchCriteria());
                    model.fillDataForTable(gettedPrCr);
                    model.setDataVector(model.getDataFromDB(),Constants.TABLE_HEADER);
                    computeAdditionalInform(gettedPrCr);


                }
                catch (Exception se)
                {
                    logger.error("Exception after press button Search",se);
                    se.printStackTrace();
                }


            }
        };

        jbAdd = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.debug("Press button Add");
                if(viewAddFrame == null) {
                    viewAddFrame = new ViewAddFrame();

                    createActionListenersAddView();

                    // add actionListeners for AddView
                    viewAddFrame.getSelect_Supplier().addActionListener(add_jcbSupplier);
                    viewAddFrame.getButton_ImageAdd1().addActionListener(add_addImage1);
                    viewAddFrame.getButton_ImageDel1().addActionListener(add_removeImage1);
                    viewAddFrame.getButton_ImageAdd2().addActionListener(add_addImage2);
                    viewAddFrame.getButton_ImageDel2().addActionListener(add_removeImage2);
                    viewAddFrame.getButton_ImageAdd3().addActionListener(add_addImage3);
                    viewAddFrame.getButton_ImageDel3().addActionListener(add_removeImage3);
                    viewAddFrame.getButton_ImageAdd4().addActionListener(add_addImage4);
                    viewAddFrame.getButton_ImageDel4().addActionListener(add_removeImage4);
                    viewAddFrame.getButton_ImageAdd5().addActionListener(add_addImage5);
                    viewAddFrame.getButton_ImageDel5().addActionListener(add_removeImage5);
                    viewAddFrame.getButton_Add().addActionListener(add_saveChanges);
                    viewAddFrame.getButton_Cancel().addActionListener(add_discardChanges);
                    viewAddFrame.getFrame().addWindowListener(add_openWindow);



                } else {
                    viewAddFrame.getFrame().setVisible(true);
                }


                // fill all comboBoxes for Add View
                viewAddFrame.getSelect_Color().removeAllItems();
                for (Object ob:model.getUniqueColor()) {
                    viewAddFrame.getSelect_Color().addItem(ob);
                }
                if(viewAddFrame.getSelect_Color().getItemCount()>0) {
                    viewAddFrame.getSelect_Color().removeItemAt(0);
                }


                viewAddFrame.getSelect_Type().removeAllItems();
                for (Object ob:model.getUniqueType()) {

                    viewAddFrame.getSelect_Type().addItem(ob);
                }
                if(viewAddFrame.getSelect_Type().getItemCount()>0) {
                    viewAddFrame.getSelect_Type().removeItemAt(0);
                }

                viewAddFrame.getSelect_Supplier().removeAllItems();
                for (Supplier sp:model.getUniqueSupplier()) {
                    Object ob = sp.getName();
                    viewAddFrame.getSelect_Supplier().addItem(ob);
                }

            }
        };

        jbChange = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.debug("Press button Change");
                if(selectedID != null) {
                    // Are you really want to edit the record?
                    int n = JOptionPane.showConfirmDialog(
                            view.getFrame(),
                            "Вы действительно хотите изменить запись под номером " + selectedID + "?",
                            "Подтверждение изменения",
                            JOptionPane.YES_NO_OPTION);
                    if (n == 0) // if Yes
                    {

                        if(viewEditFrame == null){

                            // create form
                            viewEditFrame = new ViewEditFrame();

                            // create action listeners for form's elements
                            createActionListenersEditView();

                            // add action listeners for each elements
                            viewEditFrame.getSelect_Supplier().addActionListener(edit_jcbSupplier);
                            viewEditFrame.getButton_ImageAdd1().addActionListener(edit_addChangeImage1);
                            viewEditFrame.getButton_ImageDel1().addActionListener(edit_removeImage1);
                            viewEditFrame.getButton_ImageAdd2().addActionListener(edit_addChangeImage2);
                            viewEditFrame.getButton_ImageDel2().addActionListener(edit_removeImage2);
                            viewEditFrame.getButton_ImageAdd3().addActionListener(edit_addChangeImage3);
                            viewEditFrame.getButton_ImageDel3().addActionListener(edit_removeImage3);
                            viewEditFrame.getButton_ImageAdd4().addActionListener(edit_addChangeImage4);
                            viewEditFrame.getButton_ImageDel4().addActionListener(edit_removeImage4);
                            viewEditFrame.getButton_ImageAdd5().addActionListener(edit_addChangeImage5);
                            viewEditFrame.getButton_ImageDel5().addActionListener(edit_removeImage5);
                            viewEditFrame.getButton_Edit().addActionListener(edit_save);
                            viewEditFrame.getButton_Cancel().addActionListener(edit_cancel);

                        } else {
                            viewEditFrame.getFrame().setVisible(true);
                        }

                        long id = Long.parseLong(selectedID.toString());
                        try {
                            prId = productsDAO.getProductById(id);
                            if(prId != null) {
                                fillEditForm(prId);
                            }
                        } catch (SQLException sqle)
                        {
                            sqle.printStackTrace();
                        }

                        e_file1 = null;
                        e_file2 = null;
                        e_file3 = null;
                        e_file4 = null;
                        e_file5 = null;

                        // clear flags for remove image
                        removeImage1 = false;
                        removeImage2 = false;
                        removeImage3 = false;
                        removeImage4 = false;
                        removeImage5 = false;

                    }

                }
            }
        };

        jbDelete = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.debug("Press button Delete");
                if(selectedID != null) {
                    // Are you really want to delete the record?
                    int n = JOptionPane.showConfirmDialog(
                            view.getFrame(),
                            "Вы действительно хотите удалить запись под номером " + selectedID + "?",
                            "Подтверждение удаления",
                            JOptionPane.YES_NO_OPTION);
                    if (n == 0) // if Yes
                    {
                        try {
                            if(selectedID != null) {
                                long id = Long.parseLong(selectedID.toString());
                                productsDAO.deleteProductById(id);
                                productsDAO.commitCurrentSession();

                                retrieveObjects();
                                updateUI(gettedPrCr,gettedSupplier);

                                gettedPrCr = productsDAO.getProducts_HQL_custom(searchCriteria());
                                model.fillDataForTable(gettedPrCr);
                                model.setDataVector(model.getDataFromDB(),Constants.TABLE_HEADER);
                                computeAdditionalInform(gettedPrCr);

                                // ok, the record was deleted
                                JOptionPane.showMessageDialog(view.getFrame(),
                                        "Запись под номером "+id+" успешно удалена.",
                                        "Уведомление",
                                        JOptionPane.INFORMATION_MESSAGE);
                            }
                        }catch (Exception se)
                        {
                            se.printStackTrace();
                            printErrorWithRollback("Ошибка удаления записи!");
                        }
                    }

                }

            }
        };

        jbNextImage = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.debug("Press button show next image");
                if(activePr != null) {
                    if ((activePr.getImages() != null) && (activePr.getImages().size() > 0)) {
                        if(indexImage<(activePr.getImages().size()-1))
                        {
                            indexImage++;
                            view.displayImage(activePr.getImages().get(indexImage).getData());   // show next image
                        }
                    }
                }
            }
        };

        jbPrevImage = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.debug("Press button show previous image");
                if(activePr != null) {
                    if ((activePr.getImages() != null) && (activePr.getImages().size() > 0)) {
                        if(indexImage>0)
                        {
                            indexImage--;
                            view.displayImage(activePr.getImages().get(indexImage).getData());   // show prev image
                        }
                    }
                }
            }
        };

        jtSelectRow = new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                int[] selectedRow = view.getTable().getSelectedRows();
                int[] selectedColumns = view.getTable().getSelectedColumns();

                if(selectedRow.length>0) {
                    selectedID = (Object) view.getTable().getValueAt(selectedRow[0], 0);


                    // periodically release memory
                    if(selectedID != null)
                    {
                        logger.debug("Select row in table with ID "+selectedID);
                        countReleaseMemory++;
                        if(countReleaseMemory == actionsQuantity)
                        {
                            logger.debug("Release memory after "+countReleaseMemory+" selecting operations");
                            countReleaseMemory = 0;
                            productsDAO.clear();
                            try {
                                retrieveObjects();
                            } catch (SQLException seq)
                            {seq.printStackTrace();}
                        }
                    }

                    System.out.println("Selected ID= " + selectedID);
                    for (Products p:gettedPrCr) {
                        if(p.getId()==Long.parseLong(selectedID.toString())) {
                            activePr = p;       // save current selected Product
                            indexImage = 0;     // reset index for show image
                            if((activePr.getImages() != null) &&(activePr.getImages().size()>0)) {
                                view.displayImage(activePr.getImages().get(0).getData());   // show first image (if exist)
                            } else
                            {
                                view.displayZeroImage();
                            }
                        }
                    }
                }else
                {
                    activePr = null;
                    System.out.println("Selected ID= null");
                }
            }
        };


        // transaction and close connection with DB before close application
        viewClosing = new WindowListener() {
            public void windowOpened(WindowEvent e) {

            }

            public void windowClosing(WindowEvent e) {
                logger.debug("Close application");
                if( productsDAO != null) {
                    try {
                        productsDAO.closeCurrentSessionWithTransaction();
                        System.out.println("Database closed");
                    } catch (Exception ep)
                    {
                        ep.printStackTrace();
                        logger.error("Close application with error",ep);
                        System.exit(1);
                    }
                }
            }

            public void windowClosed(WindowEvent e) {

            }

            public void windowIconified(WindowEvent e) {

            }

            public void windowDeiconified(WindowEvent e) {

            }

            public void windowActivated(WindowEvent e) {

            }

            public void windowDeactivated(WindowEvent e) {

            }
        };
    }

    /**
     * Create actionListeners for {@link ViewAddFrame} components.
     */
    private void createActionListenersAddView()
    {
        add_jcbSupplier = new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JComboBox cb = (JComboBox)e.getSource();
                String supName = (String)cb.getSelectedItem();

                for (Supplier sp:model.getUniqueSupplier()) {
                    if(sp.getName() != null) {
                        if (sp.getName().equals(supName)) {
                            logger.debug("AddForm: select another Supplier for product");
                            if(sp.getContacts() != null) {
                                viewAddFrame.getText_SupplierContacts().setText(sp.getContacts());
                            } else {
                                viewAddFrame.getText_SupplierContacts().setText("");
                            }

                            if(sp.getUrlAdress() != null) {
                                viewAddFrame.getText_SupplierURL().setText(sp.getUrlAdress());
                            } else {
                                viewAddFrame.getText_SupplierURL().setText("");
                            }
                            break;
                        }
                    }
                }
            }
        };

        add_addImage1 = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Set up the file chooser.
                if (fc1 == null) {
                    fc1 = new JFileChooser();
                    //Add a custom file filter and disable the default
                    //(Accept All) file filter.
                    fc1.addChoosableFileFilter(new com.atom.hibernateSwing.MVC.Components.ImageFilter());
                    fc1.setAcceptAllFileFilterUsed(false);

                    //Add the preview pane.
                    fc1.setAccessory(new ImagePreview(fc1));
                }

                //Show it.
                int returnVal = fc1.showDialog(view.getFrame(),"Attach");

                //Process the results.
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    logger.debug("AddForm: press button add Image1 and select file");
                    file1 = fc1.getSelectedFile();
                    viewAddFrame.getShow_ImageAdr1().setText(file1.getAbsolutePath());
                }

                //Reset the file chooser for the next time it's shown.
                fc1.setSelectedFile(null);
            }
        };

        add_removeImage1 = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(file1 != null)
                {
                    logger.debug("AddForm: press button remove Image1");
                    file1 = null;
                    viewAddFrame.getShow_ImageAdr1().setText("Изображение не выбрано ....");
                }
            }
        };

        add_addImage2 = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Set up the file chooser.
                if (fc2 == null) {
                    fc2 = new JFileChooser();
                    //Add a custom file filter and disable the default
                    //(Accept All) file filter.
                    fc2.addChoosableFileFilter(new com.atom.hibernateSwing.MVC.Components.ImageFilter());
                    fc2.setAcceptAllFileFilterUsed(false);

                    //Add the preview pane.
                    fc2.setAccessory(new ImagePreview(fc2));
                }

                //Show it.
                int returnVal = fc2.showDialog(view.getFrame(),"Attach");

                //Process the results.
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    logger.debug("AddForm: press button add Image2 and select file");
                    file2 = fc2.getSelectedFile();
                    viewAddFrame.getShow_ImageAdr2().setText(file2.getAbsolutePath());
                }

                //Reset the file chooser for the next time it's shown.
                fc2.setSelectedFile(null);
            }
        };

        add_removeImage2 = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(file2 != null)
                {
                    logger.debug("AddForm: press button remove Image2");
                    file2 = null;
                    viewAddFrame.getShow_ImageAdr2().setText("Изображение не выбрано ....");
                }
            }
        };

        add_addImage3 = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Set up the file chooser.
                if (fc3 == null) {
                    fc3 = new JFileChooser();
                    //Add a custom file filter and disable the default
                    //(Accept All) file filter.
                    fc3.addChoosableFileFilter(new com.atom.hibernateSwing.MVC.Components.ImageFilter());
                    fc3.setAcceptAllFileFilterUsed(false);

                    //Add the preview pane.
                    fc3.setAccessory(new ImagePreview(fc3));
                }

                //Show it.
                int returnVal = fc3.showDialog(view.getFrame(),"Attach");

                //Process the results.
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    logger.debug("AddForm: press button add Image3 and select file");
                    file3 = fc3.getSelectedFile();
                    viewAddFrame.getShow_ImageAdr3().setText(file3.getAbsolutePath());
                }

                //Reset the file chooser for the next time it's shown.
                fc3.setSelectedFile(null);
            }
        };

        add_removeImage3 = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(file3 != null)
                {
                    logger.debug("AddForm: press button remove Image3");
                    file3 = null;
                    viewAddFrame.getShow_ImageAdr3().setText("Изображение не выбрано ....");
                }
            }
        };

        add_addImage4 = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Set up the file chooser.
                if (fc4 == null) {
                    fc4 = new JFileChooser();
                    //Add a custom file filter and disable the default
                    //(Accept All) file filter.
                    fc4.addChoosableFileFilter(new com.atom.hibernateSwing.MVC.Components.ImageFilter());
                    fc4.setAcceptAllFileFilterUsed(false);

                    //Add the preview pane.
                    fc4.setAccessory(new ImagePreview(fc4));
                }

                //Show it.
                int returnVal = fc4.showDialog(view.getFrame(),"Attach");

                //Process the results.
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    logger.debug("AddForm: press button add Image4 and select file");
                    file4 = fc4.getSelectedFile();
                    viewAddFrame.getShow_ImageAdr4().setText(file4.getAbsolutePath());
                }

                //Reset the file chooser for the next time it's shown.
                fc4.setSelectedFile(null);
            }
        };

        add_removeImage4 = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(file4 != null)
                {
                    logger.debug("AddForm: press button remove Image4");
                    file4 = null;
                    viewAddFrame.getShow_ImageAdr4().setText("Изображение не выбрано ....");
                }
            }
        };

        add_addImage5 = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Set up the file chooser.
                if (fc5 == null) {
                    fc5 = new JFileChooser();
                    //Add a custom file filter and disable the default
                    //(Accept All) file filter.
                    fc5.addChoosableFileFilter(new com.atom.hibernateSwing.MVC.Components.ImageFilter());
                    fc5.setAcceptAllFileFilterUsed(false);

                    //Add the preview pane.
                    fc5.setAccessory(new ImagePreview(fc5));
                }

                //Show it.
                int returnVal = fc5.showDialog(view.getFrame(),"Attach");

                //Process the results.
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    logger.debug("AddForm: press button add Image5 and select file");
                    file5 = fc5.getSelectedFile();
                    viewAddFrame.getShow_ImageAdr5().setText(file5.getAbsolutePath());
                }

                //Reset the file chooser for the next time it's shown.
                fc5.setSelectedFile(null);
            }
        };

        add_removeImage5 = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(file5 != null)
                {
                    logger.debug("AddForm: press button remove Image5");
                    file5 = null;
                    viewAddFrame.getShow_ImageAdr5().setText("Изображение не выбрано ....");
                }
            }
        };

        add_saveChanges = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if((viewAddFrame.getText_Name().getText().length()>0))        // if text field not empty
                {
                    logger.debug("AddForm: press button Save changes");
                    boolean errorFlag = false;

                    // create new Product for add  it to DB
                    Products newPr = new Products();
                    // set name for product
                    newPr.setName(viewAddFrame.getText_Name().getText());
                    // set available
                    newPr.setAvailable(Available.Yes);
                    // set type for product
                    if((viewAddFrame.getSelect_Type().getSelectedItem() != null) && (viewAddFrame.getSelect_Type().getSelectedItem().toString().length()>0)) {
                        newPr.setType(viewAddFrame.getSelect_Type().getSelectedItem().toString());
                        //System.out.println("type "+ viewAddFrame.getSelect_Type().getSelectedItem().toString());
                    }
                    // set size for product
                    if(viewAddFrame.getText_Size().getText().length()>0) {
                        newPr.setSize(viewAddFrame.getText_Size().getText());
                        //System.out.println("size "+viewAddFrame.getText_Size().getText());
                    }
                    // set color for product
                    if((viewAddFrame.getSelect_Color().getSelectedItem() != null) && (viewAddFrame.getSelect_Color().getSelectedItem().toString().length()>0)) {
                        newPr.setColor(viewAddFrame.getSelect_Color().getSelectedItem().toString());
                        //System.out.println("color "+ viewAddFrame.getSelect_Color().getSelectedItem().toString());
                    }

                    // set purchase cost
                    Price price = new Price();

                    if(viewAddFrame.getText_Cost().getText().length()>0) {
                        try {
                            price.setCostPrice(Integer.parseInt(viewAddFrame.getText_Cost().getText()));
                        }
                        catch (NumberFormatException nfe)
                        {
                            errorFlag=true;
                        }

                    }

                    // set sold cost
                    if(viewAddFrame.getText_CostSold().getText().length()>0) {
                        try {
                            price.setSoldPrice(Integer.parseInt(viewAddFrame.getText_CostSold().getText()));
                        }
                        catch (NumberFormatException nfe)
                        {
                            errorFlag = true;
                        }
                    }

                    newPr.setPrice(price);

                    // set purchase date
                    ProductsDate times = new ProductsDate();

                    LocalDate localDate = viewAddFrame.getJavaFxCalendarPurchaseDate().getValue();
                    java.util.Date date = java.sql.Date.valueOf(localDate);
                    times.setPurchaseDate(date);

                    newPr.setTimes(times);


                    // set description
                    if(viewAddFrame.getText_Description().getText().length()>0) {
                        newPr.getDescription().add(viewAddFrame.getText_Description().getText());
                    }

                    // add product with famous supplier or create new
                    boolean findSupplier = false;
                    for (Supplier sp:model.getUniqueSupplier()) {
                        if( viewAddFrame.getSelect_Supplier().getSelectedItem() != null) {
                            if (sp.getName().equals(viewAddFrame.getSelect_Supplier().getSelectedItem().toString())) {
                                newPr.setSupplier(sp);
                                findSupplier = true;
                                break;
                            }
                        }
                    }

                    Supplier supplier = null;
                    if(!findSupplier)
                    {
                        if((viewAddFrame.getSelect_Supplier().getSelectedItem() != null) && (viewAddFrame.getSelect_Supplier().getSelectedItem().toString().length() > 0)) {
                            supplier = new Supplier();
                            supplier.setName(viewAddFrame.getSelect_Supplier().getSelectedItem().toString());

                            if(viewAddFrame.getText_SupplierContacts().getText().length() > 0)
                            {
                                supplier.setContacts(viewAddFrame.getText_SupplierContacts().getText());
                            }
                            if(viewAddFrame.getText_SupplierURL().getText().length() > 0)
                            {
                                supplier.setUrlAdress(viewAddFrame.getText_SupplierURL().getText());
                            }
                            newPr.setSupplier(supplier);
                        }
                        else {
                            errorFlag = true;
                        }
                    }


                    // set image 1
                    if(file1 != null)
                    {
                        productAddImage(file1,newPr);
                        file1 = null;
                    }
                    // set image 2
                    if(file2 != null)
                    {
                        productAddImage(file2,newPr);
                        file2 = null;
                    }
                    // set image 3
                    if(file3 != null)
                    {
                        productAddImage(file3,newPr);
                        file3 = null;
                    }
                    // set image 4
                    if(file4 != null)
                    {
                        productAddImage(file4,newPr);
                        file4 = null;
                    }
                    // set image 5
                    if(file5 != null)
                    {
                        productAddImage(file5,newPr);
                        file5 = null;
                    }

                    //System.out.println("+"+viewAddFrame.getSelect_Type().getSelectedItem().toString()+"+");

                    if(errorFlag)       // if was error to get data from view form
                    {
                        logger.error("AddForm: error with save new product in DB. Getting data have not correct format");
                        // show warning message
                        JOptionPane.showMessageDialog(viewAddFrame.getFrame(),
                                "Новая запись не сохранена! Проверьте правильность введения данных!",
                                "Ошыбка сохранения",
                                JOptionPane.ERROR_MESSAGE);
                    } else {       // save entity in DB
                        try {
                            productsDAO.addProduct(newPr,supplier);
                            productsDAO.commitCurrentSession();

                            // get new objects from DB for fill comboBoxes
                            retrieveObjects();
                            // update display and fill comboBoxes with new data
                            updateUI(gettedPrCr,gettedSupplier);
                            logger.debug("AddForm: successful save new product in DB");

                        } catch (Exception sqle)
                        {
                            logger.error("AddForm: error with save new Product in DB",sqle);
                            sqle.printStackTrace();
                            printErrorWithRollback("Ошибка добавления записи!");
                        }
                    }
                }
            }
        };

        add_discardChanges = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.debug("AddForm: press button Cancel changes");
                viewAddFrame.getFrame().setVisible(false);
            }
        };

        add_openWindow = new WindowListener() {
            public void windowOpened(WindowEvent e) {

            }

            public void windowClosing(WindowEvent e) {

            }

            public void windowClosed(WindowEvent e) {

            }

            public void windowIconified(WindowEvent e) {

            }

            public void windowDeiconified(WindowEvent e) {

            }

            public void windowActivated(WindowEvent e) {

            }

            public void windowDeactivated(WindowEvent e) {

            }
        };
    }

    /**
     * Create actionListeners for {@link ViewEditFrame} components.
     */
    private void  createActionListenersEditView()
    {
        edit_jcbSupplier = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                String supName = (String)cb.getSelectedItem();

                for (Supplier sp:model.getUniqueSupplier()) {
                    if(sp.getName() != null) {
                        if (sp.getName().equals(supName)) {
                            logger.debug("EditForm: select new Supplier with name: "+ sp.getName());
                            if(sp.getContacts() != null) {
                                viewEditFrame.getText_SupplierContacts().setText(sp.getContacts());
                            } else {
                                viewEditFrame.getText_SupplierContacts().setText("");
                            }

                            if(sp.getUrlAdress() != null) {
                                viewEditFrame.getText_SupplierURL().setText(sp.getUrlAdress());
                            } else {
                                viewEditFrame.getText_SupplierURL().setText("");
                            }
                            break;
                        }
                    }
                }
            }
        };

        edit_addChangeImage1 =new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Set up the file chooser.
                if (e_fc1 == null) {
                    e_fc1 = new JFileChooser();
                    //Add a custom file filter and disable the default
                    //(Accept All) file filter.
                    e_fc1.addChoosableFileFilter(new com.atom.hibernateSwing.MVC.Components.ImageFilter());
                    e_fc1.setAcceptAllFileFilterUsed(false);

                    //Add the preview pane.
                    e_fc1.setAccessory(new ImagePreview(e_fc1));
                }

                //Show it.
                int returnVal = e_fc1.showDialog(viewEditFrame.getFrame(),"Attach");

                //Process the results.
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    logger.debug("EditForm: press button Add image1 and select file");
                    e_file1 = e_fc1.getSelectedFile();
                    viewEditFrame.getShow_ImageAdr1().setText(e_file1.getAbsolutePath());
                    removeImage1=false;
                }

                //Reset the file chooser for the next time it's shown.
                e_fc1.setSelectedFile(null);
            }
        };

        edit_removeImage1 = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.debug("EditForm: press button remove image1");
                if(e_file1 != null) {
                    e_file1 = null;
                    viewEditFrame.getShow_ImageAdr1().setText(" - ");
                } else {
                    if((prId.getImages() != null) && (prId.getImages().size()>0)) {
                        viewEditFrame.getShow_ImageAdr1().setText(" - ");
                        removeImage1 = true;
                    }
                }

            }
        };

        edit_addChangeImage2 = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Set up the file chooser.
                if (e_fc2 == null) {
                    e_fc2 = new JFileChooser();
                    //Add a custom file filter and disable the default
                    //(Accept All) file filter.
                    e_fc2.addChoosableFileFilter(new com.atom.hibernateSwing.MVC.Components.ImageFilter());
                    e_fc2.setAcceptAllFileFilterUsed(false);

                    //Add the preview pane.
                    e_fc2.setAccessory(new ImagePreview(e_fc2));
                }

                //Show it.
                int returnVal = e_fc2.showDialog(viewEditFrame.getFrame(),"Attach");

                //Process the results.
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    logger.debug("EditForm: press button Add image2 and select file");
                    e_file2 = e_fc2.getSelectedFile();
                    viewEditFrame.getShow_ImageAdr2().setText(e_file2.getAbsolutePath());
                    removeImage2=false;
                }

                //Reset the file chooser for the next time it's shown.
                e_fc2.setSelectedFile(null);
            }
        };

        edit_removeImage2 = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.debug("EditForm: press button remove image2");
                if(e_file2 != null) {
                    e_file2 = null;
                    viewEditFrame.getShow_ImageAdr2().setText(" - ");
                } else {
                    if((prId.getImages() != null) && (prId.getImages().size()>1)) {
                        viewEditFrame.getShow_ImageAdr2().setText(" - ");
                        removeImage2 = true;
                    }
                }
            }
        };

        edit_addChangeImage3 = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Set up the file chooser.
                if (e_fc3 == null) {
                    e_fc3 = new JFileChooser();
                    //Add a custom file filter and disable the default
                    //(Accept All) file filter.
                    e_fc3.addChoosableFileFilter(new com.atom.hibernateSwing.MVC.Components.ImageFilter());
                    e_fc3.setAcceptAllFileFilterUsed(false);

                    //Add the preview pane.
                    e_fc3.setAccessory(new ImagePreview(e_fc3));
                }

                //Show it.
                int returnVal = e_fc3.showDialog(viewEditFrame.getFrame(),"Attach");

                //Process the results.
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    logger.debug("EditForm: press button Add image3 and select file");
                    e_file3 = e_fc3.getSelectedFile();
                    viewEditFrame.getShow_ImageAdr3().setText(e_file3.getAbsolutePath());
                    removeImage3=false;
                }

                //Reset the file chooser for the next time it's shown.
                e_fc3.setSelectedFile(null);
            }
        };

        edit_removeImage3 = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.debug("EditForm: press button remove image3");
                if(e_file3 != null) {
                    e_file3 = null;
                    viewEditFrame.getShow_ImageAdr3().setText(" - ");
                } else {
                    if((prId.getImages() != null) && (prId.getImages().size()>2))
                    {
                        viewEditFrame.getShow_ImageAdr3().setText(" - ");
                        removeImage3 =true;
                    }
                }
            }
        };

        edit_addChangeImage4 = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Set up the file chooser.
                if (e_fc4 == null) {
                    e_fc4 = new JFileChooser();
                    //Add a custom file filter and disable the default
                    //(Accept All) file filter.
                    e_fc4.addChoosableFileFilter(new com.atom.hibernateSwing.MVC.Components.ImageFilter());
                    e_fc4.setAcceptAllFileFilterUsed(false);

                    //Add the preview pane.
                    e_fc4.setAccessory(new ImagePreview(e_fc4));
                }

                //Show it.
                int returnVal = e_fc4.showDialog(viewEditFrame.getFrame(),"Attach");

                //Process the results.
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    logger.debug("EditForm: press button Add image4 and select file");
                    e_file4 = e_fc4.getSelectedFile();
                    viewEditFrame.getShow_ImageAdr4().setText(e_file4.getAbsolutePath());
                    removeImage4=false;
                }

                //Reset the file chooser for the next time it's shown.
                e_fc4.setSelectedFile(null);
            }
        };

        edit_removeImage4 = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.debug("EditForm: press button remove image4");
                if(e_file4 != null) {
                    e_file4 = null;
                    viewEditFrame.getShow_ImageAdr4().setText(" - ");
                } else {
                    if ((prId.getImages() != null) && (prId.getImages().size() > 3)) {
                        viewEditFrame.getShow_ImageAdr4().setText(" - ");
                        removeImage4 = true;
                    }
                }
            }
        };

        edit_addChangeImage5 = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Set up the file chooser.
                if (e_fc5 == null) {
                    e_fc5 = new JFileChooser();
                    //Add a custom file filter and disable the default
                    //(Accept All) file filter.
                    e_fc5.addChoosableFileFilter(new com.atom.hibernateSwing.MVC.Components.ImageFilter());
                    e_fc5.setAcceptAllFileFilterUsed(false);

                    //Add the preview pane.
                    e_fc5.setAccessory(new ImagePreview(e_fc5));
                }

                //Show it.
                int returnVal = e_fc5.showDialog(viewEditFrame.getFrame(),"Attach");

                //Process the results.
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    logger.debug("EditForm: press button Add image5 and select file");
                    e_file5 = e_fc5.getSelectedFile();
                    viewEditFrame.getShow_ImageAdr5().setText(e_file5.getAbsolutePath());
                    removeImage5=false;
                }

                //Reset the file chooser for the next time it's shown.
                e_fc5.setSelectedFile(null);
            }
        };

        edit_removeImage5 = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.debug("EditForm: press button remove image5");
                if(e_file5 != null) {
                    e_file5 = null;
                    viewEditFrame.getShow_ImageAdr5().setText(" - ");
                } else {
                    if((prId.getImages() != null) && (prId.getImages().size()>4)) {
                        viewEditFrame.getShow_ImageAdr5().setText(" - ");
                        removeImage5 = true;
                    }
                }
            }
        };

        edit_save = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.debug("EditForm: press button Save changes");
                saveEditChanges();
                viewEditFrame.getFrame().setVisible(false);
            }
        };

        edit_cancel = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.debug("EditForm: press button Cancel");
                viewEditFrame.getFrame().setVisible(false);
            }
        };
    }

    /**
     * Save all changes in ViewEditFrame form or if some parameters are incorrect to discard the changes and close
     * ViewEditFrame without saving.
     */
    private void saveEditChanges()
    {
        logger.debug("EditForm: try to save changes with product");
        if (prId != null)       // if we get correct product
        {
            boolean errorFlag = false;          // if some incoming information not correct set errorFlag to true

            /*
            if(viewEditFrame.getText_Name().getText().length() >0) // change name block
            {
                if(!viewEditFrame.getText_Name().getText().equals(prId.getName()))
                {
                    prId.setName(viewEditFrame.getText_Name().getText());
                }
            }
            */

            // set type for product
            if((viewEditFrame.getSelect_Type().getSelectedItem() != null) && (viewEditFrame.getSelect_Type().getSelectedItem().toString().length()>0)) {
                prId.setType(viewEditFrame.getSelect_Type().getSelectedItem().toString());
            }

            // set size for product
            if(viewEditFrame.getText_Size().getText().length()>0) {
                prId.setSize(viewEditFrame.getText_Size().getText());
            }

            // set color for product
            if((viewEditFrame.getSelect_Color().getSelectedItem() != null) && (viewEditFrame.getSelect_Color().getSelectedItem().toString().length()>0)) {
                prId.setColor(viewEditFrame.getSelect_Color().getSelectedItem().toString());
            }

            // set Price
            if(prId.getPrice() == null)
            {
                Price price = new Price();
                boolean wereChange = false;     // if we change some fields

                if(viewEditFrame.getText_Cost().getText().length()>0) {
                    try {
                        price.setCostPrice(Integer.parseInt(viewEditFrame.getText_Cost().getText()));
                        wereChange=true;
                    }
                    catch (NumberFormatException nfe)
                    {
                        logger.error("EditForm: not correct cost format in field",nfe);
                        errorFlag=true;
                    }

                }

                // set sold cost
                if(viewEditFrame.getText_CostSold().getText().length()>0) {
                    try {
                        price.setSoldPrice(Integer.parseInt(viewEditFrame.getText_CostSold().getText()));
                        wereChange=true;
                    }
                    catch (NumberFormatException nfe)
                    {
                        logger.error("EditForm: not correct sold format in field",nfe);
                        errorFlag = true;
                    }
                }
                if(wereChange)
                {
                    prId.setPrice(price);
                }
            } else {
                if(viewEditFrame.getText_Cost().getText().length()>0) {
                    try {
                        prId.getPrice().setCostPrice(Integer.parseInt(viewEditFrame.getText_Cost().getText()));
                    }
                    catch (NumberFormatException nfe)
                    {
                        errorFlag=true;
                    }

                }

                // set sold cost
                if(viewEditFrame.getText_CostSold().getText().length()>0) {
                    try {
                        prId.getPrice().setSoldPrice(Integer.parseInt(viewEditFrame.getText_CostSold().getText()));
                    }
                    catch (NumberFormatException nfe)
                    {
                        errorFlag = true;
                    }
                }
            }

            // set purchase date
            LocalDate localDate = viewEditFrame.getJavaFxCalendarPurchaseDate().getValue();
            java.util.Date datePr = java.sql.Date.valueOf(localDate);
            prId.getTimes().setPurchaseDate(datePr);

            if(viewEditFrame.getSelect_Available().getSelectedIndex() == 1) // if select No
            {
                LocalDate localDateS = viewEditFrame.getJavaFxCalendarSoldDate().getValue();
                java.util.Date dateS = java.sql.Date.valueOf(localDateS);
                prId.getTimes().setSaleDate(dateS);
            }

            // set available
            if(viewEditFrame.getSelect_Available().getSelectedIndex() == 1) // if select No
            {
                prId.setAvailable(Available.No);
            } else {
                prId.setAvailable(Available.Yes);
            }

            // set description
            if(viewEditFrame.getText_Description().getText().length()>0) {
                prId.getDescription().add(0,viewEditFrame.getText_Description().getText());
            }

            // set Supplier
            if(viewEditFrame.getSelect_Supplier().getSelectedItem() != null)
            {
                // edit product with famous supplier or create new
                boolean findSupplier = false;
                for (Supplier sp:model.getUniqueSupplier()) {
                    if (sp.getName().equals(viewEditFrame.getSelect_Supplier().getSelectedItem().toString())) {
                        prId.setSupplier(sp);
                        findSupplier = true;
                        break;
                    }
                }
                if(!findSupplier)
                {
                    Supplier sp = new Supplier();
                    sp.setName(viewEditFrame.getSelect_Supplier().getSelectedItem().toString());
                    prId.setSupplier(sp);
                }

                if(viewEditFrame.getText_SupplierContacts().getText().length()>0)
                {
                    prId.getSupplier().setContacts(viewEditFrame.getText_SupplierContacts().getText());
                }

                if(viewEditFrame.getText_SupplierURL().getText().length()>0)
                {
                    prId.getSupplier().setUrlAdress(viewEditFrame.getText_SupplierURL().getText());
                }

            } else {
                prId.setSupplier(null);
            }


            // set Images
            // set image 1
            if(e_file1 != null)
            {
                try {
                    ImageWrapper iw =  prId.getImages().get(0);
                    prId.removeImage(iw);
                    productAddImage(e_file1, prId);
                } catch (IndexOutOfBoundsException iob)
                {
                    productAddImage(e_file1, prId);
                }
            } else if (removeImage1)
            {
                ImageWrapper iw =  prId.getImages().get(0);
                prId.removeImage(iw);
            }
            // set image 2
            if(e_file2 != null)
            {
                try {
                    ImageWrapper iw =  prId.getImages().get(1);
                    prId.removeImage(iw);
                    productAddImage(e_file2, prId);
                } catch (IndexOutOfBoundsException iob)
                {
                    productAddImage(e_file2, prId);
                }
            } else if(removeImage2)
            {
                ImageWrapper iw =  prId.getImages().get(1);
                prId.removeImage(iw);
            }
            // set image 3
            if(e_file3 != null)
            {
                try {
                    ImageWrapper iw =  prId.getImages().get(2);
                    prId.removeImage(iw);
                    productAddImage(e_file3, prId);
                } catch (IndexOutOfBoundsException iob)
                {
                    productAddImage(e_file3, prId);
                }
            } else if (removeImage3)
            {
                ImageWrapper iw =  prId.getImages().get(2);
                prId.removeImage(iw);
            }
            // set image 4
            if(e_file4 != null)
            {
                try {
                    ImageWrapper iw =  prId.getImages().get(3);
                    prId.removeImage(iw);
                    productAddImage(e_file4, prId);
                } catch (IndexOutOfBoundsException iob)
                {
                    productAddImage(e_file4, prId);
                }
            } else if (removeImage4)
            {
                ImageWrapper iw =  prId.getImages().get(3);
                prId.removeImage(iw);
            }
            // set image 5
            if(e_file5 != null)
            {
                try {
                    ImageWrapper iw =  prId.getImages().get(4);
                    prId.removeImage(iw);
                    productAddImage(e_file5, prId);
                } catch (IndexOutOfBoundsException iob)
                {
                    productAddImage(e_file5, prId);
                }
            } else if(removeImage5)
            {
                ImageWrapper iw =  prId.getImages().get(4);
                prId.removeImage(iw);
            }



            if(errorFlag)       // if was error to get data from view form
            {
                logger.error("EditForm: error with save changes of product in DB");
                // show warning message
                JOptionPane.showMessageDialog(viewAddFrame.getFrame(),
                        "Изменения не сохранены! Проверьте правильность введения данных!",
                        "Ошыбка редактирования записи",
                        JOptionPane.ERROR_MESSAGE);
            } else {       // save entity in DB
                try {
                    productsDAO.saveOrUpdate(prId);
                    productsDAO.commitCurrentSession();
                    // get new objects from DB for fill comboBoxes
                    retrieveObjects();
                    // update display and fill comboBoxes with new data
                    updateUI(gettedPrCr,gettedSupplier);
                    logger.debug("EditForm: successful save product's changes");

                } catch (Exception sqle)
                {
                    logger.error("EditForm: error with save changes",sqle);
                    sqle.printStackTrace();
                    printErrorWithRollback("Ошибка именения записи!");
                }
            }

            e_file1 = null;
            e_file2 = null;
            e_file3 = null;
            e_file4 = null;
            e_file5 = null;
        }
    }

    /**
     * Choose the image file in ViewEditFrame.
     *
     * @param jfc fileChooser for selecting the image. It is invoked when press the button "Добавить/Изменить" image.
     * @param cf file to operate with image.
     * @param ve parent Frame.
     */
    private void chooseFile(JFileChooser jfc,File cf,ViewEditFrame ve)
    {
        //Set up the file chooser.
        if (jfc == null) {
            jfc = new JFileChooser();
            //Add a custom file filter and disable the default
            //(Accept All) file filter.
            jfc.addChoosableFileFilter(new com.atom.hibernateSwing.MVC.Components.ImageFilter());
            jfc.setAcceptAllFileFilterUsed(false);

            //Add the preview pane.
            jfc.setAccessory(new ImagePreview(jfc));
        }

        //Show it.
        int returnVal = jfc.showDialog(ve.getFrame(),"Attach");

        //Process the results.
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            cf = jfc.getSelectedFile();
            ve.getShow_ImageAdr1().setText(cf.getAbsolutePath());
        }

        //Reset the file chooser for the next time it's shown.
        jfc.setSelectedFile(null);
    }

    // fill edit form before open editView (if we select product in table and this product was founded in database)

    /**
     * Fill {@link ViewEditFrame} with parameters before open it (if we select product in the table and that product was founded in the
     * database).
     *
     * @param inPr selected product in the table of {@link View} form.
     */
    private void fillEditForm(Products inPr) {
        if (inPr != null)       // if we get correct product
        {
            viewEditFrame.getText_Name().setText(inPr.getName());       // set name

            if(inPr.getType() != null)                                  // set type
            {
                viewEditFrame.getSelect_Type().addItem(inPr.getType());
            }

            if(inPr.getSize() != null)                                  // set size
            {
                viewEditFrame.getText_Size().setText(inPr.getSize());
            } else
            {
                viewEditFrame.getText_Size().setText("");
            }

            if(inPr.getColor() != null)                                  // set color
            {
                viewEditFrame.getSelect_Color().addItem(inPr.getColor());
            }

            if(inPr.getPrice() != null)                                  // set cost and sold
            {
                String cost = String.valueOf(inPr.getPrice().getCostPrice());
                String sold = String.valueOf(inPr.getPrice().getSoldPrice());
                viewEditFrame.getText_Cost().setText(cost);
                viewEditFrame.getText_CostSold().setText(sold);
            } else
            {
                viewEditFrame.getText_Cost().setText("");
                viewEditFrame.getText_CostSold().setText("");
            }

            if(inPr.getTimes() != null )            // set sale date and purchase date
            {
                Date datePurchase = inPr.getTimes().getPurchaseDate();
                Date dateSale = inPr.getTimes().getSaleDate();

                if(datePurchase != null) {
                    LocalDate ldPurchase = LocalDate.of(datePurchase.getYear() + 1900, datePurchase.getMonth() + 1, datePurchase.getDate());
                    viewEditFrame.getJavaFxCalendarPurchaseDate().setValue(ldPurchase);
                }

                if(dateSale != null) {
                    LocalDate ldSale = LocalDate.of(dateSale.getYear() + 1900, dateSale.getMonth() + 1, dateSale.getDate());
                    viewEditFrame.getJavaFxCalendarSoldDate().setValue(ldSale);
                }
            }

            if(inPr.getAvailable() == Available.Yes)                                // set Available
            {
                viewEditFrame.getSelect_Available().setSelectedIndex(0);            // select YES
            } else {
                viewEditFrame.getSelect_Available().setSelectedIndex(1);            // select NO
            }


            if  ((inPr.getDescription() != null) && (inPr.getDescription().size() > 0)) // set description
            {
                viewEditFrame.getText_Description().setText(inPr.getDescription().get(0));
            } else {
                viewEditFrame.getText_Description().setText("");
            }

            // set images names
            viewEditFrame.getShow_ImageAdr1().setText(" - ");
            viewEditFrame.getShow_ImageAdr2().setText(" - ");
            viewEditFrame.getShow_ImageAdr3().setText(" - ");
            viewEditFrame.getShow_ImageAdr4().setText(" - ");
            viewEditFrame.getShow_ImageAdr5().setText(" - ");
            if((inPr.getImages() != null) && (inPr.getImages().size() >0 ))
            {
                for(int i=0;i<inPr.getImages().size();i++)
                {
                    if(inPr.getImages().get(i) == null)
                    {
                        continue;
                    }
                    switch (i)
                    {
                        case 0:
                        {
                            viewEditFrame.getShow_ImageAdr1().setText(inPr.getImages().get(i).getName());
                            break;
                        }
                        case 1:
                        {
                            viewEditFrame.getShow_ImageAdr2().setText(inPr.getImages().get(i).getName());
                            break;
                        }
                        case 2:
                        {
                            viewEditFrame.getShow_ImageAdr3().setText(inPr.getImages().get(i).getName());
                            break;
                        }
                        case 3:
                        {
                            viewEditFrame.getShow_ImageAdr4().setText(inPr.getImages().get(i).getName());
                            break;
                        }
                        case 4:
                        {
                            viewEditFrame.getShow_ImageAdr5().setText(inPr.getImages().get(i).getName());
                            break;
                        }
                    }
                }
            }


            // set Supplier
            if(inPr.getSupplier() != null)
            {
                if(inPr.getSupplier().getName() != null) {
                    viewEditFrame.getSelect_Supplier().removeAllItems();
                    viewEditFrame.getSelect_Supplier().addItem(inPr.getSupplier().getName());
                }
                // fill another items
                for (Supplier sp:model.getUniqueSupplier()) {
                    Object ob = sp.getName();
                    viewEditFrame.getSelect_Supplier().addItem(ob);
                }

                if(inPr.getSupplier().getContacts() != null) {
                    viewEditFrame.getText_SupplierContacts().setText(inPr.getSupplier().getContacts());
                } else {
                    viewEditFrame.getText_SupplierContacts().setText("");
                }

                if(inPr.getSupplier().getUrlAdress() != null) {
                    viewEditFrame.getText_SupplierURL().setText(inPr.getSupplier().getUrlAdress());
                } else {
                    viewEditFrame.getText_SupplierURL().setText("");
                }

            } else          // if supplier was not added for that product
            {

                viewEditFrame.getSelect_Supplier().removeAllItems();
                viewEditFrame.getSelect_Supplier().addItem("");

                // fill another items
                for (Supplier sp:model.getUniqueSupplier()) {
                    Object ob = sp.getName();
                    viewEditFrame.getSelect_Supplier().addItem(ob);
                }
                viewEditFrame.getText_SupplierContacts().setText("");
                viewEditFrame.getText_SupplierURL().setText("");
            }
        }
    }

    // collect all parameters from view before search in DB

    /**
     * Create a StringBuilder request in HQL. Collect all selected parameters in the {@link View}.
     *
     * @return combined request for search the product in the database. It will be used in the function
     * {@link ProductsDAOImpl#getProducts_HQL_custom} for search all products meets the requirements.
     */
    private StringBuilder searchCriteria()
    {
        StringBuilder whereReguest = new StringBuilder();

        // comboBox Available
        if (view.getSelect_Available().getSelectedIndex() != 0)     // not select all
        {
            if(view.getSelect_Available().getSelectedIndex() == 1)  // select available true
            {
                whereReguest.append("WHERE p.available='Yes'");
            }
            else                                                    // select available false
            {
                whereReguest.append("WHERE p.available='No'");
            }
        }

        // comboBox Type
        if(view.getSelect_Type().getSelectedItem() != null)         // if selected item not empty
        {
            if(view.getSelect_Type().getSelectedItem() != "Все")    // if we would like to add criteria
            {
                if(whereReguest.length()==0)
                {
                    whereReguest.append("WHERE p.type='"+view.getSelect_Type().getSelectedItem()+"'");
                } else {
                    whereReguest.append(" AND p.type='"+view.getSelect_Type().getSelectedItem()+"'");
                }

            }
        }

        // text field name
        if((view.getText_Name().getText() != null) && (view.getText_Name().getText().length()>0))         // if text field not empty
        {
            if(whereReguest.length()==0)
            {
                whereReguest.append("WHERE p.name='"+view.getText_Name().getText()+"'");
            } else {
                whereReguest.append(" AND p.name='"+view.getText_Name().getText()+"'");
            }
        }

        // text fiel size
        if((view.getText_Size().getText() != null) && (view.getText_Size().getText().length()>0))         // if text field not empty
        {
            if(whereReguest.length()==0)
            {
                whereReguest.append("WHERE p.size='"+view.getText_Size().getText()+"'");
            } else {
                whereReguest.append(" AND p.size='"+view.getText_Size().getText()+"'");
            }
        }

        // comboBox color
        if(view.getSelect_Color().getSelectedItem() != null)         // if selected item not empty
        {
            if(view.getSelect_Color().getSelectedItem() != "Все")    // if we would like to add criteria
            {
                if(whereReguest.length()==0)
                {
                    whereReguest.append("WHERE p.color='"+view.getSelect_Color().getSelectedItem()+"'");
                } else {
                    whereReguest.append(" AND p.color='"+view.getSelect_Color().getSelectedItem()+"'");
                }
            }
        }

        // comboBox supplier's name
        if(view.getSelect_Supplier().getSelectedItem() != null)         // if selected item not empty
        {
            if(view.getSelect_Supplier().getSelectedItem() != "Все")    // if we would like to add criteria
            {
                if(whereReguest.length()==0)
                {
                    whereReguest.append("WHERE p.supplier.name='"+view.getSelect_Supplier().getSelectedItem()+"'");
                } else {
                    whereReguest.append(" AND p.supplier.name='"+view.getSelect_Supplier().getSelectedItem()+"'");
                }
            }
        }

        // comboBox purchase cost
        if(view.getSelect_Cost_Criteria().getSelectedItem() != null)         // if selected item not empty
        {
            if(view.getSelect_Cost_Criteria().getSelectedItem() != "Все")    // if we would like to add criteria
            {
                if((view.getText_Cost().getText() != null) && (view.getText_Cost().getText().length()>0)) {
                    if (whereReguest.length() == 0) {
                        whereReguest.append("WHERE p.price.costPrice" + view.getSelect_Cost_Criteria().getSelectedItem() + view.getText_Cost().getText());
                    } else {
                        whereReguest.append(" AND p.price.costPrice" + view.getSelect_Cost_Criteria().getSelectedItem() + view.getText_Cost().getText());
                    }
                }
            }
        }

        // comboBox sold cost
        if(view.getSelect_CostSold_Criteria().getSelectedItem() != null)         // if selected item not empty
        {
            if(view.getSelect_CostSold_Criteria().getSelectedItem() != "Все")    // if we would like to add criteria
            {
                if((view.getText_CostSold().getText() != null) && (view.getText_CostSold().getText().length()>0)) {
                    if (whereReguest.length() == 0) {
                        whereReguest.append("WHERE p.price.soldPrice" + view.getSelect_CostSold_Criteria().getSelectedItem() + view.getText_CostSold().getText());
                    } else {
                        whereReguest.append(" AND p.price.soldPrice" + view.getSelect_CostSold_Criteria().getSelectedItem() + view.getText_CostSold().getText());
                    }
                }
            }
        }

        // comboBox purchase date
        if(view.getSelect_DatePursache_Criteria().getSelectedItem() != null)         // if selected item not empty
        {
            if(view.getSelect_DatePursache_Criteria().getSelectedItem() != "Все")    // if we would like to add criteria
            {
                LocalDate localDate = view.getJavaFxCalendarPursacheDate().getValue();

                if (whereReguest.length() == 0) {
                    whereReguest.append("WHERE p.times.purchaseDate" + view.getSelect_DatePursache_Criteria().getSelectedItem() +"'"+ localDate+"'");
                } else {
                    whereReguest.append(" AND p.times.purchaseDate" + view.getSelect_DatePursache_Criteria().getSelectedItem() +"'"+ localDate+"'");
                }

            }
        }

        // comboBox sold date
        if(view.getSelect_DateSold_Criteria().getSelectedItem() != null)         // if selected item not empty
        {
            if(view.getSelect_DateSold_Criteria().getSelectedItem() != "Все")    // if we would like to add criteria
            {
                LocalDate localDate = view.getJavaFxCalendarSoldDate().getValue();

                if (whereReguest.length() == 0) {
                    whereReguest.append("WHERE p.times.saleDate" + view.getSelect_DateSold_Criteria().getSelectedItem() +"'"+ localDate+"'");
                } else {
                    whereReguest.append(" AND p.times.saleDate" + view.getSelect_DateSold_Criteria().getSelectedItem() +"'"+ localDate+"'");
                }

            }
        }

        return whereReguest;
    }

    // update main view using information from DB

    /**
     * Update displayed on the {@link View} information using last fetched products and suppliers.
     *
     * @param inPr all fetched products from the database.
     * @param inSp all fetched suppliers from the database.
     */
    private void updateUI(List<Products> inPr,List<Supplier> inSp)
    {
        model.fillDataForTable(inPr);
        model.fillDataForComboBoxes(inPr);
        model.fillDataForSupplierComboBox(inSp);

        // fill comboBoxes
        view.getSelect_Color().removeAllItems();
        for (Object ob:model.getUniqueColor()) {
            view.getSelect_Color().addItem(ob);
        }

        view.getSelect_Type().removeAllItems();
        for (Object ob:model.getUniqueType()) {
            view.getSelect_Type().addItem(ob);
        }

        view.getSelect_Supplier().removeAllItems();
        view.getSelect_Supplier().addItem("Все");
        for (Supplier sp:model.getUniqueSupplier()) {
            Object ob = sp.getName();
            view.getSelect_Supplier().addItem(ob);
        }
    }

    // get all from DB and save them in List

    /**
     * Read products and suppliers from the database and assign them to the local fields {@link Controller#gettedPrCr} ,
     *  {@link Controller#gettedSupplier}.
     *
     * @throws SQLException if get exception during fetching data process.
     */
    private void retrieveObjects () throws SQLException
    {
        gettedPrCr = productsDAO.getAllProducts_criteria();
        gettedSupplier = productsDAO.getUniqueSupplier();
    }

    /**
     * Get all products from the database.
     *
     * @return the list of the products.
     * @throws SQLException if something was wrong.
     */
    private  List<Products> allPrRead() throws SQLException
    {
        return productsDAO.getAllProducts_criteria();
    }

    /**
     * Get all supplier from the database.
     *
     * @return list of the suppliers.
     * @throws SQLException if something was wrong.
     */
    private List<Supplier> unSupplier() throws SQLException
    {
        return productsDAO.getUniqueSupplier();
    }

    // add new image to product

    /**
     * Add new image to the selected product.
     *
     * @param fl selected image in the fileChooser
     * @param prs selected product.
     */
    private void productAddImage(File fl,Products prs)
    {
        byte[] imageData = new byte[(int) fl.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(fl);
            fileInputStream.read(imageData);
            fileInputStream.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        ImageWrapper imageWrapper = new ImageWrapper();
        imageWrapper.setName(fl.getName());
        imageWrapper.setData(imageData);
        prs.addImage(imageWrapper);
    }

    // if we get error

    /**
     * Invoke this function if you get error during some of the next processes: add/edit/remove. Pass the error message
     * to the function.
     *
     * @param message error message that we want to print
     */
    private void printErrorWithRollback(String message)
    {
        JOptionPane.showMessageDialog(view.getFrame(),
                message,
                "Уведомление",
                JOptionPane.ERROR_MESSAGE);

        productsDAO.rollback();
        try {
            retrieveObjects();
            updateUI(gettedPrCr,gettedSupplier);

            gettedPrCr = productsDAO.getProducts_HQL_custom(searchCriteria());
            model.fillDataForTable(gettedPrCr);
            model.setDataVector(model.getDataFromDB(),Constants.TABLE_HEADER);
            computeAdditionalInform(gettedPrCr);
        } catch (SQLException sqle)
        {sqle.printStackTrace(); }
    }

    /**
     * Calculate the statistic information for the showing products in the table. Show calculated information
     * in the {@link View} in the statistic section.
     *
     * @param incomingPr showed products in the table.
     */
    private void computeAdditionalInform(List<Products> incomingPr)
    {
        AdditionalInformation ad = new AdditionalInformation(incomingPr);
        view.getShow_QuantityElements().setText(""+ad.getCountEntities());
        view.getShow_SumCost().setText(""+ad.getSumPurchases());
        view.getShow_SumCostSold().setText(""+ad.getSumSold());
    }

    /**
     * The inner class. The goal of this class is collecting the addition information for showed products in the table.
     */
    private class AdditionalInformation
    {
        public AdditionalInformation(List<Products> pr)
        {
            calculate(pr);
        }

        int countEntities, sumPurchases,sumSold;

        private void calculate(List<Products> pr)
        {
            countEntities = 0;
            sumPurchases = 0;
            sumSold = 0;

            if(pr != null)
            {
                countEntities = pr.size();
                for (Products prIn: pr) {
                    if(prIn.getPrice() != null){
                        sumPurchases += prIn.getPrice().getCostPrice();
                        sumSold += prIn.getPrice().getSoldPrice();
                    }
                }
            }
        }

        public int getCountEntities() {
            return countEntities;
        }

        public int getSumPurchases() {
            return sumPurchases;
        }

        public int getSumSold() {
            return sumSold;
        }

    }

}

