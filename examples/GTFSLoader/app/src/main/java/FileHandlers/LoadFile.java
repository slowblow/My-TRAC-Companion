package FileHandlers;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class LoadFile {

    public static List<String[]> load(String file) throws IOException
    {
        Reader reader = Files.newBufferedReader(Paths.get(file));
        CSVReader csvReader = new CSVReader(reader);

        return csvReader.readAll();
    }
}
