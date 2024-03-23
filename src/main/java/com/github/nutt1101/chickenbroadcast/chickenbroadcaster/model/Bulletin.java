package com.github.nutt1101.chickenbroadcast.chickenbroadcaster.model;

import lombok.Builder;
import lombok.Data;

import java.net.URL;
import java.time.LocalDate;

@Data
@Builder
public class Bulletin {
    final int msgId;
    final String title;
    final String content;
    final LocalDate date;
    final URL url;
    URL image;
}
