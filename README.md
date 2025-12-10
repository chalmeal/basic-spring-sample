**basic-spring-sample**

## はじめに
本リポジトリはシンプルかつ迅速なプロジェクトスタートを目指したスタートアッププロジェクトです。小〜中規模のアプリケーション向けに利用できます。

## アーキテクチャ
```
├── src
|　　　├── main
|     |     ├── java
|     |     |     └── sample
|     |     |            ├── batch                              # バッチ
|     |     |            ├── controller                         # プレゼンテーション層
|     |     |            ├── dto                                # DTO
|     |     |            ├── entity                             # エンティティ層
|     |     |            ├── repository                         # データアクセス層
|     |     |            ├── service                            # アプリケーション層
|     |     |            └── SampleApplication.java             # アプリケーションエントリーポイント
|     |     └── resources
|　　　└── test
|　　　      ├── user
|　　　      └── TestHelper.java
├── build.gradle
└── settings.gradle
```

## 起動
> 本プロジェクトはDevcontainerで動作を想定しています。

**前提**
* Visual Studio Code(vscode)がインストールされていること
* vscodeの拡張機能に「Dev Container」がインストールされていること
* 「Docker for Desktop」がインストールされていること

**手順**
1. プロジェクトのホームから、「<> Code」ボタンを押下
2. HTTPSのURLをコピー
3. vscode上で任意のフォルダにクローン
4. vscodeで、クローンしたリポジトリを開く
5. vscodeの左下の青いボタン（「><」のようなボタン）を押下
6. vscode上部に入力フォームが表示され、項目が複数出るため、「Reopen in Container」を押下
7. 少し待機した後、vscode下部に「Java: Ready」となっている箇所があることを確認する
8. 左端メニューバーからデバッグ（Debug）を押下し、「Run cammelia-api」の▶️を押下
9. 少し待機した後、「Started CamelliaApplication」が表示すれば起動完了

### Swagger UI
アプリケーション起動後、以下のURLにアクセスして動作確認可能
```
http://localhost:8080/swagger-ui/index.html
```

### 動作確認
#### 1. ログイン
下記のAPIでログインを行う。
> auth-controller -> /api/auth/login

| メールアドレス | パスワード |
| ------------ | -------- |
| taro.yamada@example.com | Passw0rd |

> ログインAPIで返されたaccess_tokenを「2. Authorizationヘッダー設定」で利用する。

#### 2. Authorizationヘッダー設定
認可が必要なAPIを動作させるために、Authorizationヘッダーを設定する。

* SwaggerUI上部の「Authorize」を押下
* Valueに「1. ログイン」で取得したaccess_tokenを設定しAuthorizedを押下
* user-controller -> /api/user/{user_id}のAPIで動作確認
    * user_idに「taro.yamada」を入力し、Execute
* ステータスコード200と、レコードが返されればOK