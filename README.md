# spring-boot-kotlin-with-jooq
以下のサイトに則って作成したプロジェクトになります。
https://quo-digital.hatenablog.com/entry/2024/03/22/143542

Windowsの場合
事前にDockerをインストールしてください。
https://docs.docker.com/desktop/setup/install/windows-install/

## テスト実行
docker desktop を起動してからtestを実行
```
./gradlew test
```

## 動作確認方法
Windowsでの動作確認
dockerを起動
```
docker compose up
```

初回起動時には以下コマンドを実行しDBを作成(flywayで作成)
```
./gradlew build
```

アプリケーション起動
```
./gradlew runBoot
```

author登録
```
curl -X POST "http://localhost:8080/author" -H "Content-Type: application/json" -d "{\"name\": \"Taro Yamada\", \"birthDate\": \"1980-01-01\"}"
```

author更新
```
curl -X POST "http://localhost:8080/author/1" -H "Content-Type: application/json" -d "{\"name\": \"Jiro Yamada\", \"birthDate\": \"1980-01-02\"}"
```

book取得
```
curl -X GET "http://localhost:8080/author/1/books"
```

book登録
```
curl -X POST "http://localhost:8080/book" -H "Content-Type: application/json" -d "{\"title\": \"Kotlin入門\", \"price\": 1980, \"publishStatus\": \"PUBLISHED\", \"authorIds\": [1, 2]}"
```

book更新
```
curl -X PUT "http://localhost:8080/book/1" -H "Content-Type: application/json" -d "{\"title\": \"Kotlin完全ガイド\", \"price\": 2200, \"publishStatus\": \"PUBLISHED\", \"authorIds\": [1]}"
```
