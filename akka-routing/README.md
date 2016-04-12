# Chapter 3 : Working with Akka Basic Tools
## Sending Messages via Router
Here can be found examples of actor routers, router pools and router groups, as well as demonstration of the Random routing strategy.

### RoundRobin routing strategy
In the video mention many different routing strategies. RoundRobin is one of those strategies. Code example can be found from `RoundRobin.scala`.

Router pool is created as following:

	val routerPool = system.actorOf(RoundRobinPool(3).props(Props[Worker]), "round-robin-pool")

This creates pool of 3 workers from `Worker` props with RoundRobin strategy and named as `round-robin-pool`.

Router group is created based on the configurations defined in `application.conf` file:

	/round-robin-group {
		router = round-robin-group
		routees.paths = ["/user/w1", "/user/w2", "/user/w3"]
	}
  
as following:

	val routerGroup = system.actorOf(FromConfig.props(), "round-robin-group")

More information about routers and routing strategies from [Akka official documentation](http://doc.akka.io/docs/akka/2.4.0/scala/routing.html).