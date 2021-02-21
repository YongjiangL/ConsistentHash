package com.consistenthash;

/**
 * A node be mapped to a hash ring
 * @author YongjiangL
 *
 */
public interface Node {
	/**
	 * 
	 * @return a key be used for hash mapping
	 */
	String getKey();
}
