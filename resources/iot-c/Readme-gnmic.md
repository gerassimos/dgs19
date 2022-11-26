## GrpcClientChannelSubscriptions
 - createNewChannelAndStub
 - createStreamForSubscriptionList -> createStream (SubscriptionList, )
 - cancelStreamForSubscriptionList -> cancelStream
 - isConnected -> isChannelConnected
 - notifyWhenStateChanged (ManagedChannel) -> notifyWhenChannelStateChanged 
 - reConnectReStartDataCollection -> reConnectReCreateStreamsForAll
 - statusStreamStatusAll()
 - statusStreamStatus(id)

## GrpcClientHandler
 - createSubscribe
 - deleteSubscribe
 - statusSubscribe
 
## GnmiTestRestController - subscribe operations 
 - createSubscribe - /gnmi/test/create/subscribe
 - deleteSubscribe - /gnmi/test/cancel/subscribe
 - statusSubscribe - /gnmi/test/status/subscribe

