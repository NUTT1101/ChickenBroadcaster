package com.github.nutt1101.chickenbroadcast.chickenbroadcaster.http;

import com.github.nutt1101.chickenbroadcast.chickenbroadcaster.model.Bulletin;
import com.github.nutt1101.chickenbroadcast.chickenbroadcaster.utils.BulletinJsonUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.htmlunit.HttpMethod;
import org.htmlunit.UnexpectedPage;
import org.htmlunit.WebRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BulletinWebClient extends org.htmlunit.WebClient {
    // http://tis.dyu.edu.tw/app/app/news.php
    @Value("${bulletin.api}")
    String API;
    final
    BulletinJsonUtils bulletinJsonUtils;

    @PostConstruct
    void setup() {
        this.getOptions().setCssEnabled(false);
        this.getOptions().setJavaScriptEnabled(false);
    }

    public List<Bulletin> fetch(String poolId, int startIndex, int endIndex) throws IOException {
        WebRequest request = this.prepareRequest(
                poolId, startIndex, endIndex
        );
        UnexpectedPage page = this.getPage(request);
        String jsonString = this.unexpectedPageToJsonString(page);
        return this.bulletinJsonUtils.convert(jsonString);
    }

    String unexpectedPageToJsonString(UnexpectedPage page) throws IOException {
        String jsonString;
        try (InputStream stream = page.getInputStream()) {
            jsonString = IOUtils.toString(stream, StandardCharsets.UTF_8);
        }
        return jsonString;
    }

    WebRequest prepareRequest(String poolId, int startIndex, int endIndex) throws MalformedURLException {
        WebRequest request = new WebRequest(
            new URL(API),
            HttpMethod.POST
        );

        request.setRequestBody(
                String.format(
                        """
                        {
                          "pool_ID" : %s,
                          "sitems" : %d,
                          "eitems" : %d
                        }
                        """
                , poolId, startIndex, endIndex)
        );

        return request;
    }
}
