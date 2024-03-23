package com.github.nutt1101.chickenbroadcast.chickenbroadcaster;

import com.github.nutt1101.chickenbroadcast.chickenbroadcaster.http.BulletinWebClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class BulletinFetchTest {
    @Autowired
    BulletinWebClient webClient;

    @Test
    void test() {
        try {
            webClient.fetch("5100", 0 ,20).forEach(
                    System.out::println
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
