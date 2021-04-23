#### **How to run tests**

First we need to start all our docker container. In order to do this we run docker-compose file. Flag -f let us specify the file, flag -d runs containers in the detached mode. 

**docker-compose -f docker-compose.yml up -d**

Then we need to start SUT. This is done via regular Java command for launching jar files. As a parameter we pass profile which will be used for testing.

 **java -jar -Dspring.profiles.active=mysql artifacts\aqa-shop.jar**

The with the help of Gradle wrapper we launch our tests, passing the database that we want to use as a parameter.

**gradlew -Dapp.datasource="mysql" -Dapp.db_address="localhost:3306" -Dapp.db_name="app" -Dapp.db_user="app" -Dapp.db_pass="pass" test**


Next step is to stop running SUT. After this we can lauch it again with another profile to test with different data source.

**java -jar -Dspring.profiles.active=postgres artifacts\aqa-shop.jar**

Then staring tests again with different database

**gradlew -Dapp.datasource="postgresql" -Dapp.db_address="localhost:5432" -Dapp.db_name="app" -Dapp.db_user="app" -Dapp.db_pass="pass" test**
