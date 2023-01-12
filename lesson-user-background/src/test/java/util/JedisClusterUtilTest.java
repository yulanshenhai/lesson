package util;


import com.xiao.util.JedisClusterUtil;
import org.junit.Test;
import redis.clients.jedis.JedisCluster;

public class JedisClusterUtilTest {

    @Test
    public void testGetJedis() {
        JedisCluster jedis = JedisClusterUtil.getJedis();
        System.out.println(jedis.set("name","xiao"));
        JedisClusterUtil.closeJedisCluster(jedis);
    }

}
