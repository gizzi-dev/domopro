/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DomoPro;

import model.backyard.DBController;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Gianmarco
 */
public class DBControllerTest {
    
    public DBControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of executeQuery method, of class DBController.
     */
    @Test
    public void testExecuteQuery() {
        System.out.println("***************\nexecuteQuery");
        String query = "SELECT * FROM test WHERE 1";
        DBController instance = new DBController();
        ResultSet expResult = null;
        ResultSet result = instance.executeQuery(query);
        stampaResult(result);
        assertNotSame(expResult, result);       
        System.out.println("****************\n");
            // TODO review the generated test code and remove the default call to fail.
            //fail("The test case is a prototype.");
        
    }

    /**
     * Test of executeUpdate method, of class DBController.
     */
    @Test
    public void testExecuteUpdate() {        
        System.out.println("***************\nexecuteUpdate");
        String query = "UPDATE test SET nome='ProvaUpdate' WHERE id='5'";
        DBController instance = new DBController();
        instance.executeUpdate(query);
        System.out.println("***************\n");
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of executeInsert method, of class DBController.
     */
    @Test
    public void testExecuteInsert() {
        System.out.println("*************\nexecuteInsert");
        String query = "INSERT INTO `test`(`nome`) VALUES ('Prova')";
        DBController instance = new DBController();
        int expResult = -1;
        int result = instance.executeInsert(query);
        System.out.println("Ho inserito la riga di id="+result);
        assertNotSame(expResult, result); 
        System.out.println("***************\n");
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of openConnect method, of class DBController.
     */
    @Test
    public void testOpenConnect() {
        System.out.println("***************\nopenConnect");
        DBController instance = new DBController();
        instance.openConnect();       
        // TODO review the generated test code and remove the default call to fail.
       //fail("The test case is a prototype.");
        System.out.println("***************\n");
    }

    /**
     * Test of closeConnect method, of class DBController.
     */
    @Test
    public void testCloseConnect() {
        
        System.out.println("***************\ncloseConnect");
        DBController instance = new DBController();
        instance.closeConnect();
        System.out.println("***************\n");
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    public void stampaResult(ResultSet result){
        try {
            ResultSetMetaData rsmd = result.getMetaData();
            System.out.println("querying SELECT * FROM test");
            int columnsNumber = rsmd.getColumnCount();
            while (result.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(", ");
                    String columnValue = result.getString(i);
                    System.out.print(rsmd.getColumnName(i) + ": " + columnValue);
                }
                System.out.println("");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
