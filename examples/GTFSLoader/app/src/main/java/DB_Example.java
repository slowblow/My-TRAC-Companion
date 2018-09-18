import FileHandlers.LoadFile;
import MySQLHandlers.SQLConnection;
import MySQLHandlers.SQLQueryBuilder;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.lang.Thread;

import javax.servlet.*;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.*;
import static spark.Spark.*;


public class DB_Example {

    public static class Arguments {

        @Parameter(names = {"-db", "--database"}, description = "Database name", required = true)
        public String database;

        @Parameter(names = {"-ip", "--ip"}, description = "MySQL server ip", required = true)
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


    static    Connection connection = null;
    public static void main (String[] args) throws IOException, InterruptedException {


        File uploadDir = new File("upload");
        uploadDir.mkdir(); // create the upload directory if it doesn't exist

        staticFiles.externalLocation("upload");


        Arguments arguments = new Arguments();


        JCommander.newBuilder().addObject(arguments).build().parse(args);
        String ip= arguments.ip;
        String database = arguments.database;
        String user = arguments.user;
        String pw = arguments.pw;

     /*   ip="192.168.99.100";
        database="connect_test";
    */

       System.out.println(
              "ip: "+ip+"\n"+
              "database: "+database+"\n"+
              "user: "+user+"\n"+
              "pw: "+pw+"\n"
       );


       while(connection == null) {
         try{
           connection = SQLConnection.getConnection(ip,database,user,pw);
         } catch(Exception e) {
           e.printStackTrace();
           Thread.sleep(1000);
         }
       }
       System.out.println("Connection to MYSQL established");

        get("/", (req, res) ->
                "<form method='post' enctype='multipart/form-data'>" // note the enctype
                        + "    <input type='file' name='uploaded_file' accept='.txt'>" // make sure to call getPart using the same "name" in the post
                        + "    <button>Upload GTFS file</button>"
                        + "</form>"
        );

        post("/", (req, res) -> {

            Path tempFile = Files.createTempFile(uploadDir.toPath(), "", "");

            req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

            String rawFilename="";
            try (InputStream input = req.raw().getPart("uploaded_file").getInputStream()) { // getPart needs to use same "name" as input field in form
                Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
                rawFilename=getFileName(req.raw().getPart("uploaded_file"));
            }

            catch (Exception e )
            {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }


            //logInfo(req, tempFile);
            File f = tempFile.toFile();





            String result = processFile(rawFilename,f.getAbsolutePath());


            Files.delete(tempFile);
            return "<h1>Results:</h1>"+result;
        });



/*

        Arguments arguments = new Arguments();
        try{

            JCommander.newBuilder().addObject(arguments).build().parse(args);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return;
        }

        if(arguments.filename.isEmpty()&&arguments.folderName.isEmpty())
        {
            System.out.println("Either the filename [-f] or the folder [-F] containing the files to be loaded has to be passed as an argument.");
        }


        String ip= arguments.ip;
        String database = arguments.database;
        String user = arguments.user;
        String pw = arguments.pw;

        connection = SQLConnection.getConnection(ip,database,user,pw);


        if(!arguments.filename.isEmpty())
        {
            processFile(arguments.filename);
        }
        if(!arguments.folderName.isEmpty())
        {
            File folder = new File(arguments.folderName);
            String[] listOfFiles = ArrayUtils.removeElement(folder.list(),arguments.filename);
            for(String filename : listOfFiles)
            {
                if (!filename.startsWith(".")) {
                    System.out.println("Processing file: " + arguments.folderName + "/" + filename);
                    processFile(arguments.folderName + "/" + filename);
                }
            }
        }
        */
    }

    private static String getFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }


    private static String processFile(String rawFilename, String filename) throws IOException, SQLException {

        System.out.println("File: "+filename);

        String tableName = rawFilename.substring(rawFilename.lastIndexOf("/")+1,rawFilename.lastIndexOf("."));

        System.out.println("Table name: "+tableName);
        List<String[]> records = LoadFile.load(filename);
        String createTable = SQLQueryBuilder.createTable(tableName, records.get(0));

        System.out.println(createTable);

        Statement statement = connection.createStatement();
        statement.executeUpdate(createTable);

        StringBuilder result = new StringBuilder("Table:<br>"+createTable.replace("\n","<br>")+"<br><br>Inserts:<br>");
        StringBuilder errors = new StringBuilder();



        boolean first = true;
        for(String[] record : records) {
            if (first != true) {
                String insert = SQLQueryBuilder.insert(tableName, records.get(0), record);
                try {
                    statement.executeUpdate(insert);
                    result.append(insert+"<br>");
                }
                catch (Exception e)
                {
                    System.out.println("Error in "+ insert);
                    errors.append(insert+"<br>");
                }
            }
            first = false;
        }
        if(!errors.toString().isEmpty()) result.append("<br><br>Errors:<br>").append(errors);
        return result.toString();
    }
}
