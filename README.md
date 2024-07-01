# Util

Various bits of Scala that could be useful for any project.

## Concurrency
LimitedExecution and implementations that can wrap functions that return futures and limit the number of
functions that are running.

## Hash
Hashing algorithms for various object types

## IO
IO-related classes.  Notably the package includes the function `stringToInputStream` that will create an InputStream
for a string that can greatly aid testing in this area.

## Models
Notably includes IdIterator for generating a unique sequence of ids.

## Path
DirectoryDeleter

## Time
ImplicitOrdering has ordering of LocalDateTime.

Now is a trait that gives the current date time.  AdvancingNow is an implementation useful for testing.

For setting a fixed time use:
```
import io.mdcatapult.util.time.nowUtc
val currentTime = nowUtc.now()
val time: Now = () => currentTime
```

## Logging
Includes an implementation of [HMRC's logback json logger](https://github.com/hmrc/logback-json-logger) licenced under [Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0).  
Build was unavailable for scala 2.13 so code is included directly. To use it add the class to your logback.xml file.
```xml
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="io.mdcatapult.util.logger.JsonEncoder"/>
    </appender>
```
To add arbitrary fields to the log output you need to use Logback [Mapped Diagnostic Contexts](http://logback.qos.ch/manual/mdc.html).
```scala
import org.slf4j.MDC
MDC.put("a-message", "my message")
MDC.put("another-message", """"{"a-key": "a-value"}"""")
```
You can add these fields at any time before you output the log message.

## Dependency Scanning

https://github.com/albuch/sbt-dependency-check

The sbt-dependency-check plugin can be used to create a HTML report under `target/scala-x.x/dependency-check-report.html`

```bash
sbt dependencyCheck
```

### License
This project is licensed under the terms of the Apache 2 license, which can be found in the repository as `LICENSE.txt`
