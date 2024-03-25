# ChickenBroadcaster

ChickenBroadcaster is an automated tool designed to monitor and broadcast departmental bulletins from DaYeh University. This versatile application can function both as a Java API and as a LineNotify broadcasting tool, depending on your needs.

### Usage
1. Register a LineNotify token.
2. Use this command with arguments `-Dtoken` and `-Dbulletin.pool.id`.
- -Dtoken
  - Your LineNotify Token.
- -bulletin.pool.id
  - Bulletin pool ID that you want to fetch.
  - To find the appropriate department ID and name for setting up the scanner, check out the [bulletin_department_table.json](https://github.com/NUTT1101/ChickenBroadcaster/blob/main/bulletin_department_table.json). For a clearer view, use the [jsonformatter-json-viewer](https://jsonformatter.org/json-viewer).
```bash
java -jar -Dtoken=<your_line_notify_token> -Dbulletin.pool.id=<pool_id> ChickenBroadcaster-0.0.2-SNAPSHOT.jar
```

### How It Works
1. The application retrieves bulletins for specified departments from the DaYeh University [bulletin API](http://tis.dyu.edu.tw/app/app/news.php).
2. It then sends notifications to your LineNotify group, ensuring you stay updated with the latest announcements.

### Development Environment
- Java 17.0.2

### Notice
The line_notify.properties file, which contains the LineNotify token, is located in the resources/ directory and has been added to .gitignore for security reasons. To modify the LineNotify token, please create a new line_notify.properties file in the project's resources/ folder and specify your token there.

### Work in Progress

If the bulletin includes an attached file, it can be retrieved from a separate API endpoint: https://tis.dyu.edu.tw/app/app/news_content.php. To access the attached file, you need to know both the `pool_ID` and `msg_ID` and make a POST request to this API. The response will be in the following format:

```json
{
    "msg_ID": 39027,
    "msg_title": "Title of the bulletin",
    "content": "Content of the bulletin",
    "publisher_name": "Name of the publisher",
    "dept_name": "Department name",
    "contact_name": "Contact person's name",
    "contact_eMail": "Contact email",
    "contact_tel": "Contact telephone number",
    "startDate": "Start date of the bulletin",
    "endDate": "End date of the bulletin",
    "display_type": "Type of display",
    "news_media": "Media details",
    "news_date": "Date of the news",
    "msg_count": "Message count",
    "img_ary": [],
    "img_source_ary": [],
    "attachment_ary": [
        {
            "description": "Description of the attached file",
            "url": "URL to the attached file"
        }
    ],
    "link_ary": [],
    "youtube_ary": [],
    "prev_msg_ID": "Previous message ID",
    "prev_msg_title": "Title of the previous message",
    "next_msg_ID": "Next message ID",
    "next_msg_title": "Title of the next message",
    "pool_name": "Name of the pool"
}
```
The attached files can be found in the `attachment_ary` array object, providing direct links to download them.

---
ChickenBroadcaster is the go-to solution for staying informed about the latest updates from your university department, delivering them directly to your Line group.