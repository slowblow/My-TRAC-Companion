import FileHandlers.FileDriver;
import MySQLHandlers.MySQLDriver;
import MySQLHandlers.SQLConnection;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import org.apache.commons.lang3.tuple.Pair;
import spark.Request;
import org.apache.commons.lang3.tuple.ImmutablePair;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.io.*;
import java.nio.file.*;

import static java.lang.Thread.sleep;
import static spark.Spark.*;


/*
 * This app creates an API REST to upload a CSV File. Then, the CSV file is processed
 * and it's content is upload into a remote MySQL Database.
 */


public class CSVToKafkaTopic {


    //THIS PART OF THE CODE HELPS HANDLING THE ARGUMENTS REQUIRED BY THE APPLICATION
    public static class Arguments {


        @Parameter(names = {"-db", "--database"}, description = "Database name", required = false)
        public String database;

        @Parameter(names = {"-ip", "--ip"}, description = "MySQL server ip", required = false)
        public String ip;

        @Parameter(names = {"-u", "--user","--username"}, description = "DB username", required = false)
        public String user = "confluent";


        @Parameter(names = {"-pw", "--password"}, description = "DB password", required = false)
        public String pw = "confluent";

        @Parameter(names={"-f","--file"}, description="Full path to the GTFS file to be loaded", required= false)
        public String filename="";


        @Parameter(names={"-F","--folder"}, description="Full path to the folder containing the GTFS files to be loaded", required= false)
        public String folderName="";

        @Parameter(names = "--help,-help", help = true)
        private boolean help;

    }



    private static final String uploadDirPath="upload";
    private static File uploadDir;
    static long timer;


    /* This code represents a form with
        - uploaded_file, a field to be filled with the file to be uploaded
        - tipic_name, a text field which indicates to which topic the file belongs to
        - selected_option, a two option selecto to choose between a bulk/stream way of uploading the file
     */
    static String web_page_code="<form method='post' enctype='multipart/form-data'>" // note the enctype
            + "    <input type='file' name='uploaded_file' accept='.csv'><br>"
            + "    <input type='text' name='topic_name' value='Choose topic name'>         "// make sure to call getPart using the same "name" in the post
            + "<select name='selected_option'>\n"
            +"  <option value=\"stream\" selected>Stream</option>\n"
            +"  <option value=\"bulk\">Bulk</option>\n"
            +"</select><br><br>"
            + "                                         <button>Upload CSV</button>"
            + "</form>";


    private static void prepare() {
        port(4568);
        uploadDir = new File(uploadDirPath);
        uploadDir.mkdir(); // create the upload directory if it doesn't exist
        staticFiles.externalLocation(uploadDirPath);
    }

    static    Connection connection = null;

    static Arguments arguments = new Arguments();
    public static void main (String[] args) throws IOException, InterruptedException {

        //Handle the program arguments
        JCommander.newBuilder().addObject(arguments).build().parse(args);

        //Function to prepare the system to receive the files to be uploaded.
        prepare();


        // It creates a "web page" to upload a file (get) and then it handles it (post)
        get("/", (req, res) ->
               web_page_code
        );

        //It handles the uploaded file
        post("/", (req, res) -> {
            String result = handleFile(req);
            return result;
        });

    }

    private static String handleFile(Request req) throws IOException, SQLException, InterruptedException {
        Path pathToFile = Files.createTempFile(uploadDir.toPath(), "", "");

        //Retrive filename
        String rawFilename= FileDriver.getFileName(pathToFile,req);

        //Retrieve topicName
        String topicName=req.queryParams("topic_name");

        //Retrive uploaded mode: either bulk o stream
        String selectedOption = req.queryParams("selected_option");
        timer = selectedOption.equals("stream")?5000:0;


        System.out.println("File name: "+ rawFilename+" will be "+selectedOption+"ed ; topic name: "+ topicName );


        File f = pathToFile.toFile();

        String result = writeFileContentToMySQL(topicName,f.getAbsolutePath());
        Files.delete(pathToFile);
        return "<h1>Results:</h1>"+result;
    }


    //Writes the content of the file into a table called tableName of the database
    private static String writeFileContentToMySQL(String tableName, String filename) throws IOException, InterruptedException {

        //Connect with the database
        connection = SQLConnection.getConnection(arguments.ip,arguments.database,arguments.user,arguments.pw);
        System.out.println("Connection to MYSQL established");



        //Creates the table
        String createdTable = MySQLDriver.createTable(connection,tableName);

        //Insert records into the table
        String InsertsErrors= MySQLDriver.insertFileContent(connection,filename,tableName,timer);


        return "Table:<br>"+createdTable+"<br><br>"+InsertsErrors;


    }
}
