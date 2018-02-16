package nodeProcessing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.topobyte.osm4j.core.model.iface.OsmNode;

public class MonteCarloSolution {
	
	/**
	 * 38 mln 433 tys.
	 */
	private static int populationPL = 38433000;
	private static int numberOfStations = 6000;
	private static int numberOfAllNodes = 6000/5;
	public static List<OsmNode> pickRandomNodes(List<OsmNode> nodes, int ratio){
		// TODO
		
		Random random = new Random();
		int size = nodes.size();
		int nodesToGet = ratio*numberOfAllNodes + 1;
		List<OsmNode> result= new ArrayList<>(nodesToGet);
		for (int i=0; i<nodesToGet; ++i) {
			int index = random.nextInt(size);
			result.add(nodes.get(index));
		}
		
		return result;
	}
	

	
	

}
