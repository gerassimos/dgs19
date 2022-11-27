## SubscribeRestController
 - subscribe() - /gnmi/subscribe - SubscriptionCfgNewDTO
 - unsubscribe() - /gnmi/unsubscribe - SubscriptionCfgNewDTO
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
 - unsubscribeAll(target)
     - go over all the subscriptions and set status=RequestCancel
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

## Subscription - Table
 - id (incremental)
 - id (path-mode)
 - SubscriptionRequestId (SubscriptionRequest.Name)
 - path  /ptp/1/current/offsetfrommaster
 - mode (SAMPLE, ON_CHANGE etc...)
 - sample interval

## SubscriptionPerTarget - Table
 - id: <SubscriptionRequest.Name-targetID>
 - SubscriptionRequestId (SubscriptionRequest.Name)
 - name (user define unique)
 - tagsetId
 - list of Subscription(path list) ??? test this 
 - encoding (JSON, PROTO, BYTES etc...)
 - targetID (use the address <targetIP:PORT> as targetId) //Ask Ziv ???
 - status
 - last_update_time 
 - service-host-name

## tag
 - id (incremental)
 - tagset (subnet=sub1,region=reg1)
 - tagrules (regex string) /ptp/1

## SubscriptionRequest - Table
 - name (user define unique)
 - encoding (JSON, PROTO, BYTES etc...)

## Target - Table
 - id (incrementalID or <ip:port>) ???
 - name (ip address or DNS name)
 - port (TCP port for the gRPC connections)
 - credentials (username password)

  
---
Questions for Ziv
 - metadata labels 
 - tables
 - processAllSubscriptionsForTTL 

## open points to discuss again 
 - validation
   tags max lenght
   SubscriptionMode
 - tags
 - Who is updating the SubscriptionPerTarget.status 
   - SubscribeManager.unsubscribe()
   - SubscribeManager.updateSubscriptionStatus()
 - Cover tha case when we have 2 replicas and one goes down  
  