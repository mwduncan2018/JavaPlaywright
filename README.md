# JavaPlaywright

#### This is a test automation framework.

#### Test automation using Playwright, Java, TestNG, Cucumber, and Allure Report

#### React site under test -> [ReactDuncanSafeApp](https://github.com/mwduncan2018/ReactDuncanSafeApp)

# Maven Execute
```
// Execute all tests
mvn clean test

// Execute a specific feature file
mvn test -Dcucumber.filter.tags="@fuzzyMatching"
```

## Docker Build & Execute
```
docker build -t java-playwright .

docker run --rm -v ${PWD}/allure-results:/app/allure-results -v ${PWD}/target/videos:/app/target/videos --add-host=host.docker.internal:host-gateway java-playwright
```

## Generate Allure Report
```
allure serve allure-results
```


