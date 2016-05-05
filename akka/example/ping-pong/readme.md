#Execution steps for this ping-pong example
- Step1: open two sbt interactive shell.
- Step2: execute example.RemotingPongySystem in one shell
- Step3: execute example.RemotingPingySystem in the other shell

#Results
##Output for the pong shell
```
> run
Multiple main classes detected, select one to run:

 [1] example.RemotingPingySystem
 [2] example.RemotingPongySystem
 
Enter number: 2

[info] Running example.RemotingPongySystem 
creating pongy system
[INFO] [05/04/2016 17:00:52.081] [run-main-0] [akka.remote.Remoting] Starting remoting
[INFO] [05/04/2016 17:00:52.236] [run-main-0] [akka.remote.Remoting] Remoting started; listening on addresses :[akka.tcp://PongyDimension@127.0.0.1:24321]
[INFO] [05/04/2016 17:00:52.239] [run-main-0] [akka.remote.Remoting] Remoting now listens on addresses: [akka.tcp://PongyDimension@127.0.0.1:24321]
finished creating pongy system
[INFO] [05/04/2016 17:00:55.719] [PongyDimension-akka.actor.default-dispatcher-3] [akka.tcp://PongyDimension@127.0.0.1:24321/user/pongy] Got a ping -- ponging back!
[INFO] [05/04/2016 17:00:55.721] [PongyDimension-akka.actor.default-dispatcher-3] [akka.tcp://PongyDimension@127.0.0.1:24321/user/pongy] pongy going down
[ERROR] [05/04/2016 17:01:00.469] [PongyDimension-akka.remote.default-remote-dispatcher-5] [akka.tcp://PongyDimension@127.0.0.1:24321/system/endpointManager/reliableEndpointWriter-akka.tcp%3A%2F%2FPingyDimension%40127.0.0.1%3A24567-0/endpointWriter] AssociationError [akka.tcp://PongyDimension@127.0.0.1:24321] <- [akka.tcp://PingyDimension@127.0.0.1:24567]: Error [Shut down address: akka.tcp://PingyDimension@127.0.0.1:24567] [
akka.remote.ShutDownAssociation: Shut down address: akka.tcp://PingyDimension@127.0.0.1:24567
Caused by: akka.remote.transport.Transport$InvalidAssociationException: The remote system terminated the association because it is shutting down.
]
[INFO] [05/04/2016 17:01:07.262] [PongyDimension-akka.remote.default-remote-dispatcher-5] [akka.tcp://PongyDimension@127.0.0.1:24321/system/remoting-terminator] Shutting down remote daemon.
[INFO] [05/04/2016 17:01:07.264] [PongyDimension-akka.remote.default-remote-dispatcher-5] [akka.tcp://PongyDimension@127.0.0.1:24321/system/remoting-terminator] Remote daemon shut down; proceeding with flushing remote transports.
[INFO] [05/04/2016 17:01:07.284] [PongyDimension-akka.actor.default-dispatcher-3] [akka.remote.Remoting] Remoting shut down
[INFO] [05/04/2016 17:01:07.285] [PongyDimension-akka.remote.default-remote-dispatcher-13] [akka.tcp://PongyDimension@127.0.0.1:24321/system/remoting-terminator] Remoting shut down.
[success] Total time: 40 s, completed May 4, 2016 5:01:07 PM
```

##Output for the ping shell
```
> run
Multiple main classes detected, select one to run:

 [1] example.RemotingPingySystem
 [2] example.RemotingPongySystem

Enter number: 1

[info] Running example.RemotingPingySystem 
creating runner system
[INFO] [05/04/2016 17:00:55.241] [run-main-0] [akka.remote.Remoting] Starting remoting
[INFO] [05/04/2016 17:00:55.403] [run-main-0] [akka.remote.Remoting] Remoting started; listening on addresses :[akka.tcp://PingyDimension@127.0.0.1:24567]
[INFO] [05/04/2016 17:00:55.405] [run-main-0] [akka.remote.Remoting] Remoting now listens on addresses: [akka.tcp://PingyDimension@127.0.0.1:24567]
finished creating runner system
[INFO] [05/04/2016 17:00:55.723] [PingyDimension-akka.actor.default-dispatcher-3] [akka.tcp://PingyDimension@127.0.0.1:24567/user/runner] got a pong from another dimension.
[INFO] [05/04/2016 17:01:00.430] [PingyDimension-akka.remote.default-remote-dispatcher-5] [akka.tcp://PingyDimension@127.0.0.1:24567/system/remoting-terminator] Shutting down remote daemon.
[INFO] [05/04/2016 17:01:00.434] [PingyDimension-akka.remote.default-remote-dispatcher-5] [akka.tcp://PingyDimension@127.0.0.1:24567/system/remoting-terminator] Remote daemon shut down; proceeding with flushing remote transports.
[INFO] [05/04/2016 17:01:00.488] [PingyDimension-akka.actor.default-dispatcher-4] [akka.remote.Remoting] Remoting shut down
[INFO] [05/04/2016 17:01:00.490] [PingyDimension-akka.remote.default-remote-dispatcher-6] [akka.tcp://PingyDimension@127.0.0.1:24567/system/remoting-terminator] Remoting shut down.
[success] Total time: 17 s, completed May 4, 2016 5:01:00 PM
```
