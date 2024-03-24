package com.github.nutt1101.chickenbroadcast.chickenbroadcaster.model;

import lombok.Getter;

/**
 * More department id see:
 * <a href="https://github.com/NUTT1101/ChickenBroadcaster/blob/main/bulletin_department_table.json">...</a>
 */
@Getter
public enum Department {
    CSIE("5100") /*資訊工程學系*/,
    STUDENT("2700") /*學生事務處*/,
    GENERAL("1") /*一般行政*/;

    final String poolId;
    Department(String poolId) {
        this.poolId = poolId;
    }
}
