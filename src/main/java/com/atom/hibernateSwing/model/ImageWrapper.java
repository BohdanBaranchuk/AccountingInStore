package com.atom.hibernateSwing.model;

import org.omg.CORBA.Object;
import java.awt.*;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;
import javax.swing.*;

/**
 * The POJO class in Hibernate. This class is the wrapper for the goal of saving the image in the database. The Id of
 * the each image will be unique.
 */
@Entity
@Table(name = "Images", uniqueConstraints={@UniqueConstraint(columnNames={"ID"})})
public class ImageWrapper {

    /**
     * The unique identifier of the each image. Used its own sequence for the generation the Id with the allocationSize
     * equals to 1.
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "imagewraper_seq_gen")
    @SequenceGenerator(name = "imagewraper_seq_gen",sequenceName = "IMAGEWRAPER_SEQ",allocationSize = 1)
    @Column(name="ID", nullable=false, unique=true)
    private long id;

    /**
     * The name of the saving image.
     */
    @Column(name="name", length=40, nullable=true)
    private String name;

    /**
     * The byte representation of the image. Large data is saved in BLOB data type. The columns in the database has the name "image".
     */
    @Lob
    @Column(name="image",  nullable=false,length = 10000000)
    private byte[] data;

    /**
     * One product can have few images.
     */
    @ManyToOne
    private Products products;

    public long getId()
    {return id;}

    public void setId(long id)
    {this.id=id;}

    public String getName()
    {return name;}

    public void setName(String name)
    {this.name = name;}

    public byte[] getData()
    {return data;}

    public void setData(byte[] data)
    {this.data = data;}

    public Products getProducts()
    {return products;}

    public void setProducts(Products products)
    {this.products = products;}

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }

    public boolean equals(Object o)
    {
        if(this == o)
            return true;
        if(!(o instanceof ImageWrapper))
            return false;
        ImageWrapper imageWrapper = (ImageWrapper) o;
        return Objects.equals(id,imageWrapper.id);
    }
}
