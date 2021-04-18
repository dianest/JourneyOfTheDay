#### **How to run tests**

1. docker-compose -f docker-compose.yml up -d

java -jar -Dspring.profiles.active=mysql artifacts\aqa-shop.jar

gradlew -Dapp.datasource="mysql" test

java -jar -Dspring.profiles.active=postgres artifacts\aqa-shop.jar

gradlew -Dapp.datasource="postgres" test
