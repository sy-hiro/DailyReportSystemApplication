#!/bin/bash

# Apache2の起動
rm -f /var/run/apache2/apache2.pid
apachectl start

# Spring Bootアプリケーションの起動
exec java -jar /var/www/java/DailyReportSystemApplication-0.0.1-SNAPSHOT.jar
