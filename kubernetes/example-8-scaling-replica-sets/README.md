## Example 8 - Basic Scaling of Replica Sets

There are a number of ways to scale the number of pods in a Replica Set.
We will reuse the Replica Set defintion in example 7 fot this example.

- Update the `example-7/rs-definition.yaml` file and change the number of replicas. When done, use `kubectl replace -f example-7/rs-definition.yaml`
- Use `kubectl scale --replicas=<No Replicas> -f example-6/rs-definition.yaml`
- Use `kubectl scale --replicas=<No Replicas> replicaset sample-app-rs`

Once you have tried one of the above, use the `kubectl get rs` to check that the number of pods have changed. Note that in the last ways to scale, the file is NOT updated.

When finished, remove the ReplicaSet by running `kubectl delete rs sample-app-rs`