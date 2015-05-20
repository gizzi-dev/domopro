package model.backyard;
import java.sql.Connection;



import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gianmarco
 */
public class DBController {
    private final String url="jdbc:mysql://localhost:3306/domopro";
    private final String username="root";
    private final String password="";
    private Connection con;
    
       
    public ResultSet executeQuery(String query){
        try {
            openConnect();
            Statement st = con.createStatement();  
            ResultSet rs = st.executeQuery(query);
            return rs;
        } catch (SQLException ex) {
            System.err.println("errore nell'execute :"+query);
        }  
        return null;
    }
    
    public void executeUpdate(String query){
        try {
            openConnect();
            Statement st = con.createStatement();
            st.executeUpdate(query);
            closeConnect();
        } catch (SQLException ex) {
            System.err.println("Errore nell'update: "+query+"\n"+ex);
        } 
    }
    
    public int executeInsert(String query){        
        try {
            openConnect();
            Statement st = con.createStatement();
            st.executeUpdate(query);
            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()){
                closeConnect();
                return rs.getInt(1); 
            } 
            closeConnect();
            return -1;
        } catch (SQLException ex) {
            return -1;
        }        
    }
    
    public void openConnect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.con = DriverManager.getConnection(url,username,password);              
        }
        catch (ClassNotFoundException | SQLException ex) {
            System.err.println("Impossibile Connettersi al DB!\n"+ex);
        }
        
    }
    
    public void closeConnect(){
        try {
            con.close();
        } catch (SQLException |NullPointerException ex) {
            System.out.println("Nessuna connessione!\n"+ex);
        }
    }
    
    public void stampaResult(ResultSet result){
        try {
            ResultSetMetaData rsmd = result.getMetaData();            
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
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
}

