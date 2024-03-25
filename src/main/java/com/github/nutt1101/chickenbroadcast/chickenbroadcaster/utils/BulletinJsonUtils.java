package com.github.nutt1101.chickenbroadcast.chickenbroadcaster.utils;

import com.github.nutt1101.chickenbroadcast.chickenbroadcaster.model.Bulletin;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class BulletinJsonUtils {
    final String MSG_ID = "msg_ID";
    final String TITLE = "newstitle";
    final String CONTENT = "newcontent";
    final String DATE = "newsdate";
    final String URL = "newsurl";
    final String IMAGE = "img";

    /**
     * To convert to json string to jsonObject and return the list of all bulletins
     * @param jsonString  json string
     * @return list off all bulletins
     * @throws MalformedURLException URL parsed error
     */
    public List<Bulletin> convert(String jsonString) throws MalformedURLException {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray array = jsonObject.getJSONArray("news");

        String title = jsonObject.getString("title");
        log.info(title + " was requested successful. length: " + array.length());

        List<Bulletin> bulletinList = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            Bulletin bulletin = this.jsonObjToBulletin(
                    array.getJSONObject(i)
            );
            bulletinList.add(bulletin);
        }

        return bulletinList;
    }

    /**
     * To convert jsonObject to list of bulletin object
     * @param object all bulletins jsonObject
     * @return converted bulletin
     * @throws MalformedURLException URL parsed error
     */
    Bulletin jsonObjToBulletin(JSONObject object) throws MalformedURLException {
        int msgId = object.getInt(MSG_ID);
        String title = object.getString(TITLE);
        String content = object.getString(CONTENT);
        String date = object.getString(DATE);
        String url = object.getString(URL);
        String img = "";

        if (!object.isNull(IMAGE)) {
            img = object.getString(IMAGE);
        }

            Bulletin bulletin = Bulletin.builder()
                .msgId(msgId)
                .title(title)
                .content(content)
                .date(
                        LocalDate.parse(
                                date,
                                DateTimeFormatter.ofPattern("yyyy/MM/dd")
                        )
                )
                .url(new URL(url))
                .build();

        if (img.contains("https")) {
            bulletin.setImage(new URL(img));
        }

        return bulletin;
    }
}
