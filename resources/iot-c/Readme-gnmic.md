## SubscribeRestController
 - subscribe() - /gnmi/subscribe
 - unsubscribe() - /gnmi/unsubscribe
 - subscriptionStatus() - /gnmi/subscription-status
 - subscriptionStatistics() - /gnmi/subscription-statistics ???

## SubscribeTestRestController - (for dev only - to test GrpcClientHandler directly)
 - subscribe - /gnmi/test/subscribe
 - unsubscribe - /gnmi/test/unsubscribe
 - subscriptionStatus - /gnmi/test/subscription-status
 - subscriptionStatistics - /gnmi/test/subscription-statistics ???

## SubscribeManager
 - subscribe() 
   - validate Subscription requests ???
   - store Subscription requests to db (status=RequestCreate)
   - send message to `subscribe-request-topic` (one message per SubscriptionRequest/target key=targetId) 
 - unsubscribe()
    - validate Subscription requests
    - store Subscription requests to db (status=RequestCancel)
    - send messages `subscribe-request-topic` for unsubscribe
 - getSubscriptionStatus() - get subscription status from db - ???  what to return ???
 - updateSubscriptionStatus() get message from `subscribe-monitor-topic` -> update DB
 - deleteAllSubscriptionsAndScheduleRecreation()
   - executed on startup
   - send message to `all` services `topic-???` to execute disconnectAndDeleteClientAll
   - set status to Recreate
 - processAllSubscriptionsForRecreate()
   - executed via cron scheduler
   - executed with lock on sub 
   - executed subscribe() for all Subscriptions in Recreate status
 - processAllSubscriptionsForTTL()
   - executed via cron scheduler
   - executed with lock
   - executed subscribe() for all Subscriptions of which:
     currentTime - isAliveTimeFromDB > TTL (time to live)
     The TTL is related to the schedule period of the getStreamStatusAllTargets()
     The TTL = schedule period + safety margin 10%
     Ask Ziv ??? 
     Could be that we can avoid this if we can relay on the stubError  

## SubscriptionStatus - enum
 - RequestCreate  
 - RequestCancel
 - RequestCancelled (delete from DB)
 - Alive(connected and streaming)
 - StreamError ??? -> need to schedule a restart/recreate of the Subscription request  
 - TargetFailure ??? -> need to wait until the target is alive again
 - Recreate -> ??? Delete all clients and recreate new ones 
                   Set this when the service is started/restarted 
                   Needed for replica services to rebalance/redistribute the load 

## SubscribeGrpcClientHandler
 - clientMap Map<tarhetId - SubscribeGrpcClient>
 - will handle message from listener of the `subscribe-request-topic` topic
 - subscribe(SubscriptionCfgDTO.SingleTarget) 
   - ignore if already exist
   - create chanel if is the first Subscription of the target 
   - create Subscription
   - send Subscription state change Alive(connected and streaming)
 - unsubscribe(SubscriptionCfgDTO.SingleTarget)
 - disconnectAndDeleteClient()
 - disconnectAndDeleteClientAll()
 - getStreamStatusAllTargets() 
   - get Stream Status from tha clients and send message to subscribe-monitor-topic
   - executed via cron scheduler
 - notifyTargetStateChangedAsync() 
 - notifyStreamStateChangedAsync()  
   
## SubscribeGrpcClient
 - streamMap Map<SubscriptionRequestId - Stream>
 - createStream(SubscriptionRequest) -> will send SubscribeResponse to `pm-data-topic` 
 - cancelStream(SubscriptionRequest)
 - isTargetConnected() - based on channel connected state
 - getStreamStatusAll()
 - getStreamStatus(id)
 - notifyTargetStateChanged() ??
 - notifyStreamStateChanged() ??
## GrpcClientChannelSubscriptions -> SubscribeGrpcClient
 - createNewChannelAndStub
 - createStreamForSubscriptionList -> createStream(SubscriptionRequest)
 - cancelStreamForSubscriptionList -> cancelStream(SubscriptionRequest)
 - isConnected -> isTargetConnected
 - disconnectTarget() - needed in case of rebalance
 - notifyWhenStateChanged (ManagedChannel) -> notifyTargetStateChanged
 - reConnectReStartDataCollection -> reConnectReCreateStreamsForAll
 - getStreamStatusAll()
 - getStreamStatus(id)

---
Questions for Ziv
 - metadata labels 
 - tables
 - processAllSubscriptionsForTTL 
  