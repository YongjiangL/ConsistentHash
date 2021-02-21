package com.consistenthash.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.consistenthash.ConsistentHash;

public class SampleTest {

	public static void main(String[] args) {
		// set up 4 real node
		RealNode node1 = new RealNode("72.7.2.94", 1001);
		RealNode node2 = new RealNode("72.1.4.94", 1002);
		RealNode node3 = new RealNode("72.3.6.94", 1003);
		RealNode node4 = new RealNode("72.9.8.94", 1004);
		
		ConsistentHash<RealNode> consistentHash = new ConsistentHash<>(Arrays.asList(node1, node2, node3, node4), 10);
		List<String> requestIps = Arrays.asList("61.232.1.2", "121.76.3.4", "139.196.5.6");
		for (String ip: requestIps) {
            RealNode rnode  = consistentHash.getNodeForKey(ip);
            System.out.println(ip + " is mapping to " + rnode);
        }
	}

}
