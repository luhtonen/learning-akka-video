# Chapter 9: Working with Common Patterns in Akka
## Balancing Workload Across Nodes
Example on how to create workload balancing for Akka actors across all nodes. It implements Master/Worker pattern, where Worker actors may run on same node or on different nodes.

To see this pattern in action run `testkit`:

	sbt test
	
### Note regarding deprecated code
In `Worker` class was used deprecated code to get master actor reference from `ActorPath`:

	  val master = context.actorFor(masterLocation)

`actorFor` method is deprecated and `actorSelection` should be used instead:

	  val master = context.actorSelection(masterLocation)
