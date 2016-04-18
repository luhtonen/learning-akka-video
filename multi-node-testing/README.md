# Chapter 6 : Testing Actors
## Multi Node Testing
In this example testing actors running on different nodes and in this particular example on different JVMs.

### Changes to build confiugration
Since SBT version `0.13.2` `Project.defaultSettings` is deprecated. `Defaults.coreDefaultSettings` should be used instead along with plugins configurations.

### TODO
Running test produces lots of serialisation warnings, which are related to use of default Java serializer. 

One error is reported on system shutdown:

	[JVM-1] [ERROR] [04/18/2016 16:08:31.495] [MultiNodeSample-akka.remote.default-remote-dispatcher-6] [akka.tcp://MultiNodeSample@localhost:61045/system/endpointManager/reliableEndpointWriter-akka.tcp%3A%2F%2FMultiNodeSample%40localhost%3A61046-0/endpointWriter] AssociationError [akka.tcp://MultiNodeSample@localhost:61045] -> [akka.tcp://MultiNodeSample@localhost:61046]: Error [Shut down address: akka.tcp://MultiNodeSample@localhost:61046] [
	[JVM-1] akka.remote.ShutDownAssociation: Shut down address: akka.tcp://MultiNodeSample@localhost:61046
	[JVM-1] Caused by: akka.remote.transport.Transport$InvalidAssociationException: The remote system terminated the association because it is shutting down.
	
It need to be investigated to find nicer way to shutdown remote actors.