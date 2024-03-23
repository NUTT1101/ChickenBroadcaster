package com.github.nutt1101.chickenbroadcast.chickenbroadcaster.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LineSticker {
    final Integer stickerPackageId;
    final Integer stickerId;
}
