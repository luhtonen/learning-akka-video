# Chapter 3 : Working with Akka Basic Tools
## Replacing Actor Behavior via become/unbecome
This example demonstrates changing the state of the actor with `become()` and `unbecome()` functions. If using simple Actors without any extra traits, then it is very important to remember that order of message sending is very important.

For example if class UserStorage is defined as following:

	class UserStorage extends Actor
	
Then all messages sent when `UserStorage` is in state `disconnected` will be lost. For example in this code:

	userStorage ! Operation(DBOperation.Create, Some(User("Admin", "admin@packt.com")))
	userStorage ! Connect
	userStorage ! Disconnect

`DBOperation.Create` will not be processed, because `UserStorage` actor is in `disconnected` state at the time this message is sent and this message will be just discarded. 

One of the ways to fix this problem, is to change the order of the messages sending like following:

	userStorage ! Connect
	userStorage ! Operation(DBOperation.Create, Some(User("Admin", "admin@packt.com")))
	userStorage ! Disconnect

The other way is to use Stash trait like this:

	class UserStorage extends Actor with Stash
	
This trait enables message stashing to be processed later. Following code in disconnected state will stash all messages, not handled by other cases:

	case _ =>
		stash()

And when `Connect` message will be received messages can be unstashed by this function call:
	
	unstashAll()

## Replacing Actor Behavior via FSM
This is very interesting example of Finite State Machine (FSM) usage along with Akka Actors.