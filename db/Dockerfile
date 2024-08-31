# 0. ubuntu:22.04をベースにする
FROM ubuntu:22.04

# 1. 非対話的モードに設定（tzdataの設定を自動化するため）
ENV DEBIAN_FRONTEND=noninteractive

# 2. 必要なパッケージのインストール
RUN apt update && \
    apt -y install --no-install-recommends language-pack-ja tzdata mysql-server-8.0 && \
    apt clean && \
    rm -rf /var/lib/apt/lists/*

# 6. MySQL設定
RUN usermod -d /var/lib/mysql mysql && \
    sed -i -e "s/^bind-address\s*=\s*127.0.0.1/bind-address = 0.0.0.0/" /etc/mysql/mysql.conf.d/mysqld.cnf

COPY mysql_setup.sql /tmp/
RUN mysqld_safe & sleep 5 && cat /tmp/mysql_setup.sql | mysql

EXPOSE 3306

# 8. 起動時コマンドの指定
COPY startup.sh /startup.sh
RUN chmod 744 /startup.sh
CMD /startup.sh
