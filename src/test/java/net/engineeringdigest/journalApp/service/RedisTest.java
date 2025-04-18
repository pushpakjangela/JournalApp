package net.engineeringdigest.journalApp.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    @Disabled
    public void testSendEmail(){
        redisTemplate.opsForValue().set("email","pushpak.jangela@gmail.com");
        Object relation = redisTemplate.opsForValue().get("age");
        int i =1;
    }
}
