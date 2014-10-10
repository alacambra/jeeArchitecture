package j2ee.architect.handbook.chap15;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * Entries will expire after a configurable time period.  Recommend static declaration.
 * @author D. Ashmore
 *
 * @param <K>
 * @param <V>
 */
public class ExpiringCache<K, V> {
	
	public static final long DEFAULT_EXPIRATION_TIME_IN_SECONDS=3600;  // 1 hour
	public static final long DEFAULT_PURGE_INTERVAL_IN_SECONDS = DEFAULT_EXPIRATION_TIME_IN_SECONDS * 4;         // 4 hours
	
	private final Cache<K,V> cache;
	private final static ScheduledExecutorService scheduler = 
		       Executors.newScheduledThreadPool(2);
	
	public ExpiringCache() {
		this.cache = CacheBuilder.newBuilder().expireAfterWrite(DEFAULT_EXPIRATION_TIME_IN_SECONDS, TimeUnit.SECONDS).build();
		scheduler.scheduleAtFixedRate(new ExpiringCacheCleanupTask<K, V>(this.cache), 
				DEFAULT_PURGE_INTERVAL_IN_SECONDS, DEFAULT_PURGE_INTERVAL_IN_SECONDS, TimeUnit.SECONDS);
	}
	
	public ExpiringCache(long expirationTimeInSeconds, long purgeIntervalInSeconds) {
		this.cache = CacheBuilder.newBuilder().expireAfterWrite(expirationTimeInSeconds, TimeUnit.SECONDS).build();
		scheduler.scheduleAtFixedRate(new ExpiringCacheCleanupTask<K, V>(this.cache), 
				purgeIntervalInSeconds, purgeIntervalInSeconds, TimeUnit.SECONDS);
	}
	
	public V get(K key) {
		return cache.getIfPresent(key);
	}
	
	public void put(K key, V value) {
		cache.put(key, value);
	}
	
	public V remove(K key) {
		V value = cache.getIfPresent(key);
		cache.invalidate(key);
		return value;
	} 
	
	public void clearAll() {
		cache.invalidateAll();
	}
	
	public long size() {
		return cache.size();
	}
	
	private static class ExpiringCacheCleanupTask<K, V> implements Runnable {

		private final Cache<K, V> cache;
		
		public ExpiringCacheCleanupTask(Cache<K, V> c) {
			cache = c;
		}
		
		@Override
		public void run() {
			cache.cleanUp();
			
		}
		
	}

}
