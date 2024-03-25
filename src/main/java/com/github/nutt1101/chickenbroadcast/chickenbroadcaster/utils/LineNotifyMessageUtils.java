package com.github.nutt1101.chickenbroadcast.chickenbroadcaster.utils;

import com.github.nutt1101.chickenbroadcast.chickenbroadcaster.http.LineNotifyWebClient;
import com.github.nutt1101.chickenbroadcast.chickenbroadcaster.model.Bulletin;
import com.github.nutt1101.chickenbroadcast.chickenbroadcaster.model.LineParameter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LineNotifyMessageUtils {
    final LineNotifyWebClient lineNotifyWebClient;

    String prepareNotification(Bulletin bulletin) {
        return String.join(
                "\n",
                List.of(
                        "",
                        bulletin.getTitle(),
                        bulletin.getUrl().toString()
                )
        );
    }

    public void sendNotification(Bulletin bulletin) throws IOException {
        String notification = prepareNotification(bulletin);

        LineParameter parameter = LineParameter.builder()
                .message(notification)
                .build();

        if (bulletin.getImage() != null) parameter.setImageURL(bulletin.getImage());

        lineNotifyWebClient.send(parameter);
    }
}
