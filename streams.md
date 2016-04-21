# Chapter 7: Working with Akka Streams
Akka Stream is now part of standard Akka package and should be imported in `build.sbt` file as following:

	libraryDependencies ++= Seq(
	  "com.typesafe.akka" %% "akka-stream" % "2.4.4"
	)
	
## Contents
[Introduction to Akka Streams](akka-streams)  
[Reactive Tweets](reactive-tweets)  
[Testing Streams](stream-test)  
[Working with Graphs](graph-flows)  
[Working with Stream IO](stream-io)  

### Notes regarding deprecated functions
Following methods `actorSystem.shutdown()` and `actorSystem.awaitTermination()` are deprecated in Akka version `2.4.x`. So following code:

	actorSystem.shutdown()
	actorSystem.awaitTermination()
	
Should be replaced with the following code:

	import scala.concurrent.Await
	import scala.concurrent.duration._
	
	Await.result(actorSystem.terminate(), 10.seconds)
	
As well as following import is not working in newer version of Akka:
	
	import system.dispatcher

Following import should be used instead:

	import scala.concurrent.ExecutionContext.Implicits.global
	
### Official migration guides
Akka Streams went through experimental releases from `1.0` to `2.0.2` and finally ended up in official Akka release at the time of writing `2.4.4`.

[Migration Guide 1.0 to 2.x](http://doc.akka.io/docs/akka-stream-and-http-experimental/2.0.2/scala/migration-guide-1.0-2.x-scala.html)  
[Migration Guide 2.0.x to 2.4.x](http://doc.akka.io/docs/akka/2.4.4/scala/stream/migration-guide-2.0-2.4-scala.html)