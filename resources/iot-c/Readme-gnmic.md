## SubscribeRestController
 - subscribe(SubscribeConfigureDTO) - /gnmi/subscribe
 - subscriptionPerTarget(filter) - /gnmi/subscription/per-target 
     - request (Filter.name, [List] Filter.TargetName)
     - response (TargetName, TargetPort SubscriptionRequest.Name, status, last_update_time)
 - subscriptionInfo(name) - /gnmi/subscription/info
 - subscriptionPerTargetInfo(name) - /gnmi/subscription/per-target/info

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
 - unsubscribe(target)
    - validate Subscription requests
    - store Subscription requests to db (status=RequestCancel)
    - send messages `subscribe-request-topic` for unsubscribe
 - unsubscribeAll(targets)
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
 - SubscriptionRequest.Name (create foreign key)
 - path  /ptp/1/current/offsetfrommaster
 - mode (SAMPLE, ON_CHANGE etc...)
 - sample interval

## SubscriptionPerTarget - Table
 - id: (incremental) PRIMARY-KEY
 - SubscriptionRequest.name (user define unique)
 - tag.id
 - Target.id 
 - status
 - last_update_time 
 - service-host-name
 - UNIQUE (SubscriptionRequest.name, Target.id) 

## tag - Table
 - id (incremental) PRIMARY-KEY
 - tagset STRING NULLABLE TRUE
 - tagrules STRING NULLABLE TRUE
 - UNIQUE (tagset, tagrules)

## Example for tags:
  prefix          = /ptp/instance-list/
  subscribePath   = /ptp/instance-list/*
  returnedPath    = /ptp/instance-list/1/current-ds/offset-from-master

- tagset=subnet=sub1,region=reg1,service=serviceA
  tagrules=  service=(1)(.*)  # This a regex to match:
  - 1 a number  1
  - 2 a string   
  resultTag      = service=serviceA

 - In this example the tags `subnet=sub1` and `region=reg1` will be added to the response message.
 - In this example the tag `service=serviceA` will be added only if the path is matching the regex rule with the service key 

## SubscriptionRequest - Table
 - id (incremental) PRIMARY-KEY
 - name (user define unique)
 - encoding (JSON, PROTO, BYTES etc...)
 - [SubscriptionList] - not an actual column - derived from the  Subscription Table
 - UNIQUE (name)

## Target - Table
 - id (incremental) PRIMARY-KEY
 - name (ip address or DNS name)
 - port (TCP port for the gRPC connections)
 - credentials (username password)
 - UNIQUE (name, port)

---
## Questions for Ziv
 - metadata labels 
 - processAllSubscriptionsForTTL 

## open points to discuss again 
 - tags
   tags max lenght
 - Who is updating the SubscriptionPerTarget.status 
   - SubscribeManager.unsubscribe()
   - SubscribeManager.updateSubscriptionStatus()
 - Cover tha case when we have 2 replicas and one goes down   

## validation
 - When you assign a SubscriptionRequest to target if the target already have another SubscriptionRequest 
   containing at least one equal Subscription.path then should be rejected
 - Add validation for the tags. The tags should not contain invalid characters
   we can use as reference the kubernetes label valid characters
   Example the `=` cannot be part of the key or value of the tag
  