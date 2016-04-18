# Chapter 7: Working with Akka Streams
Akka Stream is now part of standard Akka package and should be imported in `build.sbt` file as following:

	libraryDependencies ++= Seq(
	  "com.typesafe.akka" %% "akka-stream" % "2.4.4"
	)
	
## Contents
[Introduction to Akka Streams](akka-streams)  
[Reactive Tweets](reactive-tweets)  
Testing Streams  
Working with Graphs  
Working with Stream IO  

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