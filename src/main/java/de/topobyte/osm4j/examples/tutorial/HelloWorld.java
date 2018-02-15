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

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmTag;
import de.topobyte.osm4j.xml.dynsax.OsmXmlIterator;

public class HelloWorld
{

	public static void main(String[] args) throws IOException
	{
		// Define a query to retrieve some data
		String query = "file:///opt/poland-latest.osm";
//		String query = "file:///home/piotr/Downloads/poland-latest.osm.bz2";
		// file:///home/piotr/Downloads/portal_pcj.pdf
		// Open a stream1
		InputStream input = new URL(query).openStream();

		// Create an iterator for XML data
		OsmIterator iterator = new OsmXmlIterator(input, false);

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

		System.out.println("finished");
	}

}
