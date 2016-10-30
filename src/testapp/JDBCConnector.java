
package testapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Aleksey Yelizarenko
 */
public class JDBCConnector {
    
    //This methos adds new task into tasks list
    public static void addTask() throws ClassNotFoundException, SQLException {
        
        String priority = "";
        Scanner scanner = new Scanner(System.in);
     
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taskdb","root", "");
        Statement stmt = conn.createStatement();
    
        System.out.println("Please enter task name: ");  
        String taskName = scanner.nextLine();
        System.out.println("Your task name is: " + taskName);
        System.out.println();
        
        System.out.println("Please enter date terms in next format: yyyy-MM-dd ");
        String terms = scanner.nextLine();
        System.out.println("Your task terms are till: " + terms);
        System.out.println();
        System.out.println("Please enter task priority: ");
        System.out.println();
        System.out.println("If task priority is LOW, enter '1' ");
        System.out.println();
        System.out.println("If task priority is MEDIUM, enter '2' ");
        System.out.println();
        System.out.println("If task priority is HIGH, enter '3' ");
  
        
        Integer priorityNumber = scanner.nextInt();
        
        switch (priorityNumber) {
            case 1:
                priority = "LOW";
                break;
            case 2:
                priority = "MEDIUM";
                break;
            case 3:
                priority = "HIGH";
                break;
            default:
                System.out.println("Your Number is Incorrect!");
        }
        System.out.println("This is your task priority: " + priority);
        System.out.println();
        
        stmt.executeUpdate("INSERT INTO active_tasks (task_name, terms, priority) VALUES ('" + taskName + "', '"+ terms + "', '" + priority + "');");
        conn.close();
    }
    
    //This method prosess al Tasks in diferent ways as we need
    public static void allTasksProsessor()throws ClassNotFoundException, SQLException, ParseException{
        
         Class.forName("com.mysql.jdbc.Driver");
         Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taskdb","root", "");
         Statement selectTasksList = conn.createStatement();
         
         System.out.println("All Tasks List: ");
         System.out.println();
         
         ResultSet result = selectTasksList.executeQuery("SELECT id, task_name, terms, priority, status FROM active_tasks;");
        
         while(result.next()){
             
             Integer id = result.getInt("id");
             String taskName = result.getString("task_name");
             String terms = result.getString("terms");
             String priority = result.getString("priority");
             String status = result.getString("status");
             
             System.out.println("Task name: " + taskName + ", terms due to: " + terms + ", priority: " + priority + ", STATUS: " + status);
             System.out.println();
             
             String dateFromServer = terms;
             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
             Date dateOfTaskTerm = sdf.parse(dateFromServer);
        
             Date curDate = new Date();
             
             boolean check = curDate.before(dateOfTaskTerm);
        
             String curentDate = new SimpleDateFormat("yyyy-MM-dd").format(curDate);
             SimpleDateFormat cur = new SimpleDateFormat("yyyy-MM-dd");
             Date todayDate = cur.parse(curentDate);
       
             if(check == true || dateOfTaskTerm.equals(todayDate)){
                 
                 if (status.equals("DONE")){
                     
                 System.out.println("This task is already DONE!");
                 System.out.println();
                 
             }else{
                     
             System.out.println("If You want to note your task as DONE, please enter 'DONE'! ");
             System.out.println();
             System.out.println("Either, please enter '0' if your task is IN PROCESS! ");
             System.out.println();
             
             Scanner scanner = new Scanner(System.in);
             String checkText = scanner.next();
     
             if(checkText.equals("DONE")){
            
             Statement noteDoneTasks = conn.createStatement();
             noteDoneTasks.executeUpdate("UPDATE active_tasks SET status = 'DONE' WHERE id = " + id + ";");    
             noteDoneTasks.executeUpdate("INSERT INTO finished_tasks (finished_tasks_name) VALUES ('" + taskName + "');");
             System.out.println("Your task is DONE! Very Good! ");
             System.out.println();
             }else if (checkText.equals("0")){
                 
                 System.out.println("This task is already IN PROCESS!");
                 System.out.println();
             }
                 }          
             } else {
                 
             System.out.println(" This task is out-of-date!");
             System.out.println();
             Statement noteOutOfDateTasks = conn.createStatement();
             noteOutOfDateTasks.executeUpdate("UPDATE active_tasks SET status = 'OUT-OF-DATE' WHERE id = " + id + ";");
             }      
         }
         
         
         System.out.println("If You want to select all DONE tasks, please enter '1' ");
         System.out.println();
         System.out.println("If You want Exit to MENU, please enter '0' ");
         System.out.println();
         
         Scanner scanner = new Scanner(System.in);
         String choose = scanner.next();
         
         if(choose.equals("1")){
             
           ResultSet showAllFinishedTasks = selectTasksList.executeQuery("SELECT id, finished_tasks_name FROM finished_tasks;"); 
           System.out.println("LIST OF FINISHED TASKS: ");
           System.out.println();
           
            while(showAllFinishedTasks.next()){
             
             String finishedTaskName = showAllFinishedTasks.getString("finished_tasks_name");  
             System.out.println("Finished Task name: " + finishedTaskName + "");
             System.out.println();
            }
             MenuSelecter.selectMenuAction();
             
         }else if(choose.equals("0")){
             
             MenuSelecter.selectMenuAction();
         }
        conn.close();
    }
    
}
