# Chapter 4: Akka Persistence
## Creating Persistent Actors

In this chapter experimenting with Akka Persistence, which requires extra packages to be added to the project `libraryDependencies` in `build.sbt` file:

	  "com.typesafe.akka" %% "akka-persistence" % "2.4.3",
	  "org.iq80.leveldb" % "leveldb" % "0.7",
	  "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8"
	  
Application configuration should be also added. `application.conf` file should be created into `src/main/resources` directory:

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

## Playing with a Persistent Actor
In this section described how to handle recovery process completion and how to create actor state shapshot.