## Example 3 - Pods with YAML based Configuration

Using YAML based configuration files provides additional benefits over using commands like in the previous examples. They can be stored in source control and provide a more verbose description of what they do which can be useful for new developers to understand

An example pod definition is located in the `example-3` folder. You can creat the pod in Kubernetes by running `kubectl create -f example-3/pod-definition.yaml`

```
kubectl create -f example-3/pod-definition.yaml
pod/sample-app-pod created
```

To verify the pod has been created, run `kubectl get pods`

```
kubectl get pods         
NAME             READY   STATUS    RESTARTS   AGE
sample-app-pod   1/1     Running   0          65s
```

You can use similar steps as the previous example to port forward to the pod and curl the endpoint.

When done, remove the pod by running `kubectl delete pod sample-app-pod`