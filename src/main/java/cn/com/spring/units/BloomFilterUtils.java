package cn.com.spring.units;

import java.nio.charset.Charset;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

public class BloomFilterUtils {
	
	private static BloomFilter<String> bloomFilter = null;
	
	private BloomFilterUtils() {
		
	}
	
	public synchronized static BloomFilter<String> getInstance(){
		
		if(null == bloomFilter) {
			synchronized (BloomFilterUtils.class) {
				bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.forName("utf-8")), 10000, 0.0001);
			}	
		}
		
		return bloomFilter;
	}

}
