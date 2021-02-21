package com.consistenthash.test;

import com.consistenthash.Node;

public class RealNode implements Node {
	private final String ip;
	private final int port;
	
	public RealNode(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}
	
	@Override
	public String getKey() {
		return ip + ":" + port;
	}

	public String toString() {
		return ip + ":" + port;
	}
}
