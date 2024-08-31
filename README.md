# 日報管理システム

### URL: http://sugawara.tokyoitgroup.com/login

## 概要

このプロジェクトは、従業員の日報を効率的に管理するために開発されました。従業員は日々の業務内容を記録し、管理者はこれを確認・管理することができます。これにより、業務の透明性を高め、効率的な業務管理を実現します。

## 目的

このシステムを開発した目的は以下の通りです：

- **業務の透明性向上**: 従業員の日々の業務内容を記録し、管理者が確認できるようにすることで、業務の透明性を高めます。
- **効率的な業務管理**: 日報をデジタル化することで、紙ベースの管理から脱却し、効率的な業務管理を実現します。
- **データの一元管理**: 日報データを一元管理し、必要な情報を迅速に取得できるようにします。

## 主な機能

- **従業員のログイン・ログアウト機能**: 従業員は自身のアカウントでログインし、セキュアにログアウトできます。
- **日報の作成・更新・削除機能**: 従業員は日報を作成し、必要に応じて更新や削除が可能です。
- **日報の日付重複チェック機能**: 同じ日付の重複した日報が存在しないかをチェックします。
- **管理者機能**: 管理者は全従業員の日報を確認・管理することができます。

## 使用技術

- **Java**: メインのプログラミング言語として使用。
- **Spring Boot**: アプリケーションフレームワークとして使用。
- **MySQL**: データベース管理システムとして使用。
- **Docker**: コンテナ化技術として使用。
- **Thymeleaf**: テンプレートエンジンとして使用。
- **Spring Security**: 認証と認可のために使用。

## セットアップ手順

1. リポジトリをクローンします。

   ```sh
   git clone https://github.com/your-username/DailyReportSystemApplication.git
   cd DailyReportSystemApplication
   ```

2. Docker を使用して環境をセットアップします。

   ```sh
   docker-compose up -d
   ```

3. ブラウザで以下の URL にアクセスすることで、アプリケーションを使用できます。

   ```
   http://localhost:8080
   ```

4. ログイン情報:
   - 社員番号: 1, パスワード: kirataro
   - 社員番号: 2, パスワード: tanataro (社員番号 2 では権限がないためログインできません)

## ディレクトリ構造

```
db/
    Dockerfile
    mysql_setup.sql
    startup.sh
src/
    main/
        java/
            com/
                techacademy/
                constants/
                controller/
                entity/
                repository/
                service/
        resources/
            static/
              css/
              js/
            templates/
              common/
              employees/
              login/
              reports/
    test/
        java/
            com/
                techacademy/
                    controller/
                    service/
web/
    Dockerfile
    startup.sh
```

## 開発環境

- **Java**
- **Spring Boot**
- **MySQL**
- **Docker**

## 使用動画

以下のリンクから各機能の動作を確認できます（動画は後ほど追加予定です）。


# ログイン

https://github.com/user-attachments/assets/b2008be1-031c-424f-9eb7-3f1466399374

# 従業員 一覧のcreate

https://github.com/user-attachments/assets/bb0d7398-4bf9-4b2a-878e-228c874137ce


#従業員 一覧のDelete

https://github.com/user-attachments/assets/b886d8cb-0228-4076-ac23-5409130e4b14

#従業員 一覧のupdate

https://github.com/user-attachments/assets/919ed5e2-732b-4734-a466-f1e06680057e


# 日報 一覧のcreate

https://github.com/user-attachments/assets/11c4ccf6-ac9a-4e39-b782-9ecb5314ee32

# 日報 一覧のupdate,delete

https://github.com/user-attachments/assets/779a7cda-0ecc-4808-93ca-1655c199e62c



---

この README.md は、プロジェクトの目的、主要な機能、使用技術、セットアップ手順、ディレクトリ構造、開発環境を詳細に説明しています。ポートフォリオとして適切な情報を提供し、プロジェクトの理解を助けることを目的としています。
