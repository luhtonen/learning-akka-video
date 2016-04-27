# Chapter 8: Working with Akka HTTP
In this chapter is explained how to work with Akka HTTP.

Here's what official [Akka HTTP documentation](http://doc.akka.io/docs/akka/current/scala/http/introduction.html) says:
> The Akka HTTP modules implement a full server- and client-side HTTP stack on top of *akka-actor* and *akka-stream*. It's not a web-framework but rather a more general toolkit for providing and consuming HTTP-based services. While interaction with a browser is of course also in scope it is not the primary focus of Akka HTTP.
 
Akka HTTP core (`akka-http-core`) is a release level, but following experimental packages are still in use: `akka-http-experimental`, `akka-http-jackson-experimental`, `akka-http-spray-json-experimental` and `akka-http-xml-experimental`.

## Contents
[Working with Client-side API](akka-http-client-side-api)  
[Working with Server-side API](akka-http-server-side-api)  
[Let's Implement a REST API](rest-api)  
[Let's Test Our REST API](rest-api)

## Projects dependencies for Akka HTTP
To be able to build and run examples in this chapter, at least following dependencies should be defined in `build.sbt` file:

	libraryDependencies ++= Seq(
	  "com.typesafe.akka" %% "akka-actor" % "2.4.4",
	  "com.typesafe.akka" %% "akka-stream" % "2.4.4",
	  "com.typesafe.akka" %% "akka-http-core" % "2.4.4",
	  "com.typesafe.akka" %% "akka-http-experimental" % "2.4.4",
	  "com.typesafe.akka" %% "akka-http-spray-json-experimental" % "2.4.4"
	)

## Deprecated code
In the examples on the videos used implicit dispatcher:

	implicit val ec = system.dispatcher

this is not necessary and is should not be used. Instead global implicit should be defined at the top of the scala source file as following:

	import scala.concurrent.ExecutionContext.Implicits.global