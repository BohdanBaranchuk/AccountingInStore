package com.atom.hibernateSwing.util;

/**
 * Created by bb on 13.01.2017.
 */

import java.util.Properties;

import com.atom.hibernateSwing.model.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import org.apache.log4j.Logger;

/**
 * <h1>Package com.atom.hibernateSwing.util</h1>
 *
 * HibernateUtil class with the convenient methods to get Session Factory objects.
 *
 *
 * @author  atom Earth
 * @version 1.2
 * @since   2017-01-13
 *
 */
public class HibernateUtil {

    // create a logger Object
    static Logger logger = Logger.getLogger(HibernateUtil.class);

    /**
     * XML based configuration
     */
    private static SessionFactory sessionFactory;

    /**
     * Annotation based configuration
     */
    private static SessionFactory sessionAnnotationFactory;

    /**
     * Property based configuration
     */
    private static SessionFactory sessionJavaConfigFactory;

    /**
     * Create Session Factory from the standard config XML file
     *
     * @return SessionFactory
     * @throws ExceptionInInitializerError
     */
    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");

            //configuration.addAnnotatedClass(Employee1.class);

            logger.info("Hibernate Configuration loaded");

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            logger.info("Hibernate serviceRegistry created");

            SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            return sessionFactory;
        }
        catch (Throwable ex) {
            logger.error("Initial SessionFactory creation failed.");
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Create Session Factory using annotation based configuration. All needed annotation classes were added.
     *
     * @return SessionFactory
     * @throws ExceptionInInitializerError
     */
    private static SessionFactory buildSessionAnnotationFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration configuration = new Configuration();
            configuration.configure("hibernate-annotation.cfg.xml");

                    configuration.addAnnotatedClass(Products.class).
                    addAnnotatedClass(ImageWrapper.class).
                    addAnnotatedClass(Price.class).
                    addAnnotatedClass(Supplier.class).
                    addAnnotatedClass(ProductsDate.class).
                    addAnnotatedClass(ImageWrapper.class);

            //System.out.println("Hibernate Annotation Configuration loaded");
            logger.info("Hibernate Annotation Configuration loaded");

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            //System.out.println("Hibernate Annotation serviceRegistry created");
            logger.info("Hibernate Annotation serviceRegistry created");

            SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            return sessionFactory;
        }
        catch (Throwable ex) {
            logger.error("Initial SessionFactory creation failed.",ex);
            //System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Create Session Factory using property based configuration
     *
     *
     * @return SessionFactory
     * @throws ExceptionInInitializerError
     */
    private static SessionFactory buildSessionJavaConfigFactory() {
        try {
            Configuration configuration = new Configuration();

            //Create Properties, can be read from property files too
            Properties props = new Properties();
            props.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
            props.put("hibernate.connection.url", "jdbc:mysql://localhost/testdb");
            props.put("hibernate.connection.username", "postgres");
            props.put("hibernate.connection.password", "admin");
            props.put("hibernate.current_session_context_class", "thread");

            configuration.setProperties(props);

            configuration.addAnnotatedClass(ImageWrapper.class).
                    addAnnotatedClass(Products.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            //System.out.println("Hibernate Java Config serviceRegistry created");
            logger.info("Hibernate Java Config serviceRegistry created");

            SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            return sessionFactory;
        }
        catch (Throwable ex) {
            logger.error("Initial SessionFactory creation failed.",ex);
            //System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Get existing Session Factory or create new if it isn't exist.
     *
     * @return SessionFactory
     */
    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null) sessionFactory = buildSessionFactory();
        return sessionFactory;
    }

    /**
     * Get existing Session Factory or create new if it isn't exist.
     *
     * @return SessionFactory
     */
    public static SessionFactory getSessionAnnotationFactory() {
        if(sessionAnnotationFactory == null) sessionAnnotationFactory = buildSessionAnnotationFactory();
        return sessionAnnotationFactory;
    }

    /**
     * Get existing Session Factory or create new if it isn't exist.
     *
     * @return SessionFactory
     */
    public static SessionFactory getSessionJavaConfigFactory() {
        if(sessionJavaConfigFactory == null) sessionJavaConfigFactory = buildSessionJavaConfigFactory();
        return sessionJavaConfigFactory;
    }

}
