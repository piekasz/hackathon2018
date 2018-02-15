// Copyright 2015 Sebastian Kuerten
//
// This file is part of osm4j.
//
// osm4j is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// osm4j is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with osm4j. If not, see <http://www.gnu.org/licenses/>.

package de.topobyte.osm4j.examples.tutorial;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmTag;
import de.topobyte.osm4j.xml.dynsax.OsmXmlIterator;

public class HelloWorld
{

	private static List<String> agglomerations = Arrays.asList(
			"Warszawa",
			"Katowice",
			"Kraków",
			"Łódź"
			);
	
	public static void main(String[] args) throws IOException
	{
		Map<String, List<OsmNode>> map = new HashMap<>();
		// Define a query to retrieve some data
//		String query = "file:///opt/poland-latest.osm"; 
		String query = "file:///home/piotr/Desktop/Nodes/Wrocław.osm"; 

		// Open a stream1
		InputStream input = new URL(query).openStream();

		// Create an iterator for XML data
		OsmIterator iterator = new OsmXmlIterator(input, false);
		for (String agglomeration : agglomerations) {
			System.out.println("searching " + agglomeration + "\n*****************************\n");
			List<OsmNode> nodes = filterCity(iterator, agglomeration);
			System.out.println(Arrays.toString(nodes.toArray()));
			map.put(agglomeration, nodes);
			System.out.println("finished " + agglomeration + "\n*****************************\n");

		}
		
		
		System.out.println("finished");
		System.exit(0);
	}

	public static List<OsmNode> filterCity(OsmIterator iterator, String agglomeration) {
		List<OsmNode> nodes = new ArrayList<>();;
		int count = 0;
		// Iterate all elements
		
		for (EntityContainer container : iterator) {

			// Check if the element is a node
			if (container.getType() == EntityType.Node) {

				// Cast the entity to OsmNode
				OsmNode node = (OsmNode) container.getEntity();
				if(filterNodes(node, agglomeration) != null) {
					nodes.add(node);
					count++;
				}
				if (100 < count) {
					break;
				}
			}

		}
		return nodes;
	}
	
	/*
stacje transformatorowe: (tag building=transformer_tower),
b. centra handlowe (tag shop=supermarket),
c. jednostki samorządowe (tag office=goverment i/lub building=civic),
d. inne, podane przez organizatorów w postaci listy adresów,
e. inne, uzasadnione przez uczestników.
	 */
	public static OsmNode filterNodes(OsmNode node, String agglomeration) {
		int tags = node.getNumberOfTags();
		for (int i = 0; i< tags; ++i) {
			OsmTag tag = node.getTag(i);
			if (tag.getKey().equals("addr:city") && tag.getValue().equals(agglomeration)){
//				System.out.println("\n ********* city " + aglomeracja + " **********");
//				System.out.println("id: " + node.getId());
//				System.out.println("latitude: " + node.getLatitude());
//				System.out.println("longitude: " + node.getLongitude());
				for (int j = 0; j< tags; ++j) {
					tag = node.getTag(j);
//					System.out.println(tag.getKey() + " = " + tag.getValue());
					switch (tag.getValue()) {
					case "transformer_tower":
					case "supermarket":
					case "goverment":
					case "civic":
					case "school":
						return node;
					default:
						break;
					
					}
				}
			}
		}
		return null;
	}



}

