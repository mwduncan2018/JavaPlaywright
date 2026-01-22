
# JavaPlaywright

#### This is a test automation framework.

#### Test automation using Playwright, Java, TestNG, Cucumber, and Allure Report

#### React site under test -> [ReactDuncanSafeApp](https://github.com/mwduncan2018/ReactDuncanSafeApp)

# Maven
```
// Execute All Tests
mvn clean test

// Execute Feature File
mvn test '-Dcucumber.filter.tags=@fuzzyMatching'
```

# Docker
```
// Build Image
docker build -t java-playwright .

// Execute All Tests
docker run --rm -v ${PWD}/allure-results:/app/allure-results -v ${PWD}/target/videos:/app/target/videos --add-host=host.docker.internal:host-gateway java-playwright

// Execute Feature File
docker run --rm `
  -v "${PWD}/allure-results:/app/allure-results" `
  -v "${PWD}/target/videos:/app/target/videos" `
  --add-host=host.docker.internal:host-gateway `
  java-playwright `
  mvn test '-Dcucumber.filter.tags=@fuzzyMatching'
```

# Generate Allure Report
```
allure serve allure-results
```
