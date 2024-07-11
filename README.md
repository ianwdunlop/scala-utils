# Util

Various bits of Scala that could be useful for any project.

## Concurrency
LimitedExecution and implementations that can wrap functions that return futures and limit the number of
functions that are running.  
One use case is to restrict the number of database writes (or reads) that you can execute at a time. For example,
let's say we have a db `collection` and we want to delete a document from it.
```scala
val writeLimiter = SemaphoreLimitedExecution.create(10) // Only 10 writes at a time using this writeLimiter would be allowed
writeLimiter(collection, s"Delete document ${foundDoc.doc._id}") {_.deleteOne(equal("_id", foundDoc.doc._id))}.toFutureOption()
```

## Hash
Hashing algorithms for various object types
```scala
val text = "some text to hash"
val md5Hash = md5(text.getBytes)
```

## IO
IO-related classes.  Includes the function `stringToInputStream` that will create a UTF-8 based InputStream
for a string that can greatly aid testing in this area. The `MagicNumberFilterInputStream` can be used to return an empty input stream if 
the file in question contains a particular "Magic Number" that you want to exclude eg. If you want to ignore any gzip files but
they may have been named wrong then you can check it with the magic number `1f 8b`.
```scala
val text = "RDX2 is to be matched" // The magic number we are looking for is RDX2
val in = stringToInputStream(text)
val magicNumberInputStream = MagicNumberFilterInputStream
      .toTruncateAnyWith(
        List("RDX2", "RDA2").map(_.getBytes)
      )(in)
val bufferSize = text.getBytes.length
val buffer = new Array[Byte](bufferSize)
val bytesRead = magicNumberInputStream.read(buffer, 0, buffer.length)
bytesRead == -1 // True
```

## Models
Notably includes IdIterator for generating a unique sequence of ids.
```scala
val iter = new SeqIdIterator(IndexedSeq("first", "second", "third", "fourth", "fifth"))
iter.next() // "first"
iter.next() // "second"

val uuidIterator = SeqIdIterator.from(UuidIdIterator, 4) // Generate a sequence of 4 random UUIDs
```

## Path
DirectoryDeleter
```scala
DirectoryDeleter.deleteDirectories(Seq(File("/home/user/scratch/delete-this"), File("/home/user/scratch/also-delete-this"))) // Deletes a sequence of directories and their child dirs
```

## Time
ImplicitOrdering has ordering of LocalDateTime.

`Now` is a trait that gives the current date time.  `AdvancingNow` can be used to advance the time by a millisecond -  useful for testing. ImplicitOrdering sorts a sequence of `LocalDateTime` into order.

For setting a fixed time use:
```scala
import io.mdcatapult.util.time.nowUtc
val currentTime = nowUtc.now()
val time: Now = () => currentTime

import io.doclib.util.time.ImplicitOrdering.localDateOrdering
val times =
  Seq(
    LocalDateTime.parse("2019-10-01T12:00:00"),
    LocalDateTime.parse("2019-10-01T12:00:01"),
    LocalDateTime.parse("2019-10-01T11:59:59"),
    LocalDateTime.parse("2019-10-01T12:00:00"),
    LocalDateTime.parse("2019-10-01T12:00:03"),
  )

times.sorted should be (Seq(times(2), times(3), times.head, times(1), times(4)))

val startTime = LocalDateTime.parse("2019-10-01T12:00:00")
val now = new AdvancingNow(startTime)
now.now() // Advance the time by 1 millisecond
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

## Source

SourceReader can extract text from a Source using a StreamReader.
The main StreamReader is TikaTextExtractingStreamReader that will use Tika.

Tika isn't completely configurable in how it handles separated value files
which leads to an interesting bug concerning a UTF_8 character that represents
no character (BOM).  We want to ignore this character but Tika throws an exception, therefore the custom class SeparatedValueStreamReader is used instead.

```scala
val reader = new TikaTextExtractingStreamReader()
val source = Source.fromFile(new File("sample.pdf")) // Read a PDF file
val text = reader.readText(source)

val source = Source.fromFile(new File("sample.doc")) // Read a word doc
val text = reader.readText(source)
```

## Dependency Scanning

https://github.com/albuch/sbt-dependency-check

The sbt-dependency-check plugin can be used to create a HTML report under `target/scala-x.x/dependency-check-report.html`

```bash
sbt dependencyCheck
```

## Publishing the package
Uses the sbt plugin [sbt-github-packages](https://github.com/djspiewak/sbt-github-packages) to publish the package. Set the
env vars `GITUB_USERNAME`, `GITHUB_PACKAGE_REPO` & `GITHUB_TOKEN` to publish it.

## License
This project is licensed under the terms of the Apache 2 license, which can be found in the repository as `LICENSE.txt`
