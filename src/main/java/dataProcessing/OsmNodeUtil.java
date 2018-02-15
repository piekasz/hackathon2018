package dataProcessing;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmTag;
import de.topobyte.osm4j.xml.dynsax.OsmXmlIterator;

public class OsmNodeUtil {

	public static String toString(OsmNode node){
		String result;
		result =  String.format( " <node id=\"%s\" lat=\"%s\" lon=\"%s\" visible=\"true\">\n",
				node.getId(), node.getLatitude(), node.getLongitude());
		for (int i = 0; i < node.getNumberOfTags(); i++) {
			OsmTag tag = node.getTag(i);
			result = result + String.format("  <tag k=\"%s\" v=\"%s\"/>\n", tag.getKey(), tag.getValue());
		}

		result = result + " </node> ";
		return result;
	}
	
	/**
	 * 
	 * @param path - path to file in osm format example Warszawa.osm;
	 * @return
	 */
	public static List<OsmNode> nodesFromFile(String path){
		List<OsmNode> nodes = new ArrayList<OsmNode>();
		String query = "file:///" +path;
		
		try(InputStream input = new URL(query).openStream()){
			OsmIterator iterator = new OsmXmlIterator(input, false);
			
			for (EntityContainer container : iterator) {

				// Check if the element is a node
				if (container.getType() == EntityType.Node) {

					// Cast the entity to OsmNode
					OsmNode node = (OsmNode) container.getEntity();
					nodes.add(node);
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nodes;
	}
	
}
