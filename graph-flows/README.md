# Chapter 7: Working with Akka Streams
## Working with Graphs
This simple example shows how to user Akka Streams Graph.

### Migration from experimental 1.0 to 2.4.x
Akka Streams is now in release stage and there are significant changes from `experimental` version `1.0` to version `2.4.x`. 

#### FlowGraph is removed
`FlowGraph` is removed from version 2.x and replaced with several other objects.

Official [Migration Guide](http://doc.akka.io/docs/akka-stream-and-http-experimental/2.0.2/scala/migration-guide-1.0-2.x-scala.html#FlowGraph_class_and_builder_methods_have_been_renamed) describes **Update procedure**:
> 1. Search and replace all occurrences of `FlowGraph` with `GraphDSL`.
2. Replace all occurrences of `GraphDSL.partial()` or `GraphDSL.closed()` with `GraphDSL.create()`.
3. Add `ClosedShape` as a return value of the builder block if it was `FlowGraph.closed()` before.
4. Wrap the closed graph with `RunnableGraph.fromGraph` if it was `FlowGraph.closed()` before.

The following code from the old experimental version:

	val g = FlowGraph.closed() { implicit builder: FlowGraph.Builder[Unit] =>
	  import FlowGraph.Implicits._

	  // snipped code here
	}
	
Should be replaced with the following code:

	val g = RunnableGraph.fromGraph(GraphDSL.create() { implicit builder: GraphDSL.Builder[NotUsed] =>
	  import GraphDSL.Implicits._

	  // snipped code
	  
	  ClosedShape
	})
	
Old graph builder code `FlowGraph.Builder[Unit]` is in new version replaced with `GraphDSL.Builder[NotUsed]` code. 

Old implicits import `import FlowGraph.Implicits._` is replaced with new `import GraphDSL.Implicits._`. 

Old runnable closed flow graph creation function `FlowGraph.closed()` is replaced with factory function `RunnableGraph.fromGraph()` and explicit returned shape at the end of the creation function `ClosedShape`.