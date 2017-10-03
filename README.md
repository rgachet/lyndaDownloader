# lyndaDownloader
Downloader for lynda videos (need premium account or free 10 days trial)

# Configuration
You need to update following properties

in => src/main/resources/course.properties
baseOutputDirectory=<PATH_TO_COURSE_DIR>
lynda.user=<LYNDA_USER>
lynda.password=<LYNDA_PWD>

in => src/main/resources/log4j.properties
log4j.appender.file.File=<PATH_TO_LOG>

# Downloading courses
Run class LyndaDownloader with courses ids separated by a comma

Exemple:
You want to download videos from following courses:
https://www.lynda.com/Hadoop-tutorials/Extending-Hadoop-Data-Science-Streaming-Spark-Storm-Kafka/516574-2.html
https://www.lynda.com/Java-tutorials/WebSocket-Programming-Java-EE/574694-2.html

Run LyndaDownloader with arg 516574,574694

This will create folders named with the id of the course in baseOutputDirectory containing all downloaded videos

# Consult courses
Run package.bat (need maven and java) to build the sprint boot web application
Run launch.bat to execute it, then access http://localhost:8042 to consult a course