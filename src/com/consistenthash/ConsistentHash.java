package com.consistenthash;

import java.util.Collection;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * refer: https://github.com/dustin/java-memcached-client/blob/master/src/main/java/net/spy/memcached/KetamaNodeLocator.java
 * mapping Nodes to a hash ring with a certain number of virtual nodes
 */
public class ConsistentHash<T extends Node> {
	private final SortedMap<Long, VirtualNode<T>> ring = new TreeMap<>();
	private final HashAlgorithm hashAlg;

	public ConsistentHash(Collection<T> rNodes, int vNodeNum) {
		this(rNodes, vNodeNum, new MD5Hash());
	}

	public ConsistentHash(Collection<T> rNodes, int vNodeNum, HashAlgorithm hashAlg) {
		if (hashAlg == null) {
			throw new NullPointerException("Hash Algorithm is null");
		}
		this.hashAlg = hashAlg;
		if (rNodes != null) {
			for (T rNode : rNodes) {
				addNode(rNode, vNodeNum);
			}
		}
	}
	
	public T getNodeForKey(String key) {
		if (ring.isEmpty()) {
			return null;
		}
		Long hashVal = hashAlg.hash(key);
		SortedMap<Long, VirtualNode<T>> tailMap = ring.tailMap(hashVal);
		if (!tailMap.isEmpty()) {
			return ring.get(tailMap.firstKey()).getRealNode();
		}
		else {
			return ring.get(ring.firstKey()).getRealNode();
		}
	}

	/**
	 * add real node to the hash ring with some virtual nodes
	 * @param rNode
	 * @param vNodeNum
	 */
	public void addNode(T realNode, int vNodeNum) {
		if (vNodeNum < 0) {
			throw new IllegalArgumentException("illegal virtual node num :" + vNodeNum);
		}
		int existingReplicas = getExistingReplicas(realNode);
		for (int i = 0; i < vNodeNum; i++) {
			VirtualNode<T> vNode = new VirtualNode<>(realNode, i + existingReplicas);
			ring.put(hashAlg.hash(vNode.getKey()), vNode);
		}
	}
	
	/**
	 * remove a real node from the hash ring
	 * @param realNode
	 */
	public void removeNode(T realNode) {
		Iterator<Long> it = ring.keySet().iterator();
		while (it.hasNext()) {
			Long key = it.next();
			VirtualNode<T> virtualNode = ring.get(key);
			if (virtualNode.isVirtualNodeOf(realNode)) {
				it.remove();
			}
		}
	}

	public int getExistingReplicas(T rNode) {
		int replicas = 0;
		for (VirtualNode<T> vNode : ring.values()) {
			if (vNode.isVirtualNodeOf(rNode)) {
				replicas++;
			}
		}
		return replicas;
	}

}
