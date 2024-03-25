package com.github.nutt1101.chickenbroadcast.chickenbroadcaster.scheduled;

import com.github.nutt1101.chickenbroadcast.chickenbroadcaster.model.Bulletin;
import com.github.nutt1101.chickenbroadcast.chickenbroadcaster.model.Department;
import com.github.nutt1101.chickenbroadcast.chickenbroadcaster.utils.BulletinUtils;
import com.github.nutt1101.chickenbroadcast.chickenbroadcaster.utils.LineNotifyMessageUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class BulletinScanner {
    final BulletinUtils bulletinUtils;
    final LineNotifyMessageUtils lineNotifyMessageUtils;

    @Value("${bulletin.pool.id}")
    Integer DEPARTMENT_POOL_ID;

    List<Bulletin> currentBulletinsPool;
    int MAX_POOL_LIMIT = 10;

    @PostConstruct
    void setup() {
        currentBulletinsPool = new ArrayList<>();
    }

    @Scheduled(cron = "0 * * * * *")
    void run() {
        try {
            List<Bulletin> newFetchedBulletins = this.bulletinUtils.getTopBulletins(
                    DEPARTMENT_POOL_ID == null || DEPARTMENT_POOL_ID <= 0
                                ? Department.STUDENT.getPoolId() : DEPARTMENT_POOL_ID,
                    MAX_POOL_LIMIT
            );

            List<Bulletin> foundLatest = this.bulletinUtils.findLatestBulletins(
                    this.currentBulletinsPool, newFetchedBulletins
            );

            if (foundLatest.isEmpty()) return;

            if (this.currentBulletinsPool.isEmpty()) {
                this.currentBulletinsPool.addAll(foundLatest);
                return;
            }

            for (Bulletin bulletin : foundLatest) {
                log.info("found latest bulletin: " + bulletin.getTitle());
                this.lineNotifyMessageUtils.sendNotification(bulletin);
            }

            while (currentBulletinsPool.size() > MAX_POOL_LIMIT) {
                Bulletin removed = this.currentBulletinsPool.remove(0);
                log.info(removed.getMsgId() + " removed");
            }
        } catch (IOException e) {
            log.error("error", e);
        }
    }
}
