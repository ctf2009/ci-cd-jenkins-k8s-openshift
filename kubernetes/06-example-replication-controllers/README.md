## Example 6 - Replication Controllers

A ReplicationController ensures that a specified number of pod replicas are running at any one time. ReplicaSets via Deployments are now the recommended way to set up replication. ReplicationControllers are still used however and this example shows how.

Create the ReplicationController using the file located in `example-6/rc-definition.yaml`

`kubectl create -f example-6/rc-definition.yaml`

Once run, you can check the status of the ReplicationController by running `kubectl get rc`

```
kubectl get rc 
NAME            DESIRED   CURRENT   READY   AGE
sample-app-rc   3         3         3       10s
```

You can view the pods that have been created by running `kubectl get pods`

```
kubectl get pods
NAME                  READY   STATUS    RESTARTS   AGE
sample-app-rc-l4hlz   1/1     Running   0          81s
sample-app-rc-p592g   1/1     Running   0          81s
sample-app-rc-pvrsf   1/1     Running   0          81s
```

If you delete one of the pods, you will find that the ReplicationController will fire up another one to replace it. In the example below, when we delete pod `sample-app-rc-pvrsf`, a new pod named `sample-app-rc-lszgm` is created in its place.

```
kubectl get pods
NAME                  READY   STATUS    RESTARTS   AGE
sample-app-rc-l4hlz   1/1     Running   0          81s
sample-app-rc-p592g   1/1     Running   0          81s
sample-app-rc-pvrsf   1/1     Running   0          81s

kubectl delete pod sample-app-rc-pvrsf
pod "sample-app-rc-pvrsf" deleted

kubectl get pods                      
NAME                  READY   STATUS    RESTARTS   AGE
sample-app-rc-l4hlz   1/1     Running   0          3m5s
sample-app-rc-lszgm   1/1     Running   0          4s
sample-app-rc-p592g   1/1     Running   0          3m5s
```
When finished, remove the ReplicationController by running `kubectl delete rc sample-app-rc`