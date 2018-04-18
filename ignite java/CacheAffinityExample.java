package com.igniteJava;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.configuration.CacheConfiguration;

public class CacheAffinityExample 
{
		//Cache name
	private static final String CACHE_NAME = CacheAffinityExample.class.getSimpleName();
		//Number of keys
	private static final int KEY_CNT =20;
	public static void main(String[] args)throws IgniteException 
	{
		try(Ignite ignite=Ignition.start())
		{
			System.out.println(">Cache affinity example started");
			CacheConfiguration<Integer,String> cfg=new CacheConfiguration<Integer,String>();
			cfg.setCacheMode(CacheMode.PARTITIONED);
			cfg.setName(CACHE_NAME);
				//Auto-close cache at the end
			try(IgniteCache<Integer,String> cache=ignite.getOrCreateCache(cfg))
			{
				for(int i=0;i<KEY_CNT;i++)
				{
					cache.put(i,Integer.toString(i));
					visitUsingAffinityRun();
					VisitUsingMapKeysToNodes();
				}
			}
			finally
			{
				// Distributed cache could be removed from cluster only by #destroyCache() call.
				ignite.destroyCache(CACHE_NAME);
			}
		}
	}
	private static void visitUsingAffinityRun() {
        Ignite ignite = Ignition.ignite();

        final IgniteCache<Integer, String> cache = ignite.cache(CACHE_NAME);

        for (int i = 0; i < KEY_CNT; i++)
        {
            int key = i;
            // This runnable will execute on the remote node where
            // data with the given key is located. Since it will be co-located
            // we can use local 'peek' operation safely.
            ignite.compute().affinityRun(CACHE_NAME, key,
                () -> System.out.println("Co-located using affinityRun [key= " + key + ", value=" + cache.localPeek(key) + ']'));
        }
    }

    /**
     * Collocates jobs with keys they need to work on using {@link Affinity#mapKeysToNodes(Collection)}
     * method. The difference from {@code affinityRun(...)} method is that here we process multiple keys
     * in a single job.
     */
    private static void VisitUsingMapKeysToNodes()
    {
        final Ignite ignite = Ignition.ignite();

        Collection<Integer> keys = new ArrayList<>(KEY_CNT);

        for (int i = 0; i < KEY_CNT; i++)
            keys.add(i);

        // Map all keys to nodes.
        Map<ClusterNode, Collection<Integer>> mappings = ignite.<Integer>affinity(CACHE_NAME).mapKeysToNodes(keys);

        for (Map.Entry<ClusterNode, Collection<Integer>> mapping : mappings.entrySet())
        {
            ClusterNode node = mapping.getKey();

            final Collection<Integer> mappedKeys = mapping.getValue();

            if (node != null) 
            {
                // Bring computations to the nodes where the data resides (i.e. collocation).
                ignite.compute(ignite.cluster().forNode(node)).run(() -> {
                    IgniteCache<Integer, String> cache = ignite.cache(CACHE_NAME);

                    // Peek is a local memory lookup, however, value should never be 'null'
                    // as we are co-located with node that has a given key.
                    for (Integer key : mappedKeys)
                        System.out.println("Co-located using mapKeysToNodes [key= " + key +
                            ", value=" + cache.localPeek(key) + ']');
                });
            }
        }
    }
}


