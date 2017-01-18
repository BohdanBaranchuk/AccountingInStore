package com.atom.hibernateSwing.model;

import org.hibernate.annotations.NaturalId;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 * The POJO class in Hibernate. This class saves the information about the purchase date, sale date and timestamp.
 * It is embeddable type.
 */
@Embeddable
public class ProductsDate {

    @Column(name="PURCHASEDATE", length=40, nullable=true)
    @Temporal(TemporalType.DATE)
    private Date purchaseDate;

    @Column(name="SALEDATE", length=40, nullable=true)
    @Temporal(TemporalType.DATE)
    private Date saleDate;

    /**
     * Timestamp is natural identifier.
     */
    @NaturalId
    @Column(name="TIMESTAMP", length=40, nullable=true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp = new Date();

    public void setPurchaseDate(Date date)
    {purchaseDate  =date;}

    public Date getPurchaseDate()
    {return purchaseDate;}

    public void setSaleDate(Date date)
    { saleDate=date;   }

    public Date getSaleDate()
    {return saleDate;}


    public void setTimestamp(Date date)
    {timestamp=date;}

    public Date getTimestamp()
    {return  timestamp;}
}

