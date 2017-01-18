package com.atom.hibernateSwing.dao;

import com.atom.hibernateSwing.ProjectEnums.Available;
import com.atom.hibernateSwing.model.ImageWrapper;
import com.atom.hibernateSwing.model.Products;
import com.atom.hibernateSwing.model.Supplier;
import com.atom.hibernateSwing.util.HibernateUtil;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.*;

import javax.persistence.TemporalType;
import javax.persistence.criteria.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * <h1>Package com.atom.hibernateSwing.dao</h1>
 *
 * This class is an implementation of the interface {@link ProductsDAO}.
 *
 *
 *
 * @author  atom Earth
 * @version 1.2
 * @since   2017-01-13
 *
 */
public class ProductsDAOImpl implements ProductsDAO {

    /**
     * the current active session
     */
    private Session session;

    /**
     * the active SessionFactory
     */
    private SessionFactory sessionFactory;


    /**
     * Just get the current session
     * @return current session
     */
    public Session openCurrentSession(){
        sessionFactory = HibernateUtil.getSessionAnnotationFactory();
        session = sessionFactory.getCurrentSession();
        return session;
    }

    /**
     * Get the current session and begin the transaction
     * @return current session
     */
    public Session openCurrentSessionWithTransaction(){
        sessionFactory = HibernateUtil.getSessionAnnotationFactory();
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        return session;
    }


    /**
     * Just close the current session.
     */
    public void closeCurrentSession(){
        HibernateUtil.getSessionAnnotationFactory().close();
    }

    /**
     * Commit the current session and close it.
     */
    public void closeCurrentSessionWithTransaction(){
        session.getTransaction().commit();
        HibernateUtil.getSessionAnnotationFactory().close();
    }

    /**
     * Rollback and close the current session.
     */
    public void closeCurrentSessionWithRollback(){
        session.getTransaction().rollback();
        HibernateUtil.getSessionAnnotationFactory().close();
    }

    /**
     * Just begin the transaction.
     */
    public void begintransactionCurrentSession(){ session.beginTransaction();}

    /**
     * Clear the session. Remove all managed entities from L1 cache.
     */
    public void clear() {
        session.clear();
    }

    /**
     * Flush the session.
     */
    public void flush() { session.flush(); }

    /**
     * Commit the session and begin the transaction again.
     */
    public void commitCurrentSession(){
        session.getTransaction().commit();
        sessionFactory = HibernateUtil.getSessionAnnotationFactory();
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
    }

    /**
     * Rollback the session and begin the transaction again.
     */
    public void rollback() {
        session.getTransaction().rollback();
        sessionFactory = HibernateUtil.getSessionAnnotationFactory();
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
    }


    /**
     * Persist the instance.
     *
     * @param products the product that we want to save
     * @throws SQLException if something was wrong
     */
    public void addProduct(Products products) throws SQLException
    {
        session.save(products);
    }

    /**
     * Either save(Object) or update(Object) the given instance.
     *
     * @param products the product that we want to save or update
     * @throws SQLException if something was wrong
     */
    public void saveOrUpdate(Products products) throws SQLException
    {
        session.saveOrUpdate(products);
    }

    /**
     * Update the persistent instance with the identifier of the given detached instance.
     *
     * @param products the product that we want to update
     * @throws SQLException if something was wrong
     */
    public void updateProduct(Products products) throws SQLException
    {
        session.update(products);
    }

    /**
     * Persist the state of the given detached instance, reusing the current identifier value with the ReplicationMode OVERWRITE.
     * You must set the {@link com.atom.hibernateSwing.model.Products#id} and the {@link com.atom.hibernateSwing.model.Products#version}
     * before.
     *
     * @param products the product that we want to replicate
     * @throws SQLException if something was wrong
     */
    public void replicateProduct(Products products) throws SQLException
    {
        session.replicate(products, ReplicationMode.OVERWRITE);
    }

    /**
     * Remove a persistent instance from the dataStore.
     *
     * @param products the product that we want to delete
     * @throws SQLException if something was wrong
     */
    public void deleteProduct(Products products) throws SQLException
    {
        session.delete(products);
    }

    /**
     * Remove a persistent instance from the dataStore using its id.
     *
     * @param id the product's id that we want to delete
     * @throws SQLException if something was wrong
     */
    public void deleteProductById(long id) throws SQLException
    {
        Products products = null;
        products = (Products) session.get(Products.class,id);
        session.delete(products);
    }

    /**
     * Get the product from database using explicit Id.
     *
     * @param product_id the id of the product, that we want to get
     * @return the fetched product from the database
     * @throws SQLException if something was wrong
     */
    public Products getProductById(long product_id) throws SQLException
    {
        Products products=null;
        products = (Products) session.load(Products.class,product_id);
        return products;
    }

