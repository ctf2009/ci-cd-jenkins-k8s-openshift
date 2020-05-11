## Example 7 - Replica Sets

ReplicaSets are are newer way to perform replication in Kubernetes. They ensure a specified number of pod replicas are running at a given time. As mentioned in Example 6, it is recommended to use a Deployment which configures a ReplicaSet as deployments provide other useful features. However we can still use a ReplicaSet without a Deployment and this example show how.

Create the ReplicaSet using the file located in `example-6/rs-definition.yaml`

`kubectl create -f example-7/rs-definition.yaml`

Once run, you can check the status of the ReplicaSet by running `kubectl get rs`

```
kubectl get rs
NAME            DESIRED   CURRENT   READY   AGE
sample-app-rs   3         3         3       9s
```

You can follow similar steps as in Example 6 to delete a pod and see another one get created in its place by the ReplicaSet.

When finished, remove the ReplicaSet by running `kubectl delete rs sample-app-rs`