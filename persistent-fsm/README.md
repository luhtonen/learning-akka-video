# Chapter 4: Akka Persistence
## Persistence FSM
Akka Persistence with Finite State Machine (FSM).

Following dependencies are required in `build.sbt` file:

	libraryDependencies ++= Seq(
	  "com.typesafe.akka" %% "akka-actor" % "2.4.3",
	  "com.typesafe.akka" %% "akka-persistence" % "2.4.3",
	  "org.iq80.leveldb" % "leveldb" % "0.7",
	  "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8"
	)

As well as application.conf file in src/main/resources directory:

	akka {
	  persistence {
	    journal {
	      plugin = "akka.persistence.journal.leveldb",
	      leveldb {
	        dir = "target/example/journal",
	        native = false
	      }
	    },
	    snapshot-store {
	      plugin = "akka.persistence.snapshot-store.local",
	      local {
	        dir = "target/example/snapshots"
	      }
	    }
	  }
	}
	
## Persistence Query
This is experimental feature in Akka Persistence and it requires extra dependencies:

	"com.typesafe.akka" %% "akka-persistence-query-experimental" % "2.4.3",
	"com.typesafe.akka" % "akka-stream-experimental_2.11" % "2.0.4"
