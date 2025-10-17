# spring-boot-kotlin-with-jooq
以下のサイトに則って作成したプロジェクトになります。
https://quo-digital.hatenablog.com/entry/2024/03/22/143542

Windowsの場合
事前にDockerをインストールしてください。
https://docs.docker.com/desktop/setup/install/windows-install/



以下にアクセスします
http://localhost:8080/book/list

Windowsでの動作確認
dockerを起動
```
docker compose up
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
