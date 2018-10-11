package FileHandlers;

import com.opencsv.CSVReader;
import spark.Request;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class FileDriver {

    //Reads the content of a CSV file.
    public static List<String[]> read(String file) throws IOException
    {
        Reader reader = Files.newBufferedReader(Paths.get(file));
        CSVReader csvReader = new CSVReader(reader);

        return csvReader.readAll();
    }

    //Gets the filename from a path a and a request.
    public static String getFileName(Path file , Request req) {
        req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
        try (InputStream input = req.raw().getPart("uploaded_file").getInputStream()) { // getPart needs to use same "name" as input field in form
            Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
            return getFileName(req.raw().getPart("uploaded_file"));
        }

        catch (Exception e )
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }
    private static String getFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
