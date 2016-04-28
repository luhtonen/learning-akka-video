# Chapter 9: Working with Common Patterns in Akka
## Throttling Messages
In this example shown how to employ a *message throttler*. This example is based on a simple implementation of a throttling actor, the `TimerBasedThrottler`, which is provided by akka `contrib` module.

Here's how official Akka documentation suggest to use `contrib` modules:
> ### Suggested Way of Using these Contributions
Since the Akka team does not restrict updates to this subproject even during otherwise binary compatible releases, and modules may be removed without deprecation, it is suggested to copy the source files into your own code base, changing the package name. This way you can choose when to update or which fixes to include (to keep binary compatibility if needed) and later releases of Akka do not potentially break your application.

In this example `TimerBasedThrottler.scala` is a copy from Akka contribution. Source code for contribution modules can be found on [Akka GitHub](https://github.com/akka/akka/tree/master/akka-contrib).