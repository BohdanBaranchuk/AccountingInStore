package com.atom.hibernateSwing.MVC;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 * <h1>Package com.atom.hibernateSwing.MVC</h1>
 * Main View form is showing when application begin start.
 * <p>
 * Form contains Table for show reading from database entities.
 * <p>
 * Form provides several buttons that are useful for add new entry, edit entry selected in the table and remove
 * entry. They are: <b>"Добавить"</b>, <b>"Изменить"</b>, <b>"Удалить"</b>.
 * <p>
 * If selected entry has images, they are showing in the right top box. Pressing the button <b>"&#62;"</b> shows next image,
 * pressing the button <b>"&#60;"</b> shows previous image.
 * <p>
 * In the box named "Критерии поиска" you can limit showing entries in the table. Just select necessary parameters in
 * the fields and press the button <b>"Поиск"</b>.
 * <p>
 * The data about count of viewed rows, the amount of purchases and the amount of the sale are showed int the box named
 * "Статистика по базе".
 * <hr>
 * All components on the Form are Swing components excepting DatePickers. They are <i>javaFx</i> components.
 *
 * @author  atom Earth
 * @version 1.2
 * @since   2017-01-13
 *
 * @see ViewAddFrame
 * @see ViewEditFrame
 */

public class View{

    /**
     * All components for Form.
     */
    JTable table;
    JFrame frame;

    JLabel info_inStore;
    JLabel info_Type;
    JLabel info_Name;
    JLabel info_Color;
    JLabel info_Size;
    JLabel info_Supplier;
    JLabel info_Cost;
    JLabel info_grn;
    JLabel info_grn2;
    JLabel info_CostSold;
    JLabel info_DatePurchase;
    JLabel info_DateSold;
    JLabel info_DateCalendar;
    JLabel info_DateCalendar2;
    JLabel info_QuantityElements;
    JLabel info_SumCost;
    JLabel info_SumCostSold;
    JLabel show_QuantityElements;
    JLabel show_SumCost;
    JLabel show_SumCostSold;
    JLabel show_Image;

    JButton button_Search;
    JButton button_Add;
    JButton button_Change;
    JButton button_Delete;
    JButton button_nextImage;
    JButton button_prevImage;

    JTextField text_Name;
    JTextField text_Size;
    JTextField text_Cost;
    JTextField text_CostSold;

    JComboBox select_Available;
    JComboBox select_Type;
    JComboBox select_Color;
    JComboBox select_Supplier;
    JComboBox select_Cost_Criteria;
    JComboBox select_CostSold_Criteria;
    JComboBox select_DatePursache_Criteria;
    JComboBox select_DateSold_Criteria;

    JavaFxCalendar javaFxCalendarPursacheDate;
    JavaFxCalendar javaFxCalendarSoldDate;

    String select_Criteria[] = {"Все",">=","<=","="};
    String select_Criteria_Date[] = {"Все",">","<","="};
    String select_AvailableCriteria[] = {"Все","Да","Нет"};

    GridLayout gridLayout_SearchCriteria;
    JPanel jPanel_SearchCriteria;                   // JPanel for search operation
    JPanel jPanel_ManipulateDB;                     // JPanel for add, change and remove from SQL database
    JPanel jPanel_SearchManipulateAggregate;        // JPanel for jPanel_SearchCriteria and jPanel_ManipulateDB
    JPanel jPanel_AggregateInformation;             // JPanel for show aggregate information
    JPanel jPanel_Image;                            // JPanel for show Image from DB
    JPanel jPanel_SearchManipulateAggregateImage;   // JPanel for all another jPanels


