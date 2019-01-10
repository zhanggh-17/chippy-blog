package top.chippy.blog.service;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @program: loser
 * @Author: chippy
 * @Description:
 */
@Service
@Slf4j
public class AccessLimitService {
    RateLimiter rateLimiter = RateLimiter.create(1.0);

    /**
     * 尝试获取令牌
     *
     * @return
     */
    public boolean tryAcquire(long millisecond, TimeUnit unit) {
        log.info("等待生成令牌...");
        return rateLimiter.tryAcquire(millisecond, unit);
    }
}
