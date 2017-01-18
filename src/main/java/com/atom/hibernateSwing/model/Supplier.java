package com.atom.hibernateSwing.model;

import org.hibernate.annotations.NaturalId;
import org.omg.CORBA.Object;

import javax.persistence.*;
import java.util.Objects;


/**
 * The POJO class in Hibernate. This class saves the information about the supplier of the product.
 *
 */
@Entity(name="SUPPLIER")
public class Supplier {

    /**
     * Unique identifier of the supplier.
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "supplier_seq_gen")
    @SequenceGenerator(name = "supplier_seq_gen",sequenceName = "SUPPLIER_SEQ",allocationSize = 1)
    @Column(name="ID", nullable=false, unique=true)
    private Long id;

    /**
     * The name of the supplier.
     */
    @NaturalId
    @Column(name="NAME")
    private String name;

    /**
     * The contact information.
     */
    @Column(name="CONTACTS")
    private String contacts;

    /**
     * The URL address of the supplier.
     */
    @Column(name="URL")
    private String urlAdress;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getUrlAdress() {
        return urlAdress;
    }

    public void setUrlAdress(String urlAdress) {
        this.urlAdress = urlAdress;
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }

    public boolean equals(Object o)
    {
        if(this == o)
            return true;
        if(!(o instanceof Supplier))
            return false;
        Supplier supplier = (Supplier) o;
        return Objects.equals(id,supplier.id);
    }

    @Override
    public String toString()
    {
        StringBuilder printString = new StringBuilder();
        printString.append("Название магазина - "+ getName()+"\n");
        if(getContacts() != null)
            printString.append("Контакты - "+ getContacts()+"\n");
        if(getUrlAdress() != null)
            printString.append("Адрес сайта - "+ getUrlAdress()+"\n");

        return printString.toString();
    }
}

