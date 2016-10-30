
package testapp;


import java.sql.SQLException;
import java.text.ParseException;


/**
 *
 * @author Alexey Yelizarenko
 */
public class TestApp {

  
    public static void main(String[] args) throws ParseException {
        
        try {
            MenuSelecter.selectMenuAction();
        } catch (ClassNotFoundException ex) {
           
        } catch (SQLException ex) {
           
        }
           
       
    }
    
}
