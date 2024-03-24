package com.github.nutt1101.chickenbroadcast.chickenbroadcaster.http;

import com.github.nutt1101.chickenbroadcast.chickenbroadcaster.model.LineParameter;
import org.htmlunit.HttpMethod;
import org.htmlunit.WebClient;
import org.htmlunit.WebRequest;
import org.htmlunit.util.NameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
@PropertySource("classpath:line_notify.properties")
public class LineNotifyWebClient extends WebClient {
    @Value("${token}")
    String TOKEN;
    @Value("${line_notify.api}")
    String API;

    String MESSAGE = "message";
    String IMAGE_THUMBNAIL = "imageThumbnail";
    String  IMAGE_FULL_SIZE = "imageFullsize";
    String IMAGE_FILE = "imageFile";
    String STICKER_PACKAGE_ID = "stickerPackageId";
    String STICKER_ID = "stickerId";
    String NOTIFICATION_DISABLED = "notificationDisabled";


    public void send(LineParameter parameter) throws IOException {
        WebRequest request = this.prepareRequest(parameter);
        this.getPage(request);
    }

    public WebRequest prepareRequest(LineParameter lineParameter) throws MalformedURLException {
        WebRequest request = new WebRequest(new URL(API), HttpMethod.POST);
        request.setAdditionalHeader("Content-Type", "application/x-www-form-urlencoded;");
        request.setAdditionalHeader("Authorization", "Bearer " + TOKEN);
        request.setRequestParameters(
                this.prepareRequestParameters(lineParameter)
        );
        request.setCharset(StandardCharsets.UTF_8);
        return request;
    }

    public List<NameValuePair> prepareRequestParameters(LineParameter parameter) {
        List<NameValuePair> reqPara = new ArrayList<>();
        reqPara.add(
                new NameValuePair(this.MESSAGE, parameter.getMessage())
        );
        if (parameter.getImageURL() != null) {
            reqPara.add(
                    new NameValuePair(this.IMAGE_THUMBNAIL, parameter.getImageURL().toString())
            );
            reqPara.add(
                    new NameValuePair(this.IMAGE_FULL_SIZE, parameter.getImageURL().toString())
            );
            if (parameter.isUploadToLineServer()) reqPara.add(
                    new NameValuePair(this.IMAGE_FILE, Boolean.TRUE.toString())
            );
        }

        if (parameter.getLineSticker() != null) {
            reqPara.add(
                    new NameValuePair(this.STICKER_PACKAGE_ID, parameter.getLineSticker().getStickerPackageId().toString())
            );
            reqPara.add(
                    new NameValuePair(this.STICKER_ID, parameter.getLineSticker().getStickerId().toString())
            );
        }

        if (parameter.isNotificationDisabled()) reqPara.add(
                new NameValuePair(this.NOTIFICATION_DISABLED, Boolean.TRUE.toString())
        );
        return reqPara;
    }
}