    /**
     * The single class constructor. Create swing UI components, add them to the JPanels, combining JPanels and add
     * them to the JFrame. Constructor specifying the exact component's position on the Form.
     */
    public View() {
        // Create views swing UI components
        table = new JTable();

        info_inStore = new JLabel("Наличие в магазине: ");
        info_Type = new JLabel("Тип: ");
        info_Name = new JLabel("Название: ");
        info_Color = new JLabel("Цвет: ");
        info_Size = new JLabel("Розмер: ");
        info_Supplier = new JLabel("Магазин закупки: ");
        info_Cost = new JLabel("Цена закупки: ");
        info_grn = new JLabel("грн. ");
        info_grn2 = new JLabel("грн. ");
        info_CostSold = new JLabel("Цена продажы ");
        info_DatePurchase = new JLabel("Дата покупки: ");
        info_DateSold = new JLabel("Дата продажы: ");
        info_DateCalendar = new JLabel("дата");
        info_DateCalendar2 = new JLabel("дата");
        info_QuantityElements= new JLabel("Количество записей:");
        info_QuantityElements.setFont(new Font("Courier New", Font.PLAIN, 12));
        info_SumCost= new JLabel("Сума закупок:");
        info_SumCost.setFont(new Font("Courier New", Font.PLAIN, 12));
        info_SumCostSold= new JLabel("Сума продаж:");
        info_SumCostSold.setFont(new Font("Courier New", Font.PLAIN, 12));
        show_QuantityElements= new JLabel("200");
        show_SumCost= new JLabel("100");
        show_SumCostSold= new JLabel("320");
        show_Image = new JLabel();

        button_Search = new JButton("Поиск");
        button_Add= new JButton("Добавить");
        button_Change= new JButton("Изменить");
        button_Delete= new JButton("Удалить");
        button_nextImage= new JButton(">");
        button_prevImage= new JButton("<");

        text_Name = new JTextField(50);
        text_Size = new JTextField(10);
        text_Cost = new JTextField(10);
        text_CostSold = new JTextField(10);

        select_Available = new JComboBox(select_AvailableCriteria); select_Available.setEditable(false);
        select_Type = new JComboBox();  select_Type.setEditable(true);
        select_Color = new JComboBox(); select_Color.setEditable(true);
        select_Supplier = new JComboBox();  select_Supplier.setEditable(true);
        select_Cost_Criteria = new JComboBox(select_Criteria);
        select_CostSold_Criteria = new JComboBox(select_Criteria);
        select_DatePursache_Criteria = new JComboBox(select_Criteria_Date);
        select_DateSold_Criteria = new JComboBox(select_Criteria_Date);

        javaFxCalendarPursacheDate = new JavaFxCalendar();
        javaFxCalendarSoldDate = new JavaFxCalendar();



        gridLayout_SearchCriteria = new GridLayout(0,4);
        gridLayout_SearchCriteria.setHgap(30);
        gridLayout_SearchCriteria.setVgap(3);

        jPanel_SearchCriteria = new JPanel();
        jPanel_SearchCriteria.setBackground(new java.awt.Color(255, 255, 243));
        jPanel_SearchCriteria.setLayout(gridLayout_SearchCriteria);
        jPanel_SearchCriteria.setPreferredSize(new Dimension(700,244));
        jPanel_SearchCriteria.setBorder(BorderFactory.createTitledBorder("Критерии поиска"));
        jPanel_SearchCriteria.add(info_inStore);
        jPanel_SearchCriteria.add(select_Available);
        jPanel_SearchCriteria.add(info_Type);
        jPanel_SearchCriteria.add(select_Type);
        jPanel_SearchCriteria.add(info_Name);
        jPanel_SearchCriteria.add(text_Name);
        jPanel_SearchCriteria.add(info_Size);
        jPanel_SearchCriteria.add(text_Size);
        jPanel_SearchCriteria.add(info_Color);
        jPanel_SearchCriteria.add(select_Color);
        jPanel_SearchCriteria.add(info_Supplier);
        jPanel_SearchCriteria.add(select_Supplier);
        jPanel_SearchCriteria.add(info_Cost);
        jPanel_SearchCriteria.add(select_Cost_Criteria);
        jPanel_SearchCriteria.add(text_Cost);
        jPanel_SearchCriteria.add(info_grn);
        jPanel_SearchCriteria.add(info_CostSold);
        jPanel_SearchCriteria.add(select_CostSold_Criteria);
        jPanel_SearchCriteria.add(text_CostSold);
        jPanel_SearchCriteria.add(info_grn2);
        jPanel_SearchCriteria.add(info_DatePurchase);
        jPanel_SearchCriteria.add(select_DatePursache_Criteria);
        jPanel_SearchCriteria.add(javaFxCalendarPursacheDate.getFxPanel());
        jPanel_SearchCriteria.add(info_DateCalendar);
        jPanel_SearchCriteria.add(info_DateSold);
        jPanel_SearchCriteria.add(select_DateSold_Criteria);
        jPanel_SearchCriteria.add(javaFxCalendarSoldDate.getFxPanel());
        jPanel_SearchCriteria.add(info_DateCalendar2);
        jPanel_SearchCriteria.add(button_Search);

        jPanel_ManipulateDB = new JPanel(new FlowLayout());
        jPanel_ManipulateDB.setBorder(BorderFactory.createTitledBorder("Управление записями в базе"));
        jPanel_ManipulateDB.setBackground(new java.awt.Color(238, 232, 231));
        jPanel_ManipulateDB.add(button_Add);
        jPanel_ManipulateDB.add(button_Change);
        jPanel_ManipulateDB.add(button_Delete);

        jPanel_AggregateInformation = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
        jPanel_AggregateInformation.setBorder(BorderFactory.createTitledBorder("Статистика по базе"));
        jPanel_AggregateInformation.setBackground(new java.awt.Color(255, 255, 240));
        jPanel_AggregateInformation.add(info_QuantityElements);
        jPanel_AggregateInformation.add(show_QuantityElements);
        jPanel_AggregateInformation.add(info_SumCost);
        jPanel_AggregateInformation.add(show_SumCost);
        jPanel_AggregateInformation.add(info_SumCostSold);
        jPanel_AggregateInformation.add(show_SumCostSold);


        jPanel_SearchManipulateAggregate = new JPanel();
        jPanel_SearchManipulateAggregate.setLayout(new BoxLayout(jPanel_SearchManipulateAggregate,BoxLayout.PAGE_AXIS));
        jPanel_SearchManipulateAggregate.add(jPanel_SearchCriteria);
        jPanel_SearchManipulateAggregate.add(jPanel_ManipulateDB);
        jPanel_SearchManipulateAggregate.add(jPanel_AggregateInformation);

        jPanel_Image = new JPanel(new FlowLayout(FlowLayout.LEFT,10,0));
        jPanel_Image.setBorder(BorderFactory.createTitledBorder("Просмотр изображений"));
        jPanel_Image.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_Image.setPreferredSize(new Dimension(600,365));
        jPanel_Image.add(button_prevImage);
        jPanel_Image.add(button_nextImage);
        jPanel_Image.add(show_Image);

        jPanel_SearchManipulateAggregateImage = new JPanel();
        jPanel_SearchManipulateAggregateImage.add(jPanel_SearchManipulateAggregate);
        jPanel_SearchManipulateAggregateImage.add(jPanel_Image);


        // Set the view layout
        JPanel ctrlPane = new JPanel();
        ctrlPane.add(jPanel_SearchManipulateAggregateImage);
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setPreferredSize(new Dimension(1280, 312));
        tableScrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Перечень товаров", TitledBorder.CENTER, TitledBorder.TOP));

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, ctrlPane, tableScrollPane);
        splitPane.setDividerLocation(373);
        splitPane.setEnabled(false);

