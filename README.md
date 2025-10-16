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
curl -X POST http://localhost:8080/author -H "Content-Type: application/json" -d "{\"name\": \"Taro Yamada\", \"birthDate\": \"1980-01-01\"}"
```

author更新
```
curl -X POST http://localhost:8080/author/1 -H "Content-Type: application/json" -d "{\"name\": \"Jiro Yamada\", \"birthDate\": \"1980-01-02\"}"
```
