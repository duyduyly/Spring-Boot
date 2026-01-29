# Note

## Api Documentation

- Get All:
```crul
curl --location 'http://localhost:8080/initialize/demos'
```

- Get By Id:
```curl
curl --location 'http://localhost:8080/initialize/demos/1'
```

- Create:
```curl
curl --location 'http://localhost:8080/initialize/demos' \
--header 'Content-Type: application/json' \
--data '{
  "filePath": "/uploads/documents/demo-file.pdf",
  "fileExt": "pdf",
  "fileName": "demo-file.pdf",
  "entityId": "ENTITY-001",
  "source": "USER_UPLOAD"
}'
```

- Update:
```curl
curl --location --request PUT 'http://localhost:8080/initialize/demos/1' \
--header 'Content-Type: application/json' \
--data '{
  "filePath": "/uploads/documents/demo-file.pdf",
  "fileExt": "pdf",
  "fileName": "demo-file.pdf",
  "entityId": "ENTITY-002",
  "source": "USER_UPLOAD"
}
'
```

- Delete:
```curl
curl --location --request DELETE 'http://    localhost:8080/initialize/demos/1'
```