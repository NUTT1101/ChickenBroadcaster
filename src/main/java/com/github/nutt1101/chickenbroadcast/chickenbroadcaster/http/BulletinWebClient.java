package com.github.nutt1101.chickenbroadcast.chickenbroadcaster.http;

import com.github.nutt1101.chickenbroadcast.chickenbroadcaster.model.Bulletin;
import com.github.nutt1101.chickenbroadcast.chickenbroadcaster.utils.BulletinJsonUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
public class BulletinWebClient extends org.htmlunit.WebClient {
    // http://tis.dyu.edu.tw/app/app/news.php
    @Value("${api.bulletin}")
    String API;
    final
    BulletinJsonUtils bulletinJsonUtils;

    /**
     * disable the css and js to make the requisition speed up
     */
    @PostConstruct
    void setup() {
        this.getOptions().setCssEnabled(false);
        this.getOptions().setJavaScriptEnabled(false);
    }

    /**
     * To send the request to bulletin api and get back the bulletin data.
     * @param poolId the id of the department id that data you want to fetch
     * @param startIndex start index of the bulletin
     * @param endIndex end index of the bulletin (not contains the end)
     * @return a list of bulletin that you want to fetch
     * @throws IOException check
     */
    public List<Bulletin> fetch(Integer poolId, int startIndex, int endIndex) throws IOException {
        WebRequest request = this.prepareRequest(
                poolId, startIndex, endIndex
        );
        UnexpectedPage page = this.getPage(request);
        String jsonString = this.unexpectedPageToJsonString(page);
        return this.bulletinJsonUtils.convert(jsonString);
    }

    /**
     * Handle the requested page to json string.
     * @param page the requested page
     * @return the page to json string
     * @throws IOException check
     */
    String unexpectedPageToJsonString(UnexpectedPage page) throws IOException {
        String jsonString;
        try (InputStream stream = page.getInputStream()) {
            jsonString = IOUtils.toString(stream, StandardCharsets.UTF_8);
        }
        return jsonString;
    }

    /**
     * To prepare the bulletin requestion object
     * @param poolId the id of the department id that data you want to fetch
     * @param startIndex start index of the bulletin
     * @param endIndex end index of the bulletin (not contains the end)
     * @return was set up requestion
     * @throws MalformedURLException URL parsed error
     */
    WebRequest prepareRequest(Integer poolId, int startIndex, int endIndex) throws MalformedURLException {
        WebRequest request = new WebRequest(
            new URL(API),
            HttpMethod.POST
        );

        request.setRequestBody(
                String.format(
                        """
                        {
                          "pool_ID" : %d,
                          "sitems" : %d,
                          "eitems" : %d
                        }
                        """
                , poolId, startIndex, endIndex)
        );

        return request;
    }
}
