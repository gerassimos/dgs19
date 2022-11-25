## GrpcClientChannelSubscriptions
 - createNewChannelAndStub
 - createStreamForSubscriptionList -> createStream (SubscriptionList, )
 - cancelStreamForSubscriptionList -> cancelStream
 - isConnected -> isChannelConnected
 - notifyWhenStateChanged (ManagedChannel) -> notifyWhenChannelStateChanged 
 - reConnectReStartDataCollection -> reConnectReCreateStreamsForAll
 - statusStreamStatusAll()
 - statusStreamStatus(id)
 - 
## GnmiTestRestController - subscribe operations 
 - createSubscribe - /gnmi/test/create/subscribe
 - deleteSubscribe - /gnmi/test/delete/subscribe
 - statusSubscribe - /gnmi/test/status/subscribe

