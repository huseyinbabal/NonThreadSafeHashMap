### Description
This is a test case for showing HashMap is not thread-safe. There is a simple in-memory user database and we are trying to add users here concurrently. Since, it is concurrent environment, there should be write operations with same user id at the same time. This will cause an index overwrite operation which results in `user count != execution count`

### How to Run

`mvn clean install`

You will see a statement in the test results like `Execution Count: 1000, User Count: 999`
