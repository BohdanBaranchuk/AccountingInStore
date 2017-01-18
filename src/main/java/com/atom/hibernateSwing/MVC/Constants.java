package com.atom.hibernateSwing.MVC;

/**
 * <h1>Package com.atom.hibernateSwing.MVC</h1>
 *
 * This class contains the static fields. They are the names of the table's header and the default data for the table.
 *
 *
 * @author  atom Earth
 * @version 1.2
 * @since   2017-01-13
 *
 * @see View
 */
public class Constants {

    /**
     * The names of the table's header in the View
     */
    public static final Object[] TABLE_HEADER =
            {
                    "Id","Наличие","Тип","Название","Розмер","Цвет","Магазин","Цена закупки","Цена продажы","Дата покупки","Дата продажы"
            };

    //public static Object[][] DataFromDB;    // all data getted from DB
    //public static Object[] DataType;        // product's type for comboBox
    //public static Object[] DataColour;      // product's colour for comboBox
    //public static Object[] DataSupplier;    // product's supplier for comboBox

    /**
     * The default data when application first started
     */
    public static final Object[][] DEFAULTDATA =
            {
                    {"-"," - "," - "," -- "," -- "," -- "," -- "," - "," - "," ----- "," ----- "}

            };
}

