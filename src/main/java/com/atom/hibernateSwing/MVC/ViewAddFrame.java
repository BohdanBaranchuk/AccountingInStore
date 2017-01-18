package com.atom.hibernateSwing.MVC;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;


/**
 * <h1>Package com.atom.hibernateSwing.MVC</h1>
 * Form is showing when we want to add new entry in the database
 * <p>
 * We will set all needed parameters, add images and then press the button "Добавить". If all parameters are correct the
 * new entry will be added, else we get error message box. If want to discard changes than press the button "Отмена".
 * <p>
 * This form is child form of the main View form. When press close button the form will be disposed.
 *
 *
 * @author  atom Earth
 * @version 1.2
 * @since   2017-01-13
 *
 * @see View
 * @see ViewEditFrame
 */
public class ViewAddFrame {

    JFrame frame;

    JLabel info_Name;
    JLabel info_Type;
    JLabel info_Size;
    JLabel info_Color;
    JLabel info_Cost;
    JLabel info_CostSold;
    JLabel info_DatePurchase;
    JLabel info_Description;
    JLabel info_SupplierName;
    JLabel info_SupplierContacts;
    JLabel info_SupplierURL;
    JLabel info_ImageNum1;
    JLabel info_ImageNum2;
    JLabel info_ImageNum3;
    JLabel info_ImageNum4;
    JLabel info_ImageNum5;
    JLabel show_ImageAdr1;
    JLabel show_ImageAdr2;
    JLabel show_ImageAdr3;
    JLabel show_ImageAdr4;
    JLabel show_ImageAdr5;

    JButton button_ImageAdd1;
    JButton button_ImageAdd2;
    JButton button_ImageAdd3;
    JButton button_ImageAdd4;
    JButton button_ImageAdd5;
    JButton button_ImageDel1;
    JButton button_ImageDel2;
    JButton button_ImageDel3;
    JButton button_ImageDel4;
    JButton button_ImageDel5;
    JButton button_Add;
    JButton button_Cancel;

    JTextField text_Name;
    JTextField text_Size;
    JTextField text_Cost;
    JTextField text_CostSold;
    JTextField text_SupplierContacts;
    JTextField text_SupplierURL;
    JTextField text_Description;

    JComboBox select_Type;
    JComboBox select_Color;
    JComboBox select_Supplier;

    JavaFxCalendar javaFxCalendarPurchaseDate;



    JPanel jPanel_infoAboutProduct;             // JPanel for elements describes the product
    JPanel jPanel_infoAboutSuppler;             // JPanel for elements describes the supplier
    JPanel jPanel_infoAboutImage;               // JPanel for selecting the Images
    JPanel jPanel_ctrButton;                    // JPanel for OK and Cancel buttons
    JPanel jPanel_main;                         // JPanel for all other JPanels

