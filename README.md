# Backend oriented task (Java)

## Instructions
### Run the server

```
java -jar Backend.jar
```

### Query operation 1
```
curl http://localhost:8888/rate/usd/2023-04-20
```
Expected result: `{"rate":4.2024}`

### Query operation 2
```
curl http://localhost:8888/maxmin/gbp/100
```
Expected result: `{"min":5.2086,"max":5.4638}`

### Query operation 3
```
curl http://localhost:8888/majordiff/eur/200
```
Expected result: `{"maxDifference":0.0974}`


### Run the frontend

Double click `./Frontend/index.html`


## This repository structure
```
Dynatrace-Backend-Oriented-Task
├── Backend_src (IntelliJ IDEA project, tests, dependencies)
│   └── (...)
├── Frontend (Vanilla HTML+CSS+JS UI for the API)
│   └── (...)
└── Backend.jar
```

## Dependencies
* [json](https://mvnrepository.com/artifact/org.json/json/20230227)
* [jUnit](https://mvnrepository.com/artifact/junit/junit)
* [hamcrest](https://mvnrepository.com/artifact/org.hamcrest/hamcrest/2.2)