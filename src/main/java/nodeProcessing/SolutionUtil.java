package nodeProcessing;

import java.math.BigDecimal;
import java.util.List;

import de.topobyte.osm4j.core.model.iface.OsmNode;

public class SolutionUtil {
	

	/**
	 * R is earth’s radius (mean radius = 6,371km)
	 */
	public static double R = 6371e3;
	
	/**
	 *  
	 * @return smallest distance between two cities
	 */
	public static BigDecimal countSmallestDistance(List<OsmNode> nodes1, List<OsmNode> nodes2) {
		 // TODO
//		return null;
	}
	
	/**
	 *  counts distance between all nodes and selects the biigest
	 *  (the bigger distance the more nodes are spreaded)
	 * @param nodes
	 * @return
	 */
	public static BigDecimal countBestArrangementInCity(List<OsmNode> nodes) {
//		return null;
//		TODO
	}
	/**
	 * 		formula:	a = sin²(Δφ/2) + cos φ1 ⋅ cos φ2 ⋅ sin²(Δλ/2)
			c = 2 ⋅ atan2( √a, √(1−a) )
			result = R ⋅ c
			where	φ is latitude, λ is longitude, R is earth’s radius (mean radius = 6,371km);
	 */
	public static double countDistance(OsmNode node1, OsmNode node2) {
		
		double fi1 = Math.toRadians(node1.getLatitude());
		double fi2 = Math.toRadians(node2.getLatitude());
		double deltaFi =  Math.toRadians((node1.getLatitude() - node2.getLatitude()));
		double daltaTeta = Math.toRadians((node1.getLongitude() - node2.getLongitude()));
		
		double a = Math.sin(deltaFi/2) * Math.sin(deltaFi/2) +
		        Math.cos(fi1) * Math.cos(fi2) *
		        Math.sin(daltaTeta) * Math.sin(daltaTeta);
		
		return  (R *2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)));
	}
}
