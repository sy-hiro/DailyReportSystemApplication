# 0. ubuntu:22.04をベースにする
FROM ubuntu:22.04

# 1. 必要なパッケージのインストールと日本語環境の設定
# - 不要なパッケージのインストールを避けるため、--no-install-recommendsを使用
# - タイムゾーンとロケールを非対話的に設定
ENV DEBIAN_FRONTEND=noninteractive
RUN ln -fs /usr/share/zoneinfo/Asia/Tokyo /etc/localtime && \
    apt update && \
    apt -y install --no-install-recommends language-pack-ja tzdata apache2 wget apt-transport-https gnupg ca-certificates gpg-agent && \
    dpkg-reconfigure --frontend noninteractive tzdata && \
    update-locale LANG=ja_JP.utf8 && \
    apt clean && rm -rf /var/lib/apt/lists/*

ENV LANG ja_JP.utf8
ENV TZ Asia/Tokyo

# 2. Apache 2の設定とモジュールの有効化
# - 必要なモジュールのみ有効化
RUN a2enmod rewrite ssl proxy proxy_ajp && \
    a2ensite default-ssl && \
    sed -i -e "s/^<\/VirtualHost>/RewriteEngine on/" /etc/apache2/sites-available/000-default.conf && \
    echo 'RewriteCond %{HTTPS} off' >> /etc/apache2/sites-available/000-default.conf && \
    echo 'RewriteRule ^(.*)$ https://%{HTTP_HOST}%{REQUEST_URI} [L]' >> /etc/apache2/sites-available/000-default.conf && \
    echo '</VirtualHost>' >> /etc/apache2/sites-available/000-default.conf

EXPOSE 8080
EXPOSE 443

# 3. JDKとSpring Bootアプリケーションのインストール
# - Temurin JDKをインストールし、Spring Bootアプリケーションを配置
RUN wget --no-check-certificate -O - https://packages.adoptium.net/artifactory/api/gpg/key/public | apt-key add - && \
    echo "deb https://packages.adoptium.net/artifactory/deb $(awk -F= '/^VERSION_CODENAME/{print$2}' /etc/os-release) main" | tee /etc/apt/sources.list.d/adoptium.list && \
    apt update && \
    apt -y install temurin-17-jdk=17.0.5.0.0+8 && \
    mkdir /var/www/java && \
    apt clean && rm -rf /var/lib/apt/lists/*

# Spring BootアプリケーションのJARファイルを配置
COPY DailyReportSystemApplication-0.0.1-SNAPSHOT.jar /var/www/java/

# 4. 起動時コマンドの指定
# - 起動スクリプトをコピーし、実行可能に設定
COPY startup.sh /startup.sh
RUN chmod 744 /startup.sh
CMD /startup.sh
