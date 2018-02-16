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
		return new BigDecimal(distance(node1.getLatitude(), node1.getLongitude(),node2.getLatitude(), node2.getLongitude() ));
//		BigDecimal fi1 = new BigDecimal(Math.toRadians(node1.getLatitude()));
//		BigDecimal fi2 = new BigDecimal(Math.toRadians(node2.getLatitude()));
//		BigDecimal deltaFi =  new BigDecimal(Math.toRadians((node1.getLatitude() - node2.getLatitude())));
//		BigDecimal daltaTeta =new BigDecimal(Math.toRadians((node1.getLongitude() - node2.getLongitude())));
//		
//		BigDecimal a =  new BigDecimal(Math.sin(deltaFi.doubleValue()/2.0) * Math.sin(deltaFi.doubleValue()/2.0) +
//		        Math.cos(fi1.doubleValue()) * Math.cos(fi2.doubleValue()) *
//		        Math.sin(daltaTeta.doubleValue()) * Math.sin(daltaTeta.doubleValue()));
//		
//		return  new BigDecimal((R *2 * Math.atan2(Math.sqrt(a.doubleValue()), Math.sqrt(1-a.doubleValue()))));
	}
	
    private static  double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
//        if (unit == 'K') {
//          dist = dist * 1.609344;
//        } else if (unit == 'N') {
//          dist = dist * 0.8684;
//          }
        return (dist);
      }

      /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
      /*::  This function converts decimal degrees to radians             :*/
      /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
      private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
      }

      /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
      /*::  This function converts radians to decimal degrees             :*/
      /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
      private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
      }
}
