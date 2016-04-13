# Chapter 5 : Working with Akka Cluster
## Building a Cluster
Sample project to build a simple actor cluster with one frontend and 3 backend actors.

## Adding Load Balancer to a Cluster Node
Sample project with load balancer of the cluster nodes.

One important note related to execution context. `context.system.scheduler.schedule()` function requires implicit context. If context is not defined then following error will occur:

	Cannot find an implicit ExecutionContext. You might pass
	 an (implicit ec: ExecutionContext) parameter to your method
	 or import scala.concurrent.ExecutionContext.Implicits.global.
	 
This error can be resolved with the following import as suggested in the error message:
	
	import scala.concurrent.ExecutionContext.Implicits.global

In our examples `context.dispatcher` is used:

	import context.dispatcher

For more information about Execution Context can be found from the official Akka documentation: [Dispatchers](http://doc.akka.io/docs/akka/current/scala/dispatchers.html) and [Futures](http://doc.akka.io/docs/akka/current/scala/futures.html).

## Creating a Singleton Actor in the Cluster
Sample project with Singleton Actor.

In the video wasn't mentioned configuration file, which is loaded by `ConfigFactory.load()` method:

	ConfigFactory.load("singleton")

This code looks for `singleton.conf` file from `src/main/resources` directory. Here's content of this file:

	akka {
	  loglevel = INFO

	  actor {
	    provider = "akka.cluster.ClusterActorRefProvider"
	  }

	  remote {
	    log-remote-lifecycle-events = off
	    netty.tcp {
	      hostname = "127.0.0.1"
	      port = 0
	    }
	  }

	  cluster {
	    seed-nodes = [
	      "akka.tcp://ClusterSystem@127.0.0.1:2551",
	      "akka.tcp://ClusterSystem@127.0.0.1:2552"]

	    auto-down-unreachable-after = 10s
	  }

	  persistence {
	    journal.plugin = "akka.persistence.journal.leveldb-shared"
	    journal.leveldb-shared.store {
	      # DO NOT USE 'native = off' IN PRODUCTION !!!
	      native = off
	      dir = "target/shared-journal"
	    }
	    snapshot-store.plugin = "akka.persistence.snapshot-store.local"
	    snapshot-store.local.dir = "target/snapshots"
	  }
	}

## Cluster Sharding
Simple example of how to use Cluster Sharding.