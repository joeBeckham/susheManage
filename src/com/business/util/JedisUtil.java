package com.business.util;

import java.util.List;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @ClassName: JedisUtil
 * @Description: TODO(Jedis 工具类 )
 * @author xbq
 * @date 2016-5-14 下午1:36:35
 * 
 */
public class JedisUtil {

	private JedisPool pool;

	private static String URL = ConstantUtil.REDIS_IP;
	private static Integer PORT = ConstantUtil.REDIS_PORT;
	//private static String REDIS_PASSWORD = ConstantUtil.REDIS_PWD;

	// threadlocal，给每个线程 都弄一份 自己的资源
	private final static ThreadLocal<JedisPool> threadPool = new ThreadLocal<JedisPool>();

	private final static ThreadLocal<Jedis> threadJedis = new ThreadLocal<Jedis>();

	// 得到连接池实例
	private JedisPool getPool() {
		JedisPoolConfig config = new JedisPoolConfig();
		// 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
		// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
		config.setMaxTotal(100);

		// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
		config.setMaxIdle(50);
		// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
		config.setMaxWaitMillis(-1);// 1000 * 300

		// 回收
		// config.setTestWhileIdle(true);
		// config.setMinEvictableIdleTimeMillis(60000);//回收闲置60秒的连接
		// config.setTimeBetweenEvictionRunsMillis(30000);//回收线程 运行周期
		// config.setNumTestsPerEvictionRun(-1);

		final int timeout = 60 * 1000;

		// //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
		// config.setTestOnBorrow(true);
		pool = new JedisPool(config, URL, PORT, timeout);
		return pool;
	}
	
	
	/**
	* @Title: hsetValue 
	* @Description: TODO(添加键值对)
	* @author xbq 
	* @date 2016-5-14 下午1:49:53
	* @param @param tabName
	* @param @param key
	* @param @param value
	* @param @return
	* @return Long
	* @throws
	 */
    public Long hsetValue(String tabName, String key, String value) {
        Jedis jedis = this.common();
        return jedis.hset(tabName, key, value);
    }

    /**
    * @Title: hvalsValue 
    * @Description: TODO(获得所有的值)
    * @author xbq 
    * @date 2016-5-14 下午1:50:17
    * @param @param tabName
    * @param @return
    * @return List<String>
    * @throws
     */
    public List<String> hvalsValue(String tabName) {
        Jedis jedis = this.common();
        return jedis.hvals(tabName);
    }

    /**
    * @Title: hdelValue 
    * @Description: TODO(删除指定的Key)
    * @author xbq 
    * @date 2016-5-14 下午1:50:29
    * @param @param tabName
    * @param @param keys
    * @param @return
    * @return Long
    * @throws
     */
    public Long hdelValue(String tabName, String... keys) {
        Jedis jedis =  this.common();
        return jedis.hdel(tabName, keys);
    }

    /**
    * @Title: hexistsValue 
    * @Description: TODO(判断key是否存在)
    * @author xbq 
    * @date 2016-5-14 下午1:50:41
    * @param @param tabName
    * @param @param key
    * @param @return
    * @return boolean
    * @throws
     */
    public boolean hexistsValue(String tabName, String key) {
        Jedis jedis =  this.common();
        return jedis.hexists(tabName, key);
    }

    /**
    * @Title: hgetValue 
    * @Description: TODO(获取key对应的value值)
    * @author xbq 
    * @date 2016-5-14 下午1:50:53
    * @param @param tabName
    * @param @param key
    * @param @return
    * @return String
    * @throws
     */
    public String hgetValue(String tabName, String key) {
        Jedis jedis = this.common();
        return jedis.hget(tabName, key);
    }
    
    /**
    * @Title: hgetValue 
    * @Description: TODO(获取key对应的value值)
    * @author xbq 
    * @date 2016-5-14 下午1:51:09
    * @param @param tabName
    * @param @param key
    * @param @return
    * @return List<String>
    * @throws
     */
    public List<String> hgetValue(String tabName, String... key) {
        Jedis jedis = this.common();
        return jedis.hmget(tabName, key);
    }
    
    /**
    * @Title: happendValue 
    * @Description: TODO(追加)
    * @author xbq 
    * @date 2016-5-14 下午1:51:43
    * @param @param key
    * @param @param value
    * @param @return
    * @return Long
    * @throws
     */
    public Long happendValue(String key, String value) {
        Jedis jedis = this.common();
        return jedis.append(key, value);
    }
    
    
    //通用方法
    private Jedis common(){
    	 pool = threadPool.get();
    	if(pool == null){
    		pool = this.getPool();
    		//维护该线程的jedis连接池实例
    		threadPool.set(pool);
    	}
    	Jedis jedis = threadJedis.get();
    	if(jedis==null){
    		jedis = this.getJedis(pool);
    		//维护该线程 对应的 jedis实例
    		threadJedis.set(jedis);
    	}
    	return jedis;
    }
    
    //从redis连接池中 获取redis连接
    private  Jedis getJedis(JedisPool pool ){
    	Jedis client = pool.getResource();
		//client.auth(REDIS_PASSWORD);
		return client;
    }
    
    //释放资源
    public void closeAll(){
    	Jedis jedis = threadJedis.get();
    	if(jedis !=null){
    		//清空线程的jedis
    		threadJedis.set(null);
    		JedisPool pool = threadPool.get();
    		if(pool!=null){
    			//连接释放 归还给连接池
    			pool.returnResource(jedis);
    		}
    	}
    }
    
    @Test
    public void testAdd(){
    	JedisUtil util = new JedisUtil();
    	util.hsetValue("tab_name", "name12", "徐邦启");
    }

}
