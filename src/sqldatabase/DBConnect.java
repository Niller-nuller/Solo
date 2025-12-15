package sqldatabase;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnect {
    //Our method to get a connection to our sql server.
    //The url, user and password is located in the .env file.
    public static Connection getConnection() {
        Properties prop = new Properties();
        try(FileInputStream inputStream = new FileInputStream(".env")){
            prop.load(inputStream);
        } catch (Exception e){
            System.out.println("failed to load .env file:");
            e.printStackTrace();
            return null;
        }
//-----Here we use String variables, to set our url, user and password attributes.//------------
//-----We use the prop.getProperty to "read" where the correspondent line fits within the .env dokument.---
        String url = prop.getProperty("url");
        String user = prop.getProperty("user");
        String password = prop.getProperty("password");

        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url, user, password);
        } catch(SQLException e){
            System.out.println("failed to connect to database:");
            e.printStackTrace();
        }
        return conn;
    }
}