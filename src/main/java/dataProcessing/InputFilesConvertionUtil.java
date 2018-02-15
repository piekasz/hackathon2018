package dataProcessing;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.xml.dynsax.OsmXmlIterator;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class InputFilesConvertionUtil {
    public static OsmIterator CreateIteratorXML(String path) throws IOException {

        BZip2CompressorInputStream input = null;
        try {
            input = new BZip2CompressorInputStream(new FileInputStream(path));
            return new OsmXmlIterator(input, false);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    public static Map<String, String> ImportAgglomerations() {

        String csvFile = "agglomerations.csv";
        String line = "";
        String cvsSplitBy = ",";
        Map<String,String> map = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] agglomeration = line.split(cvsSplitBy);
                map.put(agglomeration[0],agglomeration[1]);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }
}