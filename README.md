# ChickenBroadcaster

ChickenBroadcaster is an automated tool designed to monitor and broadcast departmental bulletins from DaYeh University. This versatile application can function both as a Java API and as a LineNotify broadcasting tool, depending on your needs.

### How It Works
1. The application retrieves bulletins for specified departments from the DaYeh University [bulletin API](http://tis.dyu.edu.tw/app/app/news.php).
2. It then sends notifications to your LineNotify group, ensuring you stay updated with the latest announcements.

### Department ID and Name
To find the appropriate department ID and name for setting up the scanner, check out the [bulletin_department_table.json](https://github.com/NUTT1101/ChickenBroadcaster/blob/main/bulletin_department_table.json). For a clearer view, use the [jsonformatter-json-viewer](https://jsonformatter.org/json-viewer).

### Development Environment
- Java 17.0.2

ChickenBroadcaster is the go-to solution for staying informed about the latest updates from your university department, delivering them directly to your Line group.