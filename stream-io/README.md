# Chapter 7: Working with Akka Streams
## Working with Stream IO
Here's can be found simple example on how to use Akka Stream IO API.

### Notes about range creation
In the video sample code in `WriteStream` object in `isPrime()` function on the last line was created the range from number `2` to `n - 1` with the following code: 

	(2 to (n - 1)) 

For the situations when range need to be created until the certain number excluding actual number `Scala` have `until` keyword. So example above can be written as following:

	(2 until n)
	
It might not save the typing but it will make the intension of the code more clear.

### Notes about usage of toMat() function
In the video's original code in `WriteStream` object `fileSink` was created using toMat() function with manually written function:

	val fileSink = Flow[Int]
     .map(i => ByteString(i.toString))
     .toMat(sink)((_, bytesWritten) => bytesWritten) 
 
Where last part `((_, bytesWritten) => bytesWritten)` is this manually written function, which selects only right side argument and discard left side argument.

`toMat()` ScalaDoc suggest to use internally optimized `Keep.left` and `Keep.right` combiners:
> It is recommended to use the internally optimized `Keep.left` and `Keep.right` combiners where appropriate instead of manually writing functions that pass through one of the values.

In the example about to select only right side argument `Keep.right` should be used instead of the function:

	val fileSink = Flow[Int]
     .map(i => ByteString(i.toString))
     .toMat(sink)(Keep.right)

This syntax is more clear and as `ScalaDoc` suggested this is more optimized code.

### Migration notes
File Streams have been changed a lot from experimental version 1.0.

#### Sink.synchronousFile() is removed
The `Sink.synchronousFile()` function is removed and replaced with `FileIO.toFile()` function.

The following original code from `WriteStream` object

	val sink = Sink.synchronousFile(new File("target/prime.txt"))

does not work anymore and should be replaced with the new code

	val sink = FileIO.toFile(new File("target/prime.txt"))
	
#### Source.synchronousFile() is removed
The `Source.synchronousFile()` function is removed and replaced with `FileIO.fromFile()` function.

The following original code from `ReadStream` object

	val source = Source.synchronousFile(logFile)

does not work anymore and should be replaced with the new code

	val source = FileIO.fromFile(logFile)
	
#### FlowGraph is removed
As in previous section was already mentioned `FlowGraph` is removed and should be replaced with `GraphDSL`.

The following code from original video source:
	
	val g = FlowGraph.closed(fileSink, consoleSink)((file, _) => file) { implicit builder =>
	  (file, console) =>
	    import FlowGraph.Implicits._

	  val broadCast = builder.add(Broadcast[Int](2))

	  source ~> broadCast ~> file
              broadCast ~> console
              
	}.run()
	
must be migrated as following:

	val g = RunnableGraph.fromGraph(GraphDSL.create(fileSink, consoleSink)(Keep.left) { implicit builder =>
     (file, console) =>
       import GraphDSL.Implicits._

       val broadCast = builder.add(Broadcast[Int](2))

       source ~> broadCast ~> file
                 broadCast ~> console

       ClosedShape
	}).run()
	
Note the use of `(Keep.left)` in `GraphDSL.create()` function call instead of manually defined function `((file, _) => file)`.