package MySQLHandlers;

import FileHandlers.FileDriver;
import Objects.Schema;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static java.lang.Thread.getAllStackTraces;
import static java.lang.Thread.sleep;

public class MySQLDriver {

    //Create a table called following the defined schema
    public static String createTable(Connection connection, Schema schema) {
        String createTable = SQLQueryBuilder.createTable(schema);
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

    //Insert the content of a file, into  tableName. timer ms is the time between insertions.
    public static String insertFileContent(Connection connection, String filename, Schema schema, long timer) throws InterruptedException, IOException {
        StringBuilder inserts = new StringBuilder("");
        StringBuilder errors = new StringBuilder("");


        String tableName=schema.getName();

        boolean first = true;
        StringBuilder tableAttributes = new StringBuilder();
        tableAttributes.append("(");

        for (Schema.Field field : schema.getFields())
        {
            String attribute_name = field.getName();
            if(attribute_name.equals("id")) continue;

            if(!first) tableAttributes.append(",");
            tableAttributes.append(" "+attribute_name);

            first=false;
        }
        tableAttributes.append(")");


        List<String[]> records = FileDriver.read(filename);
        for(String[] record : records) {

            Pair<String, String> InsertError = MySQLDriver.Insert(connection,tableName,tableAttributes.toString(),record);
            inserts.append(InsertError.getLeft());
            errors.append(InsertError.getRight());

            sleep(timer);
        }

        return errors.length()==0?"<br><br>Inserts:<br>"+inserts.toString():"<br><br>Inserts:<br>"+inserts.toString()+("<br><br>Errors:<br>")+errors.toString();

    }


}
