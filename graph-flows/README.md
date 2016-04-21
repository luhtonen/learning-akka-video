# Chapter 7: Working with Akka Streams
## Working with Graphs
This simple example shows how to user Akka Streams Graph.

### Migration from experimental 1.0 to 2.4.x
Akka Streams is now in release stage and there are significant changes from `experimental` version `1.0` to version `2.4.x`. 

#### FlowGraph is removed
`FlowGraph` is removed from version 2.x and replaced with several other objects.

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