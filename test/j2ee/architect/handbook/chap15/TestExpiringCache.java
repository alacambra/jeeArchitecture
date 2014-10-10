package j2ee.architect.handbook.chap15;

import org.junit.Assert;
import org.junit.Test;

public class TestExpiringCache {

	@Test
	public void test() throws Exception {
		ExpiringCache<Long, String> cache = new ExpiringCache<Long, String>(1, 2);
		String value = cache.get(Long.MIN_VALUE);
		Assert.assertTrue("Should be null", value == null);
		
		cache.put(Long.MIN_VALUE, "Minimum");
		value = cache.get(Long.MIN_VALUE);
		Assert.assertTrue("Should be Minimum", "Minimum".equals(value));
		
		Thread.sleep(1100);
		Assert.assertTrue("Size should be 1", cache.size() == 1);
		value = cache.get(Long.MIN_VALUE);
		Assert.assertTrue("Should be null as its expired", value == null);
		Assert.assertTrue("Size should be 0", cache.size() == 0);
		
		cache.put(Long.MIN_VALUE, "Minimum");
		Assert.assertTrue("Size should be 1", cache.size() == 1);
		Thread.sleep(4100);
		Assert.assertTrue("Size should be 0 after cleanup", cache.size() == 0);
	}

}
