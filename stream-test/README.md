# Chapter 7: Working with Akka Streams
## Testing Streams
Here's examples on how to test streams with different techniques.

### Dependencies
Akka Streams have `TestKit` test framework for testing it. Here's list of dependencies you will need for testing Akka Streams:

	libraryDependencies ++= Seq(
	  "com.typesafe.akka" %% "akka-actor" % "2.4.4",
	  "com.typesafe.akka" %% "akka-stream" % "2.4.4",
	  "com.typesafe.akka" %% "akka-stream-testkit" % "2.4.4",
	  "org.scalatest" %% "scalatest" % "2.2.6" % "test",
	  "com.typesafe.akka" %% "akka-testkit" % "2.4.4" % "test"
	)
	
### Migration from experimental version 1.x to 2.4.x
In class `StreamActorSpec` have to change old code. Following `apply()` function was removed from `Source` object of Akka Stream:

	val source = Source(0.millis, 200.millis, Tick)
	
It was replaced by `tick()` function:

	val source = Source.tick(0.millis, 200.millis, Tick)
	
In class `StreamKitSpec` following call with empty parameter list was not working:

	sourceUnderTest.runWith(TestSink.probe[Int]())
	
`ActorSystem` argument should be passed explicitly:

	sourceUnderTest.runWith(TestSink.probe[Int](system))
	
In same class following code is causing test failure:

	sourceUnderTest.runWith(TestSink.probe[Int]())
    .request(2)
    .expectNext(2, 4)
    .expectComplete()
    
Test fails with the following failure message:

	java.lang.AssertionError: assertion failed: timeout (3 seconds) during expectMsg while waiting for OnComplete
	
It looks like `stream testkit` have a bug which causes `expectComplete()` to fail after `extectNext()` call. Here's the link to reported [issue](https://github.com/akka/akka/issues/19326).