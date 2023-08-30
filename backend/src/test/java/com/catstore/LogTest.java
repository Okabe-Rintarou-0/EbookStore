package com.catstore;

import com.catstore.utils.logUtil.LogUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LogTest {
    @Test
    public void testLog(){
        LogUtil.info("Test log");
    }
}