    /**
     * The single class constructor. Create swing UI components, add them to the JPanels, combining JPanels and add
     * them to the JFrame. Initialize components, create JPanel, add components to them.
     */
    public ViewAddFrame()
    {

        // Create views swing UI components
        info_Name = new JLabel("Название: ");
        info_Type = new JLabel("Тип: ");
        info_Size = new JLabel("Розмер: ");
        info_Color = new JLabel("Цвет: ");
        info_Cost = new JLabel("Цена закупки: ");
        info_CostSold = new JLabel("Цена продажы ");
        info_DatePurchase = new JLabel("Дата покупки: ");
        info_Description = new JLabel("Описание: ");
        info_SupplierName = new JLabel("Магазин закупки: ");
        info_SupplierContacts = new JLabel("Контакты: ");
        info_SupplierURL = new JLabel("Інтернет адрес: ");
        info_ImageNum1 = new JLabel("1. ");
        info_ImageNum2 = new JLabel("2. ");
        info_ImageNum3 = new JLabel("3. ");
        info_ImageNum4 = new JLabel("4. ");
        info_ImageNum5 = new JLabel("5. ");
        show_ImageAdr1 = new JLabel("Изображение не выбрано ....");
        show_ImageAdr2 = new JLabel("Изображение не выбрано ....");
        show_ImageAdr3 = new JLabel("Изображение не выбрано ....");
        show_ImageAdr4 = new JLabel("Изображение не выбрано ....");
        show_ImageAdr5 = new JLabel("Изображение не выбрано ....");

        button_ImageAdd1 = new JButton("Добавить/Изменить");
        button_ImageAdd2 = new JButton("Добавить/Изменить");
        button_ImageAdd3 = new JButton("Добавить/Изменить");
        button_ImageAdd4 = new JButton("Добавить/Изменить");
        button_ImageAdd5 = new JButton("Добавить/Изменить");
        button_ImageDel1 = new JButton("Удалить");
        button_ImageDel2 = new JButton("Удалить");
        button_ImageDel3 = new JButton("Удалить");
        button_ImageDel4 = new JButton("Удалить");
        button_ImageDel5 = new JButton("Удалить");
        button_Add = new JButton("Добавить");
        button_Cancel = new JButton("Отмена");

        text_Name = new JTextField(50);
        text_Size = new JTextField(10);
        text_Cost = new JTextField(10);
        text_CostSold = new JTextField(10);
        text_SupplierContacts = new JTextField(50);
        text_SupplierURL  = new JTextField(50);
        text_Description = new JTextField(200);

        select_Type = new JComboBox();  select_Type.setEditable(true);
        select_Color = new JComboBox(); select_Color.setEditable(true);
        select_Supplier = new JComboBox();  select_Supplier.setEditable(true);

        javaFxCalendarPurchaseDate = new JavaFxCalendar();

        GridLayout gridLayout_infoAboutProduct = new GridLayout(0,4);
        gridLayout_infoAboutProduct.setHgap(30);
        gridLayout_infoAboutProduct.setVgap(3);

        jPanel_infoAboutProduct = new JPanel();
        jPanel_infoAboutProduct.setLayout(gridLayout_infoAboutProduct);
        //jPanel_infoAboutProduct.setBackground(new java.awt.Color(255, 255, 224));
        jPanel_infoAboutProduct.setBorder(BorderFactory.createTitledBorder("Информация про продукт"));
        jPanel_infoAboutProduct.add(info_Name);
        jPanel_infoAboutProduct.add(text_Name);
        jPanel_infoAboutProduct.add(info_Type);
        jPanel_infoAboutProduct.add(select_Type);
        jPanel_infoAboutProduct.add(info_Size);
        jPanel_infoAboutProduct.add(text_Size);
        jPanel_infoAboutProduct.add(info_Color);
        jPanel_infoAboutProduct.add(select_Color);
        jPanel_infoAboutProduct.add(info_Cost);
        jPanel_infoAboutProduct.add(text_Cost);
        jPanel_infoAboutProduct.add(info_CostSold);
        jPanel_infoAboutProduct.add(text_CostSold);
        jPanel_infoAboutProduct.add(info_DatePurchase);
        jPanel_infoAboutProduct.add(javaFxCalendarPurchaseDate.getFxPanel());
        jPanel_infoAboutProduct.add(info_Description);
        jPanel_infoAboutProduct.add(text_Description);

        jPanel_infoAboutSuppler = new JPanel();
        jPanel_infoAboutSuppler.setLayout(gridLayout_infoAboutProduct);
        jPanel_infoAboutSuppler.setBackground(new java.awt.Color(255, 255, 224));
        jPanel_infoAboutSuppler.setBorder(BorderFactory.createTitledBorder("Информация про магазин закупки"));
        jPanel_infoAboutSuppler.add(info_SupplierName);
        jPanel_infoAboutSuppler.add(select_Supplier);
        jPanel_infoAboutSuppler.add(info_SupplierContacts);
        jPanel_infoAboutSuppler.add(text_SupplierContacts);
        jPanel_infoAboutSuppler.add(info_SupplierURL);
        jPanel_infoAboutSuppler.add(text_SupplierURL);



        jPanel_infoAboutImage = new JPanel(new GridBagLayout());
        jPanel_infoAboutImage.setBorder(BorderFactory.createTitledBorder("Добавление изображений"));
        //jPanel_infoAboutImage.setBackground(new java.awt.Color(255, 255, 224));
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;c.gridy = 0;
        jPanel_infoAboutImage.add(info_ImageNum1, c);
        c.gridx = 1;c.gridy = 0;c.weightx = 2;
        jPanel_infoAboutImage.add(show_ImageAdr1, c);
        c.gridx = 2;c.gridy = 0;c.weightx = 0;
        jPanel_infoAboutImage.add(button_ImageAdd1, c);
        c.gridx = 3;c.gridy = 0;c.weightx = 0;
        jPanel_infoAboutImage.add(button_ImageDel1, c);

        c.gridx = 0;c.gridy = 1;
        jPanel_infoAboutImage.add(info_ImageNum2, c);
        c.gridx = 1;c.gridy = 1;c.weightx = 2;
        jPanel_infoAboutImage.add(show_ImageAdr2, c);
        c.gridx = 2;c.gridy = 1;c.weightx = 0;
        jPanel_infoAboutImage.add(button_ImageAdd2, c);
        c.gridx = 3;c.gridy = 1;c.weightx = 0;
        jPanel_infoAboutImage.add(button_ImageDel2, c);

        c.gridx = 0;c.gridy = 2;
        jPanel_infoAboutImage.add(info_ImageNum3, c);
        c.gridx = 1;c.gridy = 2;c.weightx = 2;
        jPanel_infoAboutImage.add(show_ImageAdr3, c);
        c.gridx = 2;c.gridy = 2;c.weightx = 0;
        jPanel_infoAboutImage.add(button_ImageAdd3, c);
        c.gridx = 3;c.gridy = 2;c.weightx = 0;
        jPanel_infoAboutImage.add(button_ImageDel3, c);

        c.gridx = 0;c.gridy = 3;
        jPanel_infoAboutImage.add(info_ImageNum4, c);
        c.gridx = 1;c.gridy = 3;c.weightx = 2;
        jPanel_infoAboutImage.add(show_ImageAdr4, c);
        c.gridx = 2;c.gridy = 3;c.weightx = 0;
        jPanel_infoAboutImage.add(button_ImageAdd4, c);
        c.gridx = 3;c.gridy = 3;c.weightx = 0;
        jPanel_infoAboutImage.add(button_ImageDel4, c);

        c.gridx = 0;c.gridy = 4;
        jPanel_infoAboutImage.add(info_ImageNum5, c);
        c.gridx = 1;c.gridy = 4;c.weightx = 2;
        jPanel_infoAboutImage.add(show_ImageAdr5, c);
        c.gridx = 2;c.gridy = 4;c.weightx = 0;
        jPanel_infoAboutImage.add(button_ImageAdd5, c);
        c.gridx = 3;c.gridy = 4;c.weightx = 0;
        jPanel_infoAboutImage.add(button_ImageDel5, c);



        jPanel_ctrButton = new JPanel(new FlowLayout());
        //jPanel_AggregateInformation.setBorder(BorderFactory.createTitledBorder("Статистика по базе"));
        //jPanel_AggregateInformation.setBackground(new java.awt.Color(255, 255, 240));
        jPanel_ctrButton.add(button_Add);
        jPanel_ctrButton.add(button_Cancel);


        jPanel_main = new JPanel();
        jPanel_main.setLayout(new BoxLayout(jPanel_main,BoxLayout.PAGE_AXIS));
        jPanel_main.add(jPanel_infoAboutProduct);
        jPanel_main.add(jPanel_infoAboutSuppler);
        jPanel_main.add(jPanel_infoAboutImage);
        jPanel_main.add(jPanel_ctrButton);
        jPanel_main.setPreferredSize(new Dimension(900,400));


        // Display it all in a scrolling window and make the window appear

        frame = new JFrame("Добавить новый продукт");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(jPanel_main);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JLabel getShow_ImageAdr1() {
        return show_ImageAdr1;
    }

    public void setShow_ImageAdr1(JLabel show_ImageAdr1) {
        this.show_ImageAdr1 = show_ImageAdr1;
    }

    public JLabel getShow_ImageAdr2() {
        return show_ImageAdr2;
    }

    public void setShow_ImageAdr2(JLabel show_ImageAdr2) {
        this.show_ImageAdr2 = show_ImageAdr2;
    }

    public JLabel getShow_ImageAdr3() {
        return show_ImageAdr3;
    }

    public void setShow_ImageAdr3(JLabel show_ImageAdr3) {
        this.show_ImageAdr3 = show_ImageAdr3;
    }

    public JLabel getShow_ImageAdr4() {
        return show_ImageAdr4;
    }

    public void setShow_ImageAdr4(JLabel show_ImageAdr4) {
        this.show_ImageAdr4 = show_ImageAdr4;
    }

    public JLabel getShow_ImageAdr5() {
        return show_ImageAdr5;
    }

    public void setShow_ImageAdr5(JLabel show_ImageAdr5) {
        this.show_ImageAdr5 = show_ImageAdr5;
    }

    public JButton getButton_ImageAdd1() {
        return button_ImageAdd1;
    }

    public void setButton_ImageAdd1(JButton button_ImageAdd1) {
        this.button_ImageAdd1 = button_ImageAdd1;
    }

    public JButton getButton_ImageAdd2() {
        return button_ImageAdd2;
    }

    public void setButton_ImageAdd2(JButton button_ImageAdd2) {
        this.button_ImageAdd2 = button_ImageAdd2;
    }

    public JButton getButton_ImageAdd3() {
        return button_ImageAdd3;
    }

    public void setButton_ImageAdd3(JButton button_ImageAdd3) {
        this.button_ImageAdd3 = button_ImageAdd3;
    }

    public JButton getButton_ImageAdd4() {
        return button_ImageAdd4;
    }

    public void setButton_ImageAdd4(JButton button_ImageAdd4) {
        this.button_ImageAdd4 = button_ImageAdd4;
    }

    public JButton getButton_ImageAdd5() {
        return button_ImageAdd5;
    }

    public void setButton_ImageAdd5(JButton button_ImageAdd5) {
        this.button_ImageAdd5 = button_ImageAdd5;
    }

    public JButton getButton_ImageDel1() {
        return button_ImageDel1;
    }

    public void setButton_ImageDel1(JButton button_ImageDel1) {
        this.button_ImageDel1 = button_ImageDel1;
    }

    public JButton getButton_ImageDel2() {
        return button_ImageDel2;
    }

    public void setButton_ImageDel2(JButton button_ImageDel2) {
        this.button_ImageDel2 = button_ImageDel2;
    }

    public JButton getButton_ImageDel3() {
        return button_ImageDel3;
    }

    public void setButton_ImageDel3(JButton button_ImageDel3) {
        this.button_ImageDel3 = button_ImageDel3;
    }

    public JButton getButton_ImageDel4() {
        return button_ImageDel4;
    }

    public void setButton_ImageDel4(JButton button_ImageDel4) {
        this.button_ImageDel4 = button_ImageDel4;
    }

    public JButton getButton_ImageDel5() {
        return button_ImageDel5;
    }

    public void setButton_ImageDel5(JButton button_ImageDel5) {
        this.button_ImageDel5 = button_ImageDel5;
    }

    public JButton getButton_Add() {
        return button_Add;
    }

    public void setButton_Add(JButton button_Add) {
        this.button_Add = button_Add;
    }

    public JButton getButton_Cancel() {
        return button_Cancel;
    }

    public void setButton_Cancel(JButton button_Cancel) {
        this.button_Cancel = button_Cancel;
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

    public JTextField getText_SupplierContacts() {
        return text_SupplierContacts;
    }

    public void setText_SupplierContacts(JTextField text_SupplierContacts) {
        this.text_SupplierContacts = text_SupplierContacts;
    }

    public JTextField getText_SupplierURL() {
        return text_SupplierURL;
    }

    public void setText_SupplierURL(JTextField text_SupplierURL) {
        this.text_SupplierURL = text_SupplierURL;
    }

    public JTextField getText_Description() {
        return text_Description;
    }

    public void setText_Description(JTextField text_Description) {
        this.text_Description = text_Description;
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

    public JavaFxCalendar getJavaFxCalendarPurchaseDate() {
        return javaFxCalendarPurchaseDate;
    }

    public void setJavaFxCalendarPurchaseDate(JavaFxCalendar javaFxCalendarPurchaseDate) {
        this.javaFxCalendarPurchaseDate = javaFxCalendarPurchaseDate;
    }

    public JPanel getjPanel_infoAboutProduct() {
        return jPanel_infoAboutProduct;
    }

    public void setjPanel_infoAboutProduct(JPanel jPanel_infoAboutProduct) {
        this.jPanel_infoAboutProduct = jPanel_infoAboutProduct;
    }

    public JPanel getjPanel_infoAboutSuppler() {
        return jPanel_infoAboutSuppler;
    }

    public void setjPanel_infoAboutSuppler(JPanel jPanel_infoAboutSuppler) {
        this.jPanel_infoAboutSuppler = jPanel_infoAboutSuppler;
    }

    public JPanel getjPanel_infoAboutImage() {
        return jPanel_infoAboutImage;
    }

    public void setjPanel_infoAboutImage(JPanel jPanel_infoAboutImage) {
        this.jPanel_infoAboutImage = jPanel_infoAboutImage;
    }

    public JPanel getjPanel_ctrButton() {
        return jPanel_ctrButton;
    }

    public void setjPanel_ctrButton(JPanel jPanel_ctrButton) {
        this.jPanel_ctrButton = jPanel_ctrButton;
    }

    public JPanel getjPanel_main() {
        return jPanel_main;
    }

    public void setjPanel_main(JPanel jPanel_main) {
        this.jPanel_main = jPanel_main;
    }
}
