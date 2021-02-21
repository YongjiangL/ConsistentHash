package com.consistenthash;

/**
 * A virtual node refers to the real node
 * @author YongjiangL
 *
 * @param <T>
 */
public class VirtualNode<T extends Node> implements Node {
	final T realNode;
	final int virtualID;
	
	public VirtualNode(T realNode, int virtualID) {
		this.realNode = realNode;
		this.virtualID = virtualID;
	}
	
	@Override
	public String getKey() {
		return realNode.getKey() + "_" + virtualID;
	}
	
	public boolean isVirtualNodeOf(T rNode) {
		return this.realNode.getKey().equals(rNode.getKey());
	}
	
	public T getRealNode() {
		return this.realNode;
	}
}
