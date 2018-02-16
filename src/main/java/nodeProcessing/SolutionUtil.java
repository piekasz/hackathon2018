package nodeProcessing;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import dataProcessing.OsmNodeUtil;
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
		BigDecimal result = new BigDecimal(1000000);
		BigDecimal d ;
		Iterator<OsmNode> iter1 = nodes1.iterator();
		while(iter1.hasNext()) {
			Iterator<OsmNode> iter2 = nodes2.iterator();
			OsmNode node1 = iter1.next();
			while(iter2.hasNext()) {
				OsmNode node2 = iter2.next();
				d = (countDistance(node1, node2 ));
				if(d.compareTo(result) == -1) {
					result = d;
					try {
						System.out.println(OsmNodeUtil.toString(node1));
						System.out.println(OsmNodeUtil.toString(node2));
						System.out.println(result);
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return result;
	}
	
	/**
	 *  counts distance between all nodes and selects the biigest
	 *  (the bigger distance the more nodes are spreaded)
	 * @param nodes
	 * @return
	 */
	public static BigDecimal countBestArrangementInCity(List<OsmNode> nodes) {
		BigDecimal sum = new BigDecimal(0);
		Iterator<OsmNode> iter1 = nodes.iterator();
		while(iter1.hasNext()) {
			Iterator<OsmNode> iter2 = nodes.iterator();
			OsmNode node1 = iter1.next();
			while(iter2.hasNext()) {
				sum = sum.add(countDistance(node1, iter2.next()));
			}
		}
		return sum;
	}
	/**
	 * 		formula:	a = sin²(Δφ/2) + cos φ1 ⋅ cos φ2 ⋅ sin²(Δλ/2)
			c = 2 ⋅ atan2( √a, √(1−a) )
			result = R ⋅ c
			where	φ is latitude, λ is longitude, R is earth’s radius (mean radius = 6,371km);
	 */
	public static BigDecimal countDistance(OsmNode node1, OsmNode node2) {
		
		double fi1 = Math.toRadians(node1.getLatitude());
		double fi2 = Math.toRadians(node2.getLatitude());
		double deltaFi =  Math.toRadians((node1.getLatitude() - node2.getLatitude()));
		double daltaTeta = Math.toRadians((node1.getLongitude() - node2.getLongitude()));
		
		double a = Math.sin(deltaFi/2) * Math.sin(deltaFi/2) +
		        Math.cos(fi1) * Math.cos(fi2) *
		        Math.sin(daltaTeta) * Math.sin(daltaTeta);
		
		return  new BigDecimal((R *2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a))));
	}
}
