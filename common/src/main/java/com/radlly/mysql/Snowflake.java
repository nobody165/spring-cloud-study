package com.radlly.mysql;

import org.springframework.stereotype.Component;

public class Snowflake {
	private static short NODE_SHIFT;
	private static short SEQ_SHIFT;

	private static long MAX_NODE;
	private static long MAX_SEQUENCE;

	private static short sequence;
	private static long referenceTime;

	// /////从2015年1月1日0点的时间毫秒。
	private final static long startTimeMillisecends = 1483200000000L;

	private static int node;

	/**
	 * A snowflake is designed to operate as a singleton instance within the
	 * context of a node. If you deploy different nodes, supplying a unique node
	 * id will guarantee the uniqueness of ids generated concurrently on
	 * different nodes.
	 *
	 * @param node
	 *            This is an id you use to differentiate different nodes.
	 */
	public Snowflake() {
		// node = getNode();
		// if (node < 0 || node > MAX_NODE) {
		// throw new IllegalArgumentException(String.format(
		// "node must be between %s and %s", 0, MAX_NODE));
		// }
		// this.node = node;

	}
	
	

	/**
	 * Generates a k-ordered unique 64-bit integer. Subsequent invocations of
	 * this method will produce increasing integer values.
	 *
	 * @return The next 64-bit integer.
	 */
	public synchronized static Long next() {

		long currentTime = System.currentTimeMillis() - startTimeMillisecends;
		long counter;

		if (currentTime < referenceTime) {
			throw new RuntimeException(String.format(
					"Last referenceTime %s is after reference time %s",
					referenceTime, currentTime));
		} else if (currentTime > referenceTime) {
			sequence = 0;
		} else {
			if (sequence < Snowflake.MAX_SEQUENCE) {
				sequence++;
			} else {
				throw new RuntimeException("Sequence exhausted at " + sequence);
			}
		}
		counter = sequence;
		referenceTime = currentTime;

		return Long.valueOf(currentTime << NODE_SHIFT << SEQ_SHIFT
				| node << SEQ_SHIFT | counter);
	}	

	public static short getNODE_SHIFT() {
		return NODE_SHIFT;
	}



	public static short getSEQ_SHIFT() {
		return SEQ_SHIFT;
	}



	public static void setNODE_SHIFT(short nodeShift) {
		NODE_SHIFT = nodeShift;
		MAX_NODE = -1L ^ (-1L << NODE_SHIFT);
	}

	public static void setSEQ_SHIFT(short seqShift) {
		SEQ_SHIFT = seqShift;
		MAX_SEQUENCE = -1L ^ (-1L << SEQ_SHIFT);
	}
	

	public static int getNode() {
		return node;
	}



	public static void setNode(int Node) {
		if (Node < 0 || Node > MAX_NODE) {
			throw new IllegalArgumentException(String.format(
					"node must be between %s and %s", 0, MAX_NODE));
		}
		node = Node;
		// return
		// Integer.valueOf(RequestUtils.getConfigValue("primekey.nodes"));
	}
}
