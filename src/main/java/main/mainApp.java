package main;

import java.math.BigDecimal;
import java.util.List;

import dataProcessing.OsmNodeUtil;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import nodeProcessing.SolutionUtil;

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

		String path = "/home/piotr/Nodes/Lublin.osm";
		List<OsmNode> nodes = OsmNodeUtil.nodesFromFile(path);
		BigDecimal result = SolutionUtil.countBestArrangementInCity(nodes);
		System.out.println(result.toString());
		
		String path2 = "/home/piotr/Nodes/Częstochowa.osm";
		List<OsmNode> nodes2 = OsmNodeUtil.nodesFromFile(path2);
		BigDecimal result2 = SolutionUtil.countSmallestDistance(nodes, nodes2);
		System.out.println(result2.toString());
	}

}
