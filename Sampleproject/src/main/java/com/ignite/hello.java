package com.ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;

public class hello
{
	 public static void main(String[] args) throws IgniteException 
	 {
		    try (Ignite ignite = Ignition.start()) 
		    {
		      // Put values in cache.
		      IgniteCache<Integer, String> cache = ignite.getOrCreateCache("myCache");
		       
		      cache.put(1, "Hello");
		      cache.put(2, "Ignite");
		 
		      // Get values from cache and
		      // broadcast 'Hello World' on all the nodes in the cluster.
		      ignite.compute().broadcast(() ->
		      {
		        String s1 = cache.get(1);
		        String s2 = cache.get(2);
		 
		        System.out.println(s1 + " " + s2);
		      });
		    }
		  }
}
