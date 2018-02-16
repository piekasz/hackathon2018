package nodeProcessing;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dataProcessing.OsmNodeUtil;
import de.topobyte.osm4j.core.model.iface.OsmNode;

public class SolutionUtilTest {

	List<OsmNode> nodes;
	@Before public void init() {
		String path = "/home/piotr/Desktop/Nodes/Kielce.osm";
		List<OsmNode> nodes = OsmNodeUtil.nodesFromFile(path);
	}
	@Test
	public void countBestArrangementInCityTest() {
//		String path = "/home/piotr/Desktop/Nodes/Kielce.osm";
//		List<OsmNode> nodes = OsmNodeUtil.nodesFromFile(path);
		
		BigDecimal result = SolutionUtil.countBestArrangementInCity(nodes);
		System.out.println(result.toString());
	}
	@Test
	public void countDistancesTest() {
//		String path = "/home/piotr/Desktop/Nodes/Kielce.osm";
//		List<OsmNode> nodes = OsmNodeUtil.nodesFromFile(path);
		
		BigDecimal result = SolutionUtil.countSmallestDistance(nodes, nodes);
		System.out.println(result.toString());
	}

}
