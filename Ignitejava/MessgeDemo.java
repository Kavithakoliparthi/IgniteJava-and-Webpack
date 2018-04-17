package Ignitejava;

import java.util.Scanner;

import javax.cache.event.CacheEntryEvent;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteAtomicSequence;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.query.ContinuousQuery;

public class MessgeDemo 
{
	public static void main(String[] args) 
	{
		try(Ignite ignite=Ignition.start())
		{
			System.out.print("Hi, enter your name: ");
			Scanner sc = new Scanner(System.in);
			String name = sc.nextLine();
			IgniteCache<Long, Message> cache = ignite.getOrCreateCache("chat");		// Get or create cache
			IgniteAtomicSequence messageId = ignite.atomicSequence(
						"chatId", 	//sequence name
						0, 			//initialize value for sequence
						true);			// if it does not exist create
			ContinuousQuery<Long, Message> qry = new ContinuousQuery<>();			// Set up continuous query
			qry.setLocalListener(iterable -> 
			{
			    for (CacheEntryEvent<? extends Long, ? extends Message> evt : iterable)			    	// This will be invoked immediately on each cache update
			        System.out.println(evt.getValue().author + ": " + evt.getValue().text);
			});
			cache.query(qry);
			while (true) 
			{
			    System.out.print(">");
			    String msgText = sc.nextLine();
			    Long msgId = messageId.incrementAndGet();
			    cache.put(msgId, new Message(name, msgText));
			}
		}
	}
}
