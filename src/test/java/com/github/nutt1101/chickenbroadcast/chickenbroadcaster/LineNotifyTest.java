package com.github.nutt1101.chickenbroadcast.chickenbroadcaster;

import com.github.nutt1101.chickenbroadcast.chickenbroadcaster.http.LineNotifyWebClient;
import com.github.nutt1101.chickenbroadcast.chickenbroadcaster.model.LineParameter;
import com.github.nutt1101.chickenbroadcast.chickenbroadcaster.model.LineSticker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URL;

@SpringBootTest
public class LineNotifyTest {
    @Autowired
    LineNotifyWebClient webClient;

    @Test
    void test() throws IOException {
          LineParameter parameter = LineParameter.builder()
                   .message("Test Message\nPowered by NUTT1101\ngithub: https://github.com/NUTT1101/ChickenBroadcaster/")
                  .lineSticker(
                          LineSticker.builder()
                                  .stickerPackageId(789)
                                  .stickerId(10857)
                                  .build()
                  )
                  .imageURL(
                          new URL("https://stickershop.line-scdn.net/stickershop/v1/sticker/10855/android/sticker.png")
                  )
                   .build();

//        webClient.send(parameter);
    }
}
