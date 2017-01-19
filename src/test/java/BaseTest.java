import com.atom.hibernateSwing.dao.*;
import com.atom.hibernateSwing.model.*;
import com.atom.hibernateSwing.ProjectEnums.*;
import com.atom.hibernateSwing.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.*;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * JUnit tests. Test the base functionality of the Hibernate CRUD operations.
 */
public class BaseTest {

    Session session;
    SessionFactory sessionFactory;

    @Rule
    public final Timeout timeout = new Timeout(20000);

    @Before
    public void initialize()
    {
        sessionFactory = HibernateUtil.getSessionAnnotationFactory();
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
    }


    @Test (timeout = 18000)
    public void createGoodAndSaveIt()
    {
        String nameGood = "Baraban31";        // change the name for each iteration
        String nameSupplier = "Rozetka31";    // change the name for each iteration
        long prId = 1031;                     // change the ID for each iteration

        Products myPr = new Products();

        myPr.setId(prId);
        myPr.setName(nameGood);
        myPr.setSize("XXL");
        myPr.setColor("Red");
        myPr.setComponents("шелк 50%, хлопок 20%б синтетика 30%");
        myPr.setAvailable(Available.Yes);
        myPr.setTimes(new ProductsDate());
        myPr.getDescription().add("Единичный екземпляр");

        myPr.addImage(createImage("C:\\test.png"));

        Price price =createPrice();
        myPr.setPrice(price);

        Supplier supplier = createSupplier(nameSupplier);
        myPr.setSupplier(supplier);


        session.save(supplier);
        session.save(price);
        session.save(myPr);


        List<Products> products = session.createCriteria(Products.class).list();
        Products readPr;

        // the assertions are following below
        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertTrue("bit products",products.size()>10);

        // receive the product from the database
        readPr = products.get(products.size()-1);

        // the assertion are following below
        assertNotNull(readPr);
        assertEquals(readPr,myPr);


        session.getTransaction().commit();

    }

    private Supplier createSupplier(String name)
    {
        Supplier supplier = new Supplier();
        supplier.setName(name);
        supplier.setContacts("Kiev, Vladimira Vinnichenko Str.,14");
        supplier.setUrlAdress("http://musthave.ua");

        return supplier;
    }

    private Price createPrice()
    {
        Price price = new Price();
        price.setCostPrice(200);
        price.setRequestPrice(300);
        price.setSoldPrice(290);

        return price;
    }

    private ImageWrapper createImage(String path)
    {
        ImageWrapper imageWrapper = new ImageWrapper();

        File file = new File(path);
        byte[] imageData = new byte[(int) file.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(imageData);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        imageWrapper.setName("FrontView.jpeg");
        imageWrapper.setData(imageData);
        return imageWrapper;
    }


    @Ignore
    @Test (expected = IllegalStateException.class)
    public void tryToRead()
    {
        List<Products> products = session.createCriteria(Products.class).list();
    }


    @After
    public void closeConnection()
    {
        session.close();
        sessionFactory.close();
    }


    /*
    @Ignore
    @Test(timeout = 1000)
    public void infinity() {
        while (true);
    }

    @Test(expected = NumberFormatException.class)
    public void testToHexStringWrong() {
        Integer.parseInt(null);
    }

    */
}