        // Display it all in a scrolling window and make the window appear
        frame = new JFrame("Магазин женской одежды \"Monro\"");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(splitPane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * The method used for showing icon on the jLabel.
     * After calling this method, the incoming byte's array will be transformed into the image.
     * Image will be scaled and transformed into the ImageIcon.
     * Then set ImageIcon to the specific jLabel.
     * Reset showed text on the jLabel
     * <p>
     * The method is invoked when we select row in the table and the appropriate entry has downloaded images.
     *
     * @param rawData byte representation of image that to be showed on the jLabel
     */
    public void displayImage(byte[] rawData)
    {
        int height = 340;
        Image im2 = new ImageIcon(rawData).getImage();
        ImageIcon icon = new ImageIcon(im2.getScaledInstance(-1, height, Image.SCALE_SMOOTH));
        show_Image.setText("");
        show_Image.setIcon(icon);
    }

    /**
     * This method shows empty icon on the jLabel. It is invoked when selected entry in the table is without any images
     */
    public void displayZeroImage()
    {
        show_Image.setText("Здесь отображаются изображения");
        show_Image.setIcon(null);
        show_Image.revalidate();
    }

    /*
        Standard getters and setters methods are below
     */

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JLabel getShow_QuantityElements() {
        return show_QuantityElements;
    }

    public void setShow_QuantityElements(JLabel show_QuantityElements) {
        this.show_QuantityElements = show_QuantityElements;
    }

    public JLabel getShow_SumCost() {
        return show_SumCost;
    }

    public void setShow_SumCost(JLabel show_SumCost) {
        this.show_SumCost = show_SumCost;
    }

    public JLabel getShow_SumCostSold() {
        return show_SumCostSold;
    }

    public void setShow_SumCostSold(JLabel show_SumCostSold) {
        this.show_SumCostSold = show_SumCostSold;
    }

    public JLabel getShow_Image() {
        return show_Image;
    }

    public void setShow_Image(JLabel show_Image) {
        this.show_Image = show_Image;
    }

    public JButton getButton_Search() {
        return button_Search;
    }

    public void setButton_Search(JButton button_Search) {
        this.button_Search = button_Search;
    }

    public JButton getButton_Add() {
        return button_Add;
    }

    public void setButton_Add(JButton button_Add) {
        this.button_Add = button_Add;
    }

    public JButton getButton_Change() {
        return button_Change;
    }

    public void setButton_Change(JButton button_Change) {
        this.button_Change = button_Change;
    }

    public JButton getButton_Delete() {
        return button_Delete;
    }

    public void setButton_Delete(JButton button_Delete) {
        this.button_Delete = button_Delete;
    }

    public JButton getButton_nextImage() {
        return button_nextImage;
    }

    public void setButton_nextImage(JButton button_nextImage) {
        this.button_nextImage = button_nextImage;
    }

    public JButton getButton_prevImage() {
        return button_prevImage;
    }

    public void setButton_prevImage(JButton button_prevImage) {
        this.button_prevImage = button_prevImage;
    }

    public JTextField getText_Name() {
        return text_Name;
    }

    public void setText_Name(JTextField text_Name) {
        this.text_Name = text_Name;
    }

    public JTextField getText_Size() {
        return text_Size;
    }

    public void setText_Size(JTextField text_Size) {
        this.text_Size = text_Size;
    }

    public JTextField getText_Cost() {
        return text_Cost;
    }

    public void setText_Cost(JTextField text_Cost) {
        this.text_Cost = text_Cost;
    }

    public JTextField getText_CostSold() {
        return text_CostSold;
    }

    public void setText_CostSold(JTextField text_CostSold) {
        this.text_CostSold = text_CostSold;
    }

    public JComboBox getSelect_Available() {
        return select_Available;
    }

    public void setSelect_Available(JComboBox select_Available) {
        this.select_Available = select_Available;
    }

    public JComboBox getSelect_Type() {
        return select_Type;
    }

    public void setSelect_Type(JComboBox select_Type) {
        this.select_Type = select_Type;
    }

    public JComboBox getSelect_Color() {
        return select_Color;
    }

    public void setSelect_Color(JComboBox select_Color) {
        this.select_Color = select_Color;
    }

    public JComboBox getSelect_Supplier() {
        return select_Supplier;
    }

    public void setSelect_Supplier(JComboBox select_Supplier) {
        this.select_Supplier = select_Supplier;
    }

    public JComboBox getSelect_Cost_Criteria() {
        return select_Cost_Criteria;
    }

    public void setSelect_Cost_Criteria(JComboBox select_Cost_Criteria) {
        this.select_Cost_Criteria = select_Cost_Criteria;
    }

    public JComboBox getSelect_CostSold_Criteria() {
        return select_CostSold_Criteria;
    }

    public void setSelect_CostSold_Criteria(JComboBox select_CostSold_Criteria) {
        this.select_CostSold_Criteria = select_CostSold_Criteria;
    }

    public JComboBox getSelect_DatePursache_Criteria() {
        return select_DatePursache_Criteria;
    }

    public void setSelect_DatePursache_Criteria(JComboBox select_DatePursache_Criteria) {
        this.select_DatePursache_Criteria = select_DatePursache_Criteria;
    }

    public JComboBox getSelect_DateSold_Criteria() {
        return select_DateSold_Criteria;
    }

    public void setSelect_DateSold_Criteria(JComboBox select_DateSold_Criteria) {
        this.select_DateSold_Criteria = select_DateSold_Criteria;
    }

    public JavaFxCalendar getJavaFxCalendarPursacheDate() {
        return javaFxCalendarPursacheDate;
    }

    public void setJavaFxCalendarPursacheDate(JavaFxCalendar javaFxCalendarPursacheDate) {
        this.javaFxCalendarPursacheDate = javaFxCalendarPursacheDate;
    }

    public JavaFxCalendar getJavaFxCalendarSoldDate() {
        return javaFxCalendarSoldDate;
    }

    public void setJavaFxCalendarSoldDate(JavaFxCalendar javaFxCalendarSoldDate) {
        this.javaFxCalendarSoldDate = javaFxCalendarSoldDate;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public JTable getTable()
    {return table;}

}

