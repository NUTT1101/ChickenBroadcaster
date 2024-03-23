package com.github.nutt1101.chickenbroadcast.chickenbroadcaster.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.net.URL;

@Data
@Builder
public class LineParameter {
    final String message;
    URL imageURL;
    /**
     * Upload a image file to the LINE server.
     * Supported image format is png and jpeg.
     * If you specified imageThumbnail ,imageFullsize and imageFile, imageFile takes precedence.
     * There is a limit that you can upload to within one hour.
     * For more information, please see the section of the API Rate Limit.
     */
    boolean imageFile;
    final LineSticker lineSticker;
    boolean notificationDisabled;


}
