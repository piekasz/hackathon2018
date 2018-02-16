package dataProcessing;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmTag;
import de.topobyte.osm4j.xml.dynsax.OsmXmlIterator;

public class OsmNodeSelectionUtil
{

	private static List<String> agglomerations ;
	public static void main(String[] args) throws IOException
	{
		
		runSelection();
		System.exit(0);
	}



	public static void runSelection() throws IOException, FileNotFoundException {
		String path = "/home/piotr/Downloads/poland-latest.osm.bz2";
//		Map<String, String> agglomerationsMap = InputFilesConvertionUtil.ImportAgglomerations();
		agglomerations = new ArrayList<>(InputFilesConvertionUtil.ImportAgglomerations().keySet());
		// Define a query to retrieve some data
//		String query = "file:///opt/poland-latest.osm";

		try(BZip2CompressorInputStream input = new BZip2CompressorInputStream(new FileInputStream(path))){
			
			OsmIterator iterator = new OsmXmlIterator(input, false);
			startFiles();
			createNodesSet(iterator);
			endFiles();
			
			System.out.println("finished");
		}
	}
	
	public static void flushNodes(Map<String, List<OsmNode>> map) {
		for(String city : agglomerations) {
			List<OsmNode> nodes = map.get(city);
			try ( FileWriter fw = new FileWriter("/home/piotr/Nodes/"+city+".osm",true)){
				 for(OsmNode node : nodes) {
				    	fw.write(OsmNodeUtil.toString(node));			    	
				 }
			} catch (IOException e) {
				e.printStackTrace();
			}
			nodes.clear();
		}
	}
	
	public static void startFiles() {
		 //the true will append the new data
			for(String city : agglomerations) {
				try ( FileWriter fw = new FileWriter("/home/piotr/Nodes/"+city+".osm")){
				    fw.write(" <?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
				    		"<osm version=\"0.6\" generator=\"CGImap 0.0.2\">\n ");
					} catch (IOException e) {
						// 
						e.printStackTrace();
					}
			}
	}
	
	public static void endFiles() {
		for(String city : agglomerations) {
			try ( FileWriter fw = new FileWriter("/home/piotr/Nodes/"+city+".osm",true)){
				 fw.write("\n</osm>");
			} catch (IOException e) {
				// 
				e.printStackTrace();
			}
		}
	}




	public static boolean checkNodesFunction(OsmNode node) {
		int tags = node.getNumberOfTags();
		for (int j = 0; j< tags; ++j) {
			OsmTag tag = node.getTag(j);
//			System.out.println(tag.getKey() + " = " + tag.getValue());
			switch (tag.getValue()) {
			case "transformer_tower":
			case "transformer":
			case "supermarket":
			case "goverment":
			case "civic":
			case "townhall":
			case "manufacture":
			case "stadium":
//				System.out.println(OsmNodeUtil.toString(node));
				return true;
			default:
//				System.out.println(OsmNodeUtil.toString(node));
				break;
			}
		}
		return false;
	}
	public static String getNodeCity(OsmNode node) {
		int tags = node.getNumberOfTags();
		OsmTag tag;
		for (int j = 0; j< tags; ++j) {
			tag = node.getTag(j);
			if (tag.getKey().equals("addr:city")) {
				for (String agglomeration : agglomerations) {
					if(tag.getValue().equals(agglomeration)) {
						return agglomeration;
					}
				}
				return null;
			}
		}
		return null;
	}
	public static void createNodesSet(OsmIterator iterator) {
		Map<String,  List<OsmNode>> nodes = new HashMap<>();
		for(String city : agglomerations) {
			nodes.put(city, new ArrayList<>());
		}
		// TODO delete, only for mockup data generation;
		int iter = 0;
		int stop = 0;
		for (EntityContainer container : iterator) {
			if(stop>250) {
				flushNodes(nodes);
				stop = 0;
				System.out.println(iter);
				iter++;
				if (iter >4) {
					return;
				}
			}
			if (container.getType() == EntityType.Node) {

				OsmNode node = (OsmNode) container.getEntity();
				if(checkNodesFunction(node)) {
					String city = getNodeCity(node);
					if(city != null) {
						nodes.get(city).add(node);
						stop++;
						System.out.print(stop);
					} else {
//						iterator.remove();
					}
				} else {
//					iterator.remove();
				}
			}
		}
		// flush the rest of nodes
		flushNodes(nodes);
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
						System.out.println(OsmNodeUtil.toString(node));
						return node;
					default:
//						System.out.println(OsmNodeUtil.toString(node));
						break;
					}
				}
				break;
			}
		}
		return null;
	}
	private static void generateFiles(Map<String, List<OsmNode>> map) {
		for(String city : agglomerations) {
			List<OsmNode> nodes = map.get(city);
			try {
			    BufferedWriter writer = new BufferedWriter(new FileWriter("/home/piotr/Nodes/"+city+".osm"));
			    writer.write(" <?xml version='1.0' encoding='UTF-8'?>\n" + 
			    		"<osm version=\"0.6\" generator=\"osmconvert 0.8.5\" timestamp=\"2018-02-11T21:44:02Z\">\n" + 
			    		"	<bounds minlat=\"48.986421\" minlon=\"13.990216\" maxlat=\"55.228256\" maxlon=\"24.161023\"/> ");
			    for(OsmNode node : nodes) {
			    	writer.write(OsmNodeUtil.toString(node));			    	
			    }
			    writer.write("\n</osm>");
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
	}
	


}

