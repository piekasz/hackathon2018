package dataProcessing;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmTag;
import de.topobyte.osm4j.xml.dynsax.OsmXmlIterator;

public class OsmNodeUtil {

	/**
	 * convets node to string that represents node in osm xml format
	 * @param node
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String toString(OsmNode node) throws UnsupportedEncodingException{
		String result;
		result =  String.format( " <node id=\"%s\" lat=\"%s\" lon=\"%s\" visible=\"true\">\n",
				node.getId(), node.getLatitude(), node.getLongitude());
		for (int i = 0; i < node.getNumberOfTags(); i++) {
			OsmTag tag = node.getTag(i);
			if (tag.getValue().contains("\"")) {
				continue;
			}
			result = result + String.format("  <tag k=\"%s\" v=\"%s\"/>\n", tag.getKey(),
//					URLEncoder.encode(tag.getValue(), "UTF-8"));
					tag.getValue(), "UTF-8");
		}

		result = result + " </node> ";
		return result;
	}
	
	/**
	 * converts node to string that represents node address example Warsaw ul.Marszałkowska 1;
	 */
	public static String toAddress(OsmNode node) {
		OsmTag tag;
		String city = "";
		String street = "";
		String houseNumber = "";
		for (int i = 0; i < node.getNumberOfTags(); i++) {
			tag = node.getTag(i);
			switch(tag.getKey()) {
			case "addr:city":
				city = tag.getValue();
				break;
			case "addr:street":
				street = tag.getValue();
				break;
			case "addr:housenumber":
				houseNumber = tag.getValue();
				break;
			default:
				break;
			}
		}
		
		return String.format(" %s, %s %s " ,city, street, houseNumber);
		
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
