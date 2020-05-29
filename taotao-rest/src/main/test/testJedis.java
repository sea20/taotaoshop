import org.junit.Test;
import redis.clients.jedis.Jedis;

public class testJedis {
    @Test
    public  void testJedisSingle(){
        Jedis jedis = new Jedis("localhost",6379);
        jedis.set("abc","hehehe");
        String abc = jedis.get("abc");
        System.out.println(abc);

    }
    @Test
    public void testString(){
        System.out.println("哦哦");
    }
}
