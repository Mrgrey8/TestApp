
package testapp;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;


/**
 *
 * @author Aleksey Yelizarenko
 */
public class MenuSelecter {
    
    //This method opens MENU 
    public static void selectMenuAction() throws ClassNotFoundException, SQLException{
        System.out.println("WELCOME!");
        System.out.println();
        
        System.out.println("Please, select your action: ");
        System.out.println();
        System.out.println("If You want to add new task, please enter '1'; ");
        System.out.println();
        System.out.println("If You want to check list of tasks, please enter '2'; ");
        System.out.println();
        System.out.println("If You want to EXIT, please enter '0'; ");
        System.out.println();
        
        
        Scanner scanner = new Scanner(System.in);
        
        
        switch(scanner.next()){
            case "1":     
                JDBCConnector.addTask();
                MenuSelecter.selectMenuAction();
            break;
            case "2": 
            try {
                JDBCConnector.allTasksProsessor();
            } catch (ParseException ex) {
                
            }
            break;
            case "0": System.out.println("Good Bye!");
            break;
        }
          
    }
    
}
