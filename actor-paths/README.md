# Chapter 3: Working with Akka basic tools
## ActorRef Versus Actor Path Versus Actor Selection
In this chapter exploring differences between `ActorRef`, actor path and selection. When actor is created for example with `system.actorOf` actually reference to this actor is returned `ActorRef`.

The method `actorSelection()` returns an `ActorSelection` rather than an `ActorRef`. An `ActorSelection` can be used to send a message to the actor referenced by it. However using this approach is slower and more resource intensive to resolve than using an `ActorRef`. Yet, `actorSelection()` is a nice facility because it can accept wildcard actor queries, which when resolved allows you to broadcast a message to any number of actors represented by the `ActorSelection`.

Interesting line in file `App.scala`:
	
	counter1 ! PoisonPill

`PoisonPill` is a message all Actors will understand, that when processed will terminate the Actor permanently.