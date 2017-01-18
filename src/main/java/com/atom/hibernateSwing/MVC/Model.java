package com.atom.hibernateSwing.MVC;

import com.atom.hibernateSwing.model.Products;
import com.atom.hibernateSwing.model.Supplier;

import javax.swing.table.DefaultTableModel;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;




/**
 * <h1>Package com.atom.hibernateSwing.MVC</h1>
 *
 * This is the default table model for the table that shows the date from the database. This class consists of fields and methods. These
 * fields are useful for filling the comboBoxes of the {@link View}, {@link ViewAddFrame}, {@link ViewEditFrame}. Those
 * comboBoxes are <i>Цвет</i>, <i>Тип</i>, <i>Поставщик</i>.
 *
 * @author  atom Earth
 * @version 1.2
 * @since   2017-01-13
 *
 * @see ViewAddFrame
 * @see ViewEditFrame
 */
public class Model extends DefaultTableModel{

    /**
     * the result of the search data in database
     */
    List<Products> productsList;
    /**
     * fetching unique type from database
     */
    Set<Object> uniqueType;
    /**
     * fetching unique color from database
     */
    Set<Object> uniqueColor;
    /**
     * fetching unique supplier from database
     */
    Set<Supplier> uniqueSupplier;

    /**
     * Each object is the string for filling the cells of the View's table
     */
    public Object[][] dataFromDB;

    /**
     * The default constructor calls the parent constructor with no arguments
     */
    public Model(){
        super(Constants.DEFAULTDATA,Constants.TABLE_HEADER);
    }

    public List<Products> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Products> productsList) {
        this.productsList = productsList;
    }

    public Set<Object> getUniqueType() {
        return uniqueType;
    }

    public void setUniqueType(Set<Object> uniqueType) {
        this.uniqueType = uniqueType;
    }

    public Set<Object> getUniqueColor() {
        return uniqueColor;
    }

    public void setUniqueColor(Set<Object> uniqueColor) {
        this.uniqueColor = uniqueColor;
    }

    public Set<Supplier> getUniqueSupplier() {
        return uniqueSupplier;
    }

    public void setUniqueSupplier(Set<Supplier> uniqueSupplier) {
        this.uniqueSupplier = uniqueSupplier;
    }

    public Object[][] getDataFromDB() {
        return dataFromDB;
    }

    public void setDataFromDB(Object[][] dataFromDB) {
        this.dataFromDB = dataFromDB;
    }

    /**
     * This method is used to fill the array {@link #dataFromDB}. The number of columns is 11. The column's
     * sequence is following:
     * <ul>
     *     <li> Id
     *     <li> Available
     *     <li> Type
     *     <li> Name
     *     <li> Size
     *     <li> Color
     *     <li> Supplier's name
     *     <li> Cost
     *     <li> Sold
     *     <li> Purchase date
     *     <li> Sale date
     * </ul>
     * @param products all products fetching from database. These products are the result of the last request.
     */
    public void fillDataForTable(List<Products> products)
    {
        int numberColumns = 11;

        dataFromDB = new Object[products.size()][numberColumns];

        for (int i=0;i<products.size();i++)
        {
            // column ID
            dataFromDB[i][0] = products.get(i).getId();

            // column Available
            if(products.get(i).getAvailable() != null) {
                dataFromDB[i][1] = products.get(i).getAvailable();
            } else {
                dataFromDB[i][1] = " - ";
            }

            // column Type
            if(products.get(i).getType() != null){
                dataFromDB[i][2] = products.get(i).getType();
            } else {
                dataFromDB[i][2] = " -- ";
            }

            // column Name
            if(products.get(i).getName() != null) {
                dataFromDB[i][3] = products.get(i).getName();
            } else {
                dataFromDB[i][3] = " -- ";
            }

            // column Size
            if(products.get(i).getSize() != null) {
                dataFromDB[i][4] = products.get(i).getSize();
            } else {
                dataFromDB[i][4] = " - ";
            }

            // column Color
            if(products.get(i).getColor() != null) {
                dataFromDB[i][5] = products.get(i).getColor();
            } else {
                dataFromDB[i][5] = " -- ";
            }

            // column supplier name
            if(products.get(i).getSupplier() != null){
                dataFromDB[i][6] = products.get(i).getSupplier().getName();
            } else {
                dataFromDB[i][6] =" ---- ";}

            // columns about prices
            if(products.get(i).getPrice() != null){
                // column cost price
                dataFromDB[i][7] = products.get(i).getPrice().getCostPrice();
                // column sold price
                dataFromDB[i][8] = products.get(i).getPrice().getSoldPrice();
            } else {
                dataFromDB[i][7] = " -- ";
                dataFromDB[i][8] = " --";}

            // columns Times
            if (products.get(i).getTimes() != null) {
                // column purchase date
                if(products.get(i).getTimes().getPurchaseDate() != null) {
                    dataFromDB[i][9] = products.get(i).getTimes().getPurchaseDate();
                } else {
                    dataFromDB[i][9] = " ----- ";
                }

                // column sale date
                if(products.get(i).getTimes().getSaleDate() != null) {
                    dataFromDB[i][10] = products.get(i).getTimes().getSaleDate();
                } else {
                    dataFromDB[i][10] = " ---- ";
                }
            } else {
                dataFromDB[i][9] = " ----- ";
                dataFromDB[i][10] = " ----- ";
            }

        }
    }

    /**
     * This method is used to fill the fields {@link #uniqueType}, {@link #uniqueColor}.
     *
     * <p> The fields are {@link LinkedHashSet}. They are used to add items to the comboBoxes of the Views.
     * The first element of the each set is <b>"Все"</b>.
     *
     * @param products all products fetching from database. These products are the result of the last request.
     */
    public void fillDataForComboBoxes(List<Products> products)
    {
        if(uniqueType == null){
            uniqueType = new LinkedHashSet<Object>(); }
        else {
            uniqueType.clear();}

        if(uniqueColor == null) {
            uniqueColor = new LinkedHashSet<Object>(); }
        else {
            uniqueColor.clear();
        }

        uniqueType.add("Все");
        uniqueColor.add("Все");

        for (Products p:products) {
            if(p.getType() != null)
            {
                uniqueType.add(p.getType());
            }
            if(p.getColor() != null)
            {
                uniqueColor.add(p.getColor());
            }
        }

        if(products.size() > 0)
        {
            uniqueType.add(" ----- ");
            uniqueColor.add(" ----- ");
        }
    }

    /**
     * This method is used to fill the field {@link #uniqueSupplier}. The type of the field is {@link LinkedHashSet}.
     *
     * @param suppliers all suppliers fetched from the database during the last request.
     */
    public void fillDataForSupplierComboBox(List<Supplier> suppliers)
    {
        if(uniqueSupplier == null){
            uniqueSupplier = new LinkedHashSet<Supplier>();
        }
        else {
            uniqueSupplier.clear();
        }


        for (Supplier s:suppliers) {
            uniqueSupplier.add(s);
        }
    }

    /**
     * Set the rows of the table not editable. We can edit the row only by press the button <b>"Изменить"</b>
     *
     * @param row the table's row
     * @param column the table's column
     * @return false. Anybody can not change cells.
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        //all cells false
        return false;
    }

}

