package com.atom.hibernateSwing.model;

import com.atom.hibernateSwing.ProjectEnums.Available;
import org.hibernate.annotations.*;
import org.hibernate.engine.transaction.jta.platform.internal.SunOneJtaPlatform;
import org.omg.CORBA.Object;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.swing.*;




/**
 * This is the main POJO class. It saves full information about the product.
 *
 */
@Entity
@Table(name = "Products",uniqueConstraints={@UniqueConstraint(columnNames={"ID"})})
public class Products {

    /**
     * Unique identifier of the product.
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "products_seq_gen")
    @SequenceGenerator(name = "products_seq_gen",sequenceName = "PRODUCTS_SEQ", allocationSize = 1)
    @Column(name="ID", nullable=false, unique=true)
    private long id;

    /**
     * For optimistic locking.
     */
    @Version
    private Integer version;

    /**
     * The type of the product.
     */
    @Column(name="TYPE", length=20)
    private String type;

    /**
     * The name of the product.
     */
    @NaturalId
    @Column(name="NAME", length=20)
    private String name;

    /**
     * The product's size.
     */
    @Column(name="SIZE", length=20)
    private String size;

    /**
     * The product's size.
     */
    @Column(name="COLOR", length=20)
    private String color;

    /**
     * The product's fabric.
     */
    @Column(name ="COMPONENTS",length = 60)
    private String components;

    /**
     * The availability of product in the store.
     */
    @Enumerated(EnumType.STRING)
    @Column(name="Available")
    private Available available;

    /**
     * Time information about the purchase date, sale date and timestamp when the entry was made.
     */
    @Embedded
    private ProductsDate times;

    /**
     * All images of the product (up to 5 items).
     */
    // Bidirectional
    @OneToMany(mappedBy = "products", cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ImageWrapper> images = new ArrayList<ImageWrapper>();

    /**
     * The cost and the gain for the product.
     */
    // Unidirectional
    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "PRICE_ID")
    private Price price;

    /**
     * The product's supplier.
     */
    // Unidirectional
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.DETACH})
    @JoinColumn(name = "SUPPLIER_ID",foreignKey = @ForeignKey(name = "SUPPLIER_ID_FK"))
    private Supplier supplier;

    /**
     * Service information about the product. For future use.
     */
    @Transient
    private String serviceData;

    /**
     * The product's description.
     */
    @ElementCollection
    private List<String> description = new ArrayList<String>();


    public void setDescription(List<String> description)
    {this.description = description;}

    public List<String> getDescription() {
        return description;
    }

    public long getId()
    {return id;}

    public void setId(long id)
    {this.id = id;}

    public Integer getVersion()
    {return version;}

    public void setVersion(Integer version)
    {this.version = version;}

    public String getType()
    {return this.type;}

    public void setType(String type)
    {this.type = type;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getComponents()
    {return components;}

    public void setComponents(String components)
    {this.components = components;}

    public Available getAvailable() {
        return available;
    }

    public void setAvailable(Available available) {
        this.available = available;
    }

    public void setTimes(ProductsDate productsDate)
    {times=productsDate;}

    public ProductsDate getTimes()
    {return times;}

    public List<ImageWrapper> getImages()
    {return images;}

    public void addImage(ImageWrapper imageWrapper)
    {
        images.add(imageWrapper);
        imageWrapper.setProducts(this);
    }

    public void removeImage(ImageWrapper imageWrapper)
    {
        images.remove(imageWrapper);
        imageWrapper.setProducts(null);
    }

    public void setPrice(Price price)
    {this.price=price;}

    public Price getPrice()
    {return price;}

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public int hashCode(){
        return Objects.hash(name);
    }

    public boolean equals(Object o)
    {
        if(this == o)
            return true;
        if(!(o instanceof Products))
            return false;
        Products products = (Products) o;
        return Objects.equals(name,products.getName());
    }


    @Override
    public String toString()
    {
        StringBuilder printString = new StringBuilder();

        printString.append(" **************** \n");

        printString.append("Найменование товара: "+ getName()+"\n");

        if (getType() != null)
            printString.append("Тип товара: "+ getType()+"\n");

        if (getAvailable() == Available.Yes)
            printString.append("Наличие: Да \n");
        else
            printString.append("Наличие: Нет \n");

        if (getSize() != null)
            printString.append("Розмер: "+ getSize()+"\n");

        if (getColor() != null)
            printString.append("Цвет: "+ getColor()+"\n");

        if (getSupplier() != null)
            printString.append("Магазин закупки: "+ getSupplier().toString()+"\n");

        if (getPrice() != null) {
            printString.append("Цена закупки: " + getPrice().getCostPrice() + "\n");
            printString.append("Цена продажы: " + getPrice().getSoldPrice() + "\n");
        }

        printString.append("Дата занесения в базу: " + getTimes().getTimestamp() + "\n");
        printString.append("Дата закупки: " + getTimes().getPurchaseDate() + "\n");
        if(getAvailable() == Available.No)
            printString.append("Дата продажы: " + getTimes().getSaleDate() + "\n");

        if(getImages() != null)
            printString.append("Количество изображений: " + getImages().size() + "\n");
        printString.append(" ********************* \n");


        return  printString.toString();
    }















}

