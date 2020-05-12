## Example 1 - Run Pod 

To run a pod using the locally built sample-app image you can run the following:

`kubectl run sample-app-pod --image=ctf/sample-app:latest --port=9000 --restart=Never --image-pull-policy=Never`

To view the pods run: `kubectl get pods`

```
kubectl get pods             
NAME                      READY   STATUS    RESTARTS   AGE
sample-app-pod    1/1     Running   0          117s
```

Once the pod is running, port forward directly to the pod: `kubectl port-forward sample-app-pod 9000`

Open a new terminal window and type `curl localhost:9000/greeting` and you should get a response

```
curl localhost:9000/greeting
{
  "hostname": "sample-app-pod",
  "greeting": "Hello World",
  "comment": "This is the default profile comment"
}
```

When finished with the example, remove the pod by running the following: `kubectl delete pod sample-app-pod`

### Using a Pod Definition Files

Using YAML based configuration files provides additional benefits over using commands like in the previous examples. They can be stored in source control and provide a more verbose description of what they do which can be useful for new developers to understand

The `pod-definition.yaml` file will create a pod that is exactly the same as in the example above. 
You can create the pod in Kubernetes by running `kubectl create -f pod-definition.yaml`

```
kubectl create -f pod-definition.yaml
pod/sample-app-pod created
```

As in the previous example, you can use the `kubectl get pods` to verify the pod is running and the `kubectl port-forward sample-app-pod 9000` to port forward to the pod.

You will then be able to curl the endpoint and get the same response as the previous example above

When finished with the example, remove the pod by running the following: `kubectl delete pod sample-app-pod`