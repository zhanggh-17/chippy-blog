package top.chippy.blog.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import top.chippy.blog.config.KeyConfiguration;
import top.chippy.blog.jwt.RsaKeyHelper;

import java.util.Map;

/**
 * @Description If key in the redis. Get key for redis.
 * If haven't key in the redis, generate key and set redis.
 * @Author chippy
 */
@Configuration
@Slf4j
public class ShopServerRunner implements CommandLineRunner {

    private static final String REDIS_USER_PRI_KEY = "CHIPPY:BLOG:AUTH:JWT:PRI";
    private static final String REDIS_USER_PUB_KEY = "CHIPPY:BLOG:AUTH:JWT:PUB";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private KeyConfiguration keyConfiguration;

    @Override
    public void run(String... args) throws Exception {
        log.info("chippy-blog manager system starting...");
        generateKey();
    }

    private void generateKey() throws Exception {
        if (redisTemplate.hasKey(REDIS_USER_PRI_KEY) && redisTemplate.hasKey(REDIS_USER_PUB_KEY)) {
            keyConfiguration.setPriKey(
                    RsaKeyHelper.toBytes(redisTemplate.opsForValue().get(REDIS_USER_PRI_KEY).toString()));
            keyConfiguration.setPubKey(
                    RsaKeyHelper.toBytes(redisTemplate.opsForValue().get(REDIS_USER_PUB_KEY).toString()));
        } else {
            Map<String, byte[]> keyMap = RsaKeyHelper.generateKey(keyConfiguration.getUserSecret());
            keyConfiguration.setPriKey(keyMap.get("pri"));
            keyConfiguration.setPubKey(keyMap.get("pub"));
            redisTemplate.opsForValue().set(REDIS_USER_PRI_KEY, RsaKeyHelper.toHexString(keyMap.get("pri")));
            redisTemplate.opsForValue().set(REDIS_USER_PUB_KEY, RsaKeyHelper.toHexString(keyMap.get("pub")));
        }
    }

    @Scheduled(cron = "0 0/10 * * * ?")
    public void refreshServicePubKey() throws Exception {
        log.info("timer job starting...");
        generateKey();
    }
}
