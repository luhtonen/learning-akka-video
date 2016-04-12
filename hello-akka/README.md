# Chapter 1: Exploring the Actor World
This is brief introduction to Akka and Actor model with traditional `hello-world` example project.

# Building Akka project
In this video serie all Akka projects are developed using [Scala programming language](http://www.scala-lang.org/). There are several build tools used for building Scala projects: [Maven](https://maven.apache.org/), [Gradle](http://gradle.org/) and [SBT](http://www.scala-sbt.org/). SBT is the mostly used tool for Scala projects and in the following examples this tool is used to build all example projects.

Basic SBT build settings go in a file called `build.sbt`, located in the project’s base directory. All project will have similar configuration defined in this file:

	name := "hello-akka"

	version := "1.0"

	scalaVersion := "2.11.8"

	libraryDependencies ++= Seq(
	  "com.typesafe.akka" %% "akka-actor" % "2.4.0"
	)
	
* `name` defines the name of the project. This should be unique for every project.
* `version` defines the project version. In our example it is not important and it have same value in all projects.
* `scalaVersion` defines the version of scala to be used to build the project. This also will be the same for all projects.
* `libraryDependencies` is a list of project dependendies. Since this serie is talking about Akka all projects will require at least one dependency: `"com.typesafe.akka" %% "akka-actor" % "2.4.0"`. Which says that it requires `com.typesafe.akka` package and `akka-actor` artifact in this package of version `2.4.0`. `%%` between package and artifact tells SBT to automatically search for artifact for defined Scala version. In this example it will resolve it to `akka-actor_2.11`.

Some of the projects will require extra configurations and additional dependencies. Those will be descussed in those particular projects.

To run project execute following command in the project root directory:

	sbt run
	
This will compile and run the project. For more information about building read SBT documentation.

