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

    public void send(LineParameter parameter) throws IOException {
        WebRequest request = prepareRequest(parameter);
        this.getPage(request);
    }

    public WebRequest prepareRequest(LineParameter lineParameter) throws MalformedURLException {
        WebRequest request = new WebRequest(new URL(API), HttpMethod.POST);
        request.setAdditionalHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        request.setAdditionalHeader("Authorization", "Bearer " + TOKEN);
        request.setRequestParameters(
                prepareRequestParameters(lineParameter)
        );
        request.setCharset(StandardCharsets.UTF_8);
        return request;
    }

    public List<NameValuePair> prepareRequestParameters(LineParameter parameter) {
        List<NameValuePair> reqPara = new ArrayList<>();
        reqPara.add(
                new NameValuePair("message", parameter.getMessage())
        );
        if (parameter.getImageURL() != null) {
            reqPara.add(
                    new NameValuePair("imageThumbnail", parameter.getImageURL().toString())
            );
            reqPara.add(
                    new NameValuePair("imageFullsize", parameter.getImageURL().toString())
            );
            if (parameter.isUploadToLineServer()) reqPara.add(
                    new NameValuePair("imageFile", "true")
            );
        }

        if (parameter.getLineSticker() != null) {
            reqPara.add(
                    new NameValuePair("stickerPackageId", parameter.getLineSticker().getStickerPackageId().toString())
            );
            reqPara.add(
                    new NameValuePair("stickerId", parameter.getLineSticker().getStickerId().toString())
            );
        }

        if (parameter.isNotificationDisabled()) reqPara.add(
                new NameValuePair("notificationDisabled", "true")
        );
        return reqPara;
    }
}
