### How to Run

`mvn clean test`

### Description
In this test, I tried to use HashMap's resize operation to show you that HashMap is not thread-safe.
As in the [HashMap Javadoc](http://hg.openjdk.java.net/jdk8/jdk8/jdk/file/687fd7c7986d/src/share/classes/java/util/HashMap.java#l56), HashMap tries to
resize itself and build hash again when it is about to reach capacity by using some threshold.
During resize operation, there will be clean list and old items will be copied there. During this operation
there may be some null values if you try to do `HashMap#get`, since it is not ready yet.

In codebase, I simply put a value in hashmap in an index more than `1000`. On one thread, I tried to set values to hashmap (to trigger resize on HashMap),
and on main thread I tried to read value on certain index to check I have a null value due to resize operation.