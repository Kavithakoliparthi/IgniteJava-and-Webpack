package Ignitejava;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;

public class SampleDemo 
{
	public static void main(String[] args) 
	{
		try(Ignite ignite=Ignition.start())
		{
			IgniteCache<Integer,String> cache=ignite.getOrCreateCache("mycache");
			cache.put(1, "Ignite");
			cache.put(2, "is a");
			cache.put(3, "MultiLanguage Support");
			
			ignite.compute().broadcast(()->
			{
				System.out.println(cache.get(1)+" "+cache.get(2)+" "+cache.get(3));
			});
		}
	}
}
