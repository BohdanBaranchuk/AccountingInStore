package com.atom.hibernateSwing.model;

import org.omg.CORBA.Object;
import javax.persistence.*;
import java.util.Objects;


/**
 * The POJO class in Hibernate. This class saves the information about the cost of the each product.
 */
@Entity(name="PRICE")
public class Price {

    /**
     * The unique identifier of the each Price. It uses its own sequence for the generation the Id with the allocationSize
     * equals to 1.
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "price_seq_gen")
    @SequenceGenerator(name = "price_seq_gen",sequenceName = "PRICE_SEQ",allocationSize = 1)
    @Column(name="ID", nullable=false, unique=true)
    private long id;

    @Column(name="COST")
    private int costPrice;

    @Column(name="REQUESTCOST")
    private int requestPrice;

    @Column(name="SOLDCOST")
    private int soldPrice;

    public long getId()
    {return id;}

    public void setId(long id)
    {this.id=id;}

    public int getCostPrice()
    {return costPrice;}

    public void setCostPrice(int costPrice)
    {this.costPrice = costPrice;}

    public int getRequestPrice()
    {return requestPrice;}

    public void setRequestPrice(int requestPrice)
    {this.requestPrice = requestPrice;}

    public int getSoldPrice() {
        return soldPrice;
    }

    public void setSoldPrice(int soldPrice) {
        this.soldPrice = soldPrice;
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }

    public boolean equals(Object o)
    {
        if(this == o)
            return true;
        if(!(o instanceof Price))
            return false;
        Price price = (Price) o;
        return Objects.equals(id,price.id);
    }
}
