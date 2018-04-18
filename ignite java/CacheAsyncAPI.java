package com.igniteJava;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.lang.IgniteFuture;

public class CacheAsyncAPI 
{
	 private static final String CACHE_NAME = CacheAsyncAPI.class.getSimpleName();

	  public static void main(String[] args) throws IgniteException 
	  {
	        try (Ignite ignite = Ignition.start("example-ignite.xml")) 
	        {
	            System.out.println(">>> Cache asynchronous API example started.");
	            CacheConfiguration<Integer, String> cfg = new CacheConfiguration<>();
	            cfg.setCacheMode(CacheMode.PARTITIONED);
	            cfg.setName(CACHE_NAME);

	            // Auto-close cache at the end of the example.
	            try (IgniteCache<Integer, String> cache = ignite.getOrCreateCache(cfg)) 
	            {
	                // Enable asynchronous mode.
	                IgniteCache<Integer, String> asyncCache = cache.withAsync();

	                Collection<IgniteFuture<?>> futs = new ArrayList<>();

	                // Execute several puts asynchronously.
	                for (int i = 0; i < 10; i++) 
	                {
	                    asyncCache.put(i, String.valueOf(i));
	                    futs.add(asyncCache.future());
	                }

	                // Wait for completion of all futures.
	                futs.forEach(IgniteFuture::get);

	                // Execute get operation asynchronously.
	                asyncCache.get(2);

	                // Asynchronously wait for result.
	                asyncCache.<String>future().listen(fut ->
	                    System.out.println("Get operation completed [value=" + fut.get() + ']'));
	            }
	            finally 
	            {
	                // Distributed cache could be removed from cluster only by #destroyCache() call.
	                ignite.destroyCache(CACHE_NAME);
	            }
	        }
	    }
}
