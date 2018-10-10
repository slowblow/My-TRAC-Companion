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
    public static String createTable(Connection connection, String topicName) throws SQLException {
        Statement statement = connection.createStatement();
        String createTable = SQLQueryBuilder.createTable(topicName); //We will need to extend that to create a table based on the registered schema
        statement.executeUpdate(createTable);

        return createTable;
    }

    public static Pair<String, String> Insert(Connection connection, String topicName, String header, String[] record) throws SQLException {
        {
            Statement statement = connection.createStatement();
            String insert = SQLQueryBuilder.insert(topicName, header, record); //We will need to extend that to adapt the inser to the table previously created
            String correct = "";
            String error = "";
            try {
                statement.executeUpdate(insert);
                correct = insert + "<br>";
            } catch (Exception e) {
                System.out.println("Error in " + insert);
                error = insert + "<br>";
            }
            return new ImmutablePair<String, String>(correct, error);

        }
    }

    public static String insertFileContent(Connection connection, String filename, String tableName, long timer) throws InterruptedException, IOException, SQLException {
        StringBuilder inserts = new StringBuilder("Inserts:<br>");
        StringBuilder errors = new StringBuilder("");

        String header= "(user_id, activity_id, rating)";

        Pair<StringBuilder,StringBuilder> InsertsErrors= new ImmutablePair<StringBuilder,StringBuilder>(inserts,errors);

        List<String[]> records = FileDriver.load(filename);
        for(String[] record : records) {

            Pair<String, String> InsertError = MySQLDriver.Insert(connection,tableName,header,record);

            sleep(timer);
        }

        return errors.length()==0?inserts.toString():InsertsErrors.getKey().append("<br><br>Errors:<br>").append(InsertsErrors.getValue()).toString();

    }


}
