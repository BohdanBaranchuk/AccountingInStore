package com.atom.hibernateSwing.dao;

import com.atom.hibernateSwing.ProjectEnums.Available;
import com.atom.hibernateSwing.model.Price;
import com.atom.hibernateSwing.model.Products;
import com.atom.hibernateSwing.model.Supplier;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * <h1>Package com.atom.hibernateSwing.dao</h1>
 *
 * This interface contains all the CRUD methods to interact with database.
 *
 *
 * @author  atom Earth
 * @version 1.2
 * @since   2017-01-13
 *
 */
public interface ProductsDAO {

    public Session openCurrentSession();
    public Session openCurrentSessionWithTransaction();
    public void closeCurrentSession();
    public void closeCurrentSessionWithTransaction();
    public void closeCurrentSessionWithRollback();

    public void begintransactionCurrentSession();
    public void clear();
    public void flush();
    public void commitCurrentSession();
    public void rollback();

    public void addProduct(Products products) throws SQLException;
    public void saveOrUpdate(Products products) throws SQLException;
    public void updateProduct(Products products) throws SQLException;
    public void replicateProduct(Products products) throws SQLException;
    public void deleteProduct(Products products) throws SQLException;
    public void deleteProductById(long id) throws SQLException;

    public Products getProductById(long product_id) throws SQLException;
    // criteria
    public List<Products> getAllProducts_criteria() throws SQLException;
    public List<Supplier> getUniqueSupplier() throws SQLException;
    // HQL
    public List<Products> getAllProducts_HQL() throws SQLException;
    public List<Products> getProducts_HQL_OnlyType(String type) throws SQLException;
    public List<Products> getProducts_HQL_OnlyAvailable(Available available) throws SQLException;
    public List<Products> getProducts_HQL_OnlyName(String name) throws SQLException;
    public List<Products> getProducts_HQL_OnlyColor(String color) throws SQLException;
    public List<Products> getProducts_HQL_custom(StringBuilder resultLimitations) throws SQLException;
    public List<Products> getProducts_HQL_Timestamp(String color) throws SQLException;
    // SQL
    public List<Products> getAllProducts_SQL() throws SQLException;

}

