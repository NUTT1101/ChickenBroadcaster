package com.github.nutt1101.chickenbroadcast.chickenbroadcaster.utils;

import com.github.nutt1101.chickenbroadcast.chickenbroadcaster.http.BulletinWebClient;
import com.github.nutt1101.chickenbroadcast.chickenbroadcaster.model.Bulletin;
import com.github.nutt1101.chickenbroadcast.chickenbroadcaster.model.Department;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Log4j2
public class BulletinUtils {
    final BulletinWebClient bulletinWebClient;

    public List<Bulletin> getTopBulletins(Department department, int top) throws IOException {
        return this.getTopBulletins(department.getPoolId(), top);
    }

    public List<Bulletin> getTopBulletins(Integer poolId, int top) throws IOException {
        log.info("Pool ID: " + poolId + " fetching...");
        return bulletinWebClient.fetch(poolId, 0, top);
    }

    public List<Bulletin> findLatestBulletins(List<Bulletin> currentPool, List<Bulletin> newPool) {
        Set<Integer> existsPool = currentPool.stream()
                .map(Bulletin::getMsgId)
                .collect(Collectors.toSet());

        return newPool
                .stream()
                .filter(bulletin ->
                        this.isSameDateBulletin(bulletin) &&
                        !existsPool.contains(bulletin.getMsgId())
                )
                .collect(Collectors.toList());
    }

    boolean isSameDateBulletin(Bulletin bulletin) {
        return bulletin.getDate().isEqual(LocalDate.now()) ||
                bulletin.getDate().isAfter(LocalDate.now());
    }
}
