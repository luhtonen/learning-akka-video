# Chapter 7: Working with Akka Streams
## Reactive Tweets
This example shows how to stream, transform and print out tweets.

### Some notes
In original video Stream `Source` was created using following line of code:

	val source = Source(() => TwitterClient.retrieveTweets("#Akka"))
	
In Akka Stream version `2.4.4` this was causing `type mismatch` error:

	[error] ReactiveTweets.scala:16: type mismatch;
	[error]  found   : () => Iterator[twitter4j.Status]
	[error]  required: scala.collection.immutable.Iterable[?]
	[error]   val source = Source(() => TwitterClient.retrieveTweets("#Akka"))
	[error]                          ^
	[error] one error found
	[error] (compile:compileIncremental) Compilation failed

As this error message clearly say Source expects to receive `Iterable` from `scala.collection.immutable`. `TweeterClient` is returning `Iterator` from `scala.collection`. This problem can be resolved by calling `fromIterator()` function of `Source` object. So the line above should be fixed as following:

	val source = Source.fromIterator(() => TwitterClient.retrieveTweets("#Akka"))
