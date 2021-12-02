C:\Users\nddgd\Diploma\CMD folder\JourneyOfTheDay>gradlew -Dapp.datasource="mysql" -Dapp.db_address="localhost:3306" -Dapp.db_name="app" -Dapp.db_user="app" -Dapp.db_pass="pass" test

> Task :test

BankTest > testInvalidCardCredit() FAILED
    com.codeborne.selenide.ex.ElementShould at BankTest.java:205

BankTest > testInvalidHolderCardPayment() FAILED
    com.codeborne.selenide.ex.ElementShould at BankTest.java:195

BankTest > testInvalidCardPayment() FAILED
    com.codeborne.selenide.ex.ElementShould at BankTest.java:195

BankTest > testInvalidCodeCardPayment() FAILED
    com.codeborne.selenide.ex.ElementShould at BankTest.java:75

BankTest > testInvalidHolderCardCredit() FAILED
    com.codeborne.selenide.ex.ElementShould at BankTest.java:205

BankTest > testInvalidCodeCardCredit() FAILED
    com.codeborne.selenide.ex.ElementShould at BankTest.java:155

16 tests completed, 6 failed

> Task :test FAILED

FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':test'.
> There were failing tests. See the report at: file:///C:/Users/nddgd/Diploma/CMD%20folder/JourneyOfTheDay/build/reports/tests/test/index.html

* Try:
Run with --stacktrace option to get the stack trace. Run with --info or --debug option to get more log output. Run with --scan to get full insights.

* Get more help at https://help.gradle.org

BUILD FAILED in 1m 45s
3 actionable tasks: 1 executed, 2 up-to-date
