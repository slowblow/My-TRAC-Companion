package MySQLHandlers;

import FileHandlers.FileDriver;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static java.lang.Thread.sleep;

public class MySQLDriver {

    //Create a table called tableName
    public static String createTable(Connection connection, String tableName) {
        String createTable = SQLQueryBuilder.createTable(tableName); //We will need to extend that to create a table based on the registered schema
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(createTable);
            return createTable;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return ("ERROR in SQL query: " + createTable);
        }
    }

    //Insert a record into the table tableName  with the form tableAttributes.
    public static Pair<String, String> Insert(Connection connection, String tableName, String tableAttributes, String[] record) {
        {
            String insert = SQLQueryBuilder.insert(tableName, tableAttributes, record); //We will need to extend that to adapt the inser to the table previously created
            String correct = "";
            String error = "";
            try {
                Statement statement = connection.createStatement();
                statement.executeUpdate(insert);
                correct = insert + "<br>";
            } catch (SQLException e) {
                System.out.println("Error in " + insert);
                error = insert + "<br>";
            }
            return new ImmutablePair<String, String>(correct, error);

        }
    }

    //Insert teh content of a file, into  tableName. timer ms is the time between insertions.
    public static String insertFileContent(Connection connection, String filename, String tableName, long timer) throws InterruptedException, IOException {
        StringBuilder inserts = new StringBuilder("Inserts:<br>");
        StringBuilder errors = new StringBuilder("");

        String tableAttributes= "(user_id, activity_id, rating)";

        Pair<StringBuilder,StringBuilder> InsertsErrors= new ImmutablePair<StringBuilder,StringBuilder>(inserts,errors);

        List<String[]> records = FileDriver.read(filename);
        for(String[] record : records) {

            Pair<String, String> InsertError = MySQLDriver.Insert(connection,tableName,tableAttributes,record);

            sleep(timer);
        }

        return errors.length()==0?inserts.toString():InsertsErrors.getKey().append("<br><br>Errors:<br>").append(InsertsErrors.getValue()).toString();

    }


}