    /**
     * Get all products from the database using Criteria.
     *
     * @return fetched products
     * @throws SQLException if something was wrong
     */
    public List<Products> getAllProducts_criteria() throws SQLException
    {
        List<Products> products = new ArrayList<Products>();
        products = session.createCriteria(Products.class).list();
        return products;
    }

    /**
     * Get all suppliers from the database using Criteria.
     *
     * @return list of the suppliers
     * @throws SQLException if something was wrong
     */
    public List<Supplier> getUniqueSupplier() throws SQLException
    {
        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<Supplier> criteria = builder.createQuery( Supplier.class );
        Root<Supplier> root = criteria.from( Supplier.class );
        criteria.select( root );

        List<Supplier> suppliers =session.createQuery( criteria ).getResultList();
        return suppliers;
    }

    /**
     * Get all products from the database using HQL requests.
     *
     * @return fetched products
     * @throws SQLException if something was wrong
     */
    @SuppressWarnings("unchecked")
    public List<Products> getAllProducts_HQL() throws SQLException
    {
        List<Products> products = new ArrayList<Products>();
        products = session.createQuery("FROM Products p").list();
        return products;
    }

    /**
     * Get all products with the explicit type from the database using HQL requests.
     *
     * @param type the type of the products what we want to get from the database
     * @return fetched products that satisfy the condition
     * @throws SQLException if something was wrong
     */
    public List<Products> getProducts_HQL_OnlyType(String type) throws SQLException
    {
        List<Products> products = new ArrayList<Products>();
        products = session.createQuery("FROM Products p WHERE p.type='"+type+"'").list();
        return products;
    }

    /**
     * Get all products with the explicit available status from the database using HQL requests.
     *
     * @param available the availability status of the product what we want to get
     * @return fetched products that satisfy the condition
     * @throws SQLException if something was wrong
     */
    public List<Products> getProducts_HQL_OnlyAvailable(Available available) throws SQLException
    {
        List<Products> products = new ArrayList<Products>();
        products = session.createQuery("FROM Products p WHERE p.available='"+available+"' GROUP BY p.name ORDER BY p.times.timestamp").list();
        return products;
    }

    /**
     * Get all products with the explicit name from the database using HQL requests.
     *
     * @param name the name of the product what we want to get from the database
     * @return fetched products that satisfy the condition
     * @throws SQLException if something was wrong
     */
    public List<Products> getProducts_HQL_OnlyName(String name) throws SQLException
    {
        String hql = "FROM Products p WHERE p.name = :name";
        Query query = session.createQuery(hql);
        query.setParameter("name",name);
        List<Products> results = query.list();
        return results;
    }

    /**
     * Get all products with the explicit color from the database using HQL requests.
     *
     * @param color the color of the products what we want to get from the database
     * @return fetched products that satisfy the condition
     * @throws SQLException if something was wrong
     */
    public List<Products> getProducts_HQL_OnlyColor(String color) throws SQLException
    {
        List<Products> products = new ArrayList<Products>();
        products = session.createQuery("FROM Products p WHERE p.color='"+color+"'").list();
        return products;
    }

    /**
     * Get all products from the database using the HQL request from the parameter.
     *
     * @param resultLimitations the full HQL request
     * @return fetched products that satisfy the condition from the incoming HQL request
     * @throws SQLException if something was wrong
     */
    public List<Products> getProducts_HQL_custom(StringBuilder resultLimitations) throws SQLException
    {
        System.out.println("before = "+resultLimitations.length());
        System.out.println(resultLimitations.toString());
        resultLimitations.insert(0,"FROM Products p ");
        List<Products> products;
        products = session.createQuery(resultLimitations.toString()).list();
        return products;
    }

    /**
     * For future use.
     *
     * @param timeParam timestamp in string representation.
     * @return fetched products that satisfy the condition
     * @throws SQLException if something was wrong
     */
    public List<Products> getProducts_HQL_Timestamp(String timeParam) throws SQLException
    {
        List<Products> products = new ArrayList<Products>();
        products = session.createQuery("FROM Products p WHERE p.times.timestamp<'"+timeParam +"'").list();
        return products;
    }

    /**
     * Get all products from the database using SQL requests.
     *
     * @return all fetched products
     * @throws SQLException if something was wrong
     */
    public List<Products> getAllProducts_SQL() throws SQLException
    {
        List<Products> products = new ArrayList<Products>();
        products = session.createSQLQuery("SELECT * FROM products").list();
        return products;
    }
}

