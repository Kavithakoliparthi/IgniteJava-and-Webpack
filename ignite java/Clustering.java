package com.igniteJava;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCluster;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.cluster.ClusterGroup;

public class Clustering 
{
	public static void main(String[] args) 
	{
		try(Ignite ignite=Ignition.start("example-ignite.xml"))
		{
			if (!ExamplesUtils.checkMinTopologySize(ignite.cluster(), 2))
                return;
			IgniteCluster cluster=ignite.cluster();
				//to all nodes in the cluster, including local node.
			sayHello(ignite,cluster);
				//to all remote nodes.
			sayHello(ignite,cluster.forRemotes());
				//pick random node out of remote nodes
			ClusterGroup randomNode=cluster.forRemotes().forRandom();
				//to random node
			sayHello(ignite,randomNode);
				//to all residing on the same host with random node
			sayHello(ignite,cluster.forHost(randomNode.node()));
				//to all nodes that have current CPU load less than 50%
			sayHello(ignite,cluster.forPredicate(n -> n.metrics().getCurrentCpuLoad() < 0.5));
		}
	}
	private static void sayHello(Ignite ignite,final ClusterGroup grp)throws IgniteException
	{
		ignite.compute(grp).broadcast(
				() ->System.out.println(">> Hello Node:"+grp.ignite().cluster().localNode().id()));
	}
}

