package main;

import java.util.List;

import dataProcessing.OsmNodeUtil;
import de.topobyte.osm4j.core.model.iface.OsmNode;

public class mainApp {
	
	// TODO here we can put all solution may be yet not pararelled
	
	/*
	 *  main(){
	 *   load files:
	 *   	- osm data
	 *   	- file with aglomerations and its populations
	 *   convert file and pass then further
	 *   select nodes
	 *   put nodes to file - then we can run only monte carlo simulations and check iterations
	 *   selec random nodes for each city
	 *   count how solution is good
	 *   if the best save randoms seed and all solution
	 *   go for next itaration
	 */
	public static void main(String[] args) {
		String path = "/home/piotr/Desktop/Nodes/Kielce.osm";
		List<OsmNode> nodes = OsmNodeUtil.nodesFromFile(path);
		for (OsmNode osmNode : nodes) {
			System.out.println(OsmNodeUtil.toAddress(osmNode));
		}
		System.out.println("Koniec");
	}

}
