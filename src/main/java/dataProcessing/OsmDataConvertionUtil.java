package dataProcessing;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmTag;
import de.topobyte.osm4j.xml.dynsax.OsmXmlIterator;

public class OsmDataConvertionUtil
{

	public static void main(String[] args) throws IOException
	{
		// Define a query to retrieve some data
		String query = "C:\\Users\\Jacek Andrzej SteÄ‡\\Downloads\\HACKATHON 2018\\poland-latest.osm.bz2";
//		String query = "file:///home/piotr/Downloads/poland-latest.osm.bz2";
		// file:///home/piotr/Downloads/portal_pcj.pdf
		// Open a stream1
		//InputStream input = new URL(query).openStream();

        FileInputStream in = new FileInputStream(query);
        BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream(in);

        //in.close();

		// Create an iterator for XML data
		OsmIterator iterator = new OsmXmlIterator(bzIn, false);

		int count = 0;
		// Iterate all elements
		for (EntityContainer container : iterator) {

			// Check if the element is a node
			if (container.getType() == EntityType.Node) {

				// Cast the entity to OsmNode
				OsmNode node = (OsmNode) container.getEntity();
				int tags = node.getNumberOfTags();
				for (int i = 0; i< tags; ++i) {
					OsmTag tag = node.getTag(i);
//					if ((tag.getKey().equals("building") && tag.getValue().equals("church")) || (tag.getKey().equals("power") && tag.getValue().equals("tower"))) {
					if ((tag.getKey().equals("addr:city"))) {
						System.out.println("\n ********* nubmer " + count + " **********");
						System.out.println("id: " + node.getId());
						System.out.println("latitude: " + node.getLatitude());
						System.out.println("longitude: " + node.getLongitude());
						for (int j = 0; j< tags; ++j) {
							tag = node.getTag(j);
							System.out.println(tag.getKey() + " = " + tag.getValue());
							
						}
						count++;
						break;
					}
				}
				if (100 < count) {
					break;
				}
				
//					// Print basic information
//					System.out.println("id: " + node.getId());
//					System.out.println("latitude: " + node.getLatitude());
//					System.out.println("longitude: " + node.getLongitude());
//
//					// Also print all tags
//					System.out.println("tags:");
//					for (int i = 0; i < node.getNumberOfTags(); i++) {
//						OsmTag tag = node.getTag(i);
//						System.out.println(tag.getKey() + " = " + tag.getValue());
//
//					}
			}

		}


		bzIn.close();
		System.out.println("finished");
	}

}