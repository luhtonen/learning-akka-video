# Chapter 2: Playing with Actors
This chapter introduces Actor system, hierarchical structure, components and lifecycle. Examples of this chapter show how to create actor with props, how to communicate with the actors as well as actor supervision strategy and monitoring capability.

## Running examples
It is possible to define project's main class in `build.sbt` file like following:
	
	mainClass := org.elu.akka.Creation

But this is not required. In all examples in this series `mainClass` is not defined and SBT tries to detect main class. Those are Scala classes or objects, which extends `scala.App` trait. If multiple main classes are detected, SBT show warning and ask to select class to run:
	
	[warn] Multiple main classes detected.  Run 'show discoveredMainClasses' to see the list

	Multiple main classes detected, select one to run:

	 [1] org.elu.akka.Creation
	 [2] org.elu.akka.Monitoring
	 [3] org.elu.akka.Supervision
	 [4] org.elu.akka.TalkToActor

	Enter number: 
	
Just press the number of the main class to run it.

## Notes about Scala traits and case classes
In most of the examples messages are defined in the combination of `sealed trait` and `case class` or `case object`.

`sealed trait` or `sealed class` can be extended only in the same source file as its declaration. `sealed trait` is usually used in combination with case classes or case objects for pattern matching and allow the compiler to perform exhaustiveness checking on the match expression. In simpler words, this means the compiler will shout at us if we miss out a case in our structural recursion.

`case class` and `case object` are used to define value object, which can be used in pattern matching as case option. Difference between class and object is that class must have at least one parameter, for example in `TalkToActor.scala`:
	
	sealed trait RecorderMsg
	case class NewUser(user: User) extends RecorderMsg

When there is not need to pass the parameters to the case class `case object` should be used instead, for example in `ActorCreation.scala`:

	sealed trait PlayMsg
	case object StopMusic extends PlayMsg
	case object StartMusic extends PlayMsg
  	
Use of no-arg case classes, for example like following `case class StopMusic extends PlayMsg`, is deprecated and may lead to unexpected results. For example following pattern matching wouldn't work: `case StopMusic => println("I don't want to stop music")`. This deprecation warning can be fixed by defining empty parameter list for the case class like following `case class StopMusic() extends PlayMsg`, but using `case object` is preferred.

## Other notes
In many `App` objects or classes before terminating the Akka system can be seen following line:

	Thread.sleep(100)
	
This tells the application to way for a specified amount of milliseconds (in some cases it is 100 ms some times it is 1000ms) to give the Akka time to process all messages. If you try to remove those lines, you might see that some messages were not processed, because `ActorSystem` is terminated before those messages were processed.