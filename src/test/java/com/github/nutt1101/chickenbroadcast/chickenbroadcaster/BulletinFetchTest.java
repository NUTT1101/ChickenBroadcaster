package com.github.nutt1101.chickenbroadcast.chickenbroadcaster;

import com.github.nutt1101.chickenbroadcast.chickenbroadcaster.http.BulletinWebClient;
import com.github.nutt1101.chickenbroadcast.chickenbroadcaster.model.Department;
import com.github.nutt1101.chickenbroadcast.chickenbroadcaster.utils.BulletinUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class BulletinFetchTest {
    @Autowired
    BulletinWebClient webClient;
    @Autowired
    BulletinUtils bulletinUtils;

    @Test
    void test() {
        try {
//            webClient.fetch("1", 0 ,5).forEach(
//                    System.out::println
//            );
            bulletinUtils.getTopBulletins(Department.GENERAL, 10).forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
