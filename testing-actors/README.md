# Chapter 6 : Testing Actors
## How to Test an Actor?
This is simple example of how to test Akka Actors with [ScalaTest](http://www.scalatest.org/) and [TestKit](http://doc.akka.io/docs/akka/current/scala/testing.html) test frameworks.

## Note on ScalaTest version
I've used `ScalaTest` version `2.2.6`. This version was producing warning about conflicting packages with `sbt` version `0.13.8`. In order to resolve this issue I have to upgrade `sbt` to version `0.13.11` in `build.properties` file, which can be found under `project` directory.