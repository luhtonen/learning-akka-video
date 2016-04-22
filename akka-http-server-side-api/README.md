# Chapter 8: Working with Akka HTTP
## Working with Server-side API
In this sample shown how to create simple HTTP server with `Akka HTTP`. There are 2 levels of API for that: *low-level* and *high-level*. **Low-level** allows to create server with source, flow and sink on the HTTP connection level. **High-level** offers simple DSL to define routing handled by server.

### Note about used deprecated API
In the original video example is used deprecated API:

	Console.readLine()
	
It should be replaced with the following code (including import):

	import scala.io.StdIn
	
	StdIn.readLine()
	
