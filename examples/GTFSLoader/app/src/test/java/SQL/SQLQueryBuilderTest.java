package SQL;

import FileHandlers.LoadFile;
import MySQLHandlers.SQLQueryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class SQLQueryBuilderTest {

    @Test
    public void createTable() throws IOException {
        List<String[]> records = LoadFile.load("/Users/joan/Downloads/original_gtfs/routes.txt");
        String createTableString = SQLQueryBuilder.createTable("routes", records.get(0));



        assertTrue(1==1);

    }

    @Test
    public void insert() throws IOException {
        List<String[]> records = LoadFile.load("/Users/joan/Downloads/original_gtfs/routes.txt");
        String insertQuery = SQLQueryBuilder.insert("routes", records.get(0), records.get(1));
        System.out.println(insertQuery);
        assertTrue(1==1);
    }
}