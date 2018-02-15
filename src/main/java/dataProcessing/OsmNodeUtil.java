package dataProcessing;

import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmTag;

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
}
