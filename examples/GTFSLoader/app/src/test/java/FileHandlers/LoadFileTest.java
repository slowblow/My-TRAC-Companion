package FileHandlers;

import org.junit.Assert;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class LoadFileTest {

    @org.junit.Test
    public void load() throws IOException {
        List<String[]> records = LoadFile.load("/Users/joan/Downloads/original_gtfs/routes.txt");
        for(String[] nextRecord : records)
        {
            for(String field : nextRecord)
            {
                System.out.print(field+" ");
            }
            System.out.println();
        }
        Assert.assertTrue(1==1);

    }
}