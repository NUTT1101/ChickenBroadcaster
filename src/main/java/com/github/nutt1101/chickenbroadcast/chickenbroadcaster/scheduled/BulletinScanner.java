package com.github.nutt1101.chickenbroadcast.chickenbroadcaster.scheduled;

import com.github.nutt1101.chickenbroadcast.chickenbroadcaster.http.BulletinWebClient;
import com.github.nutt1101.chickenbroadcast.chickenbroadcaster.http.LineNotifyWebClient;
import com.github.nutt1101.chickenbroadcast.chickenbroadcaster.model.Bulletin;
import com.github.nutt1101.chickenbroadcast.chickenbroadcaster.model.LineParameter;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BulletinScanner {
    final BulletinWebClient bulletinWebClient;
    final LineNotifyWebClient lineNotifyWebClient;

    int latestBulletinId = 1;

    @Scheduled(cron = "0 * * * * *")
    void run() {
        try {
            List<Bulletin> bulletinList = bulletinWebClient.fetch(
                    "5100", 0, 1
            );
            Bulletin current = bulletinList.get(0);
            if (latestBulletinId == current.getMsgId()) return;

            latestBulletinId = current.getMsgId();

            String notification = notification(current);
            LineParameter parameter = LineParameter.builder()
                    .message(notification)
                    .build();
            if (current.getImage() != null) parameter.setImageURL(current.getImage());

            lineNotifyWebClient.send(parameter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    String notification(Bulletin bulletin) {
        return String.join(
                "\n",
                List.of(
                        "",
                        bulletin.getTitle(),
                        bulletin.getUrl().toString()
                )
        );
    }
}
