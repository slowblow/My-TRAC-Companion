package MySQLHandlers;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class SQLConnection {

    public static Connection getConnection(String ip, String database, String user, String pw) throws InterruptedException {

        System.out.println("-------- MySQL JDBC Connection Testing ------------");
        System.out.println("Connecting to...jdbc:mysql://"+ip+"/"+database);
        Connection connection = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return connection;
        }
        System.out.println("MySQL JDBC Driver Registered!");

        while(connection == null) {
            try {
                String url = "jdbc:mysql://"+ip+"/"+database+","+user+","+pw;
                connection = DriverManager
                        .getConnection("jdbc:mysql://"+ip+"/"+database,user, pw);

            } catch (SQLException e) {

                System.out.println("Connection Failed! Check output console");
                System.out.println("IP: "+ip+", database: "+database+", user: "+user+", pw: "+pw);

                e.printStackTrace();
                Thread.sleep(1000);
            }
        }

        System.out.println("You made it, take control your database now!");
        return connection;
    }
}
