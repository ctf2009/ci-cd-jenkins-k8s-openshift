## Example 2 - Run Pod and Access Endpoint

This example shows how we can check the application is actually up. Run the following:

`kubectl run sample-app-pod --image=ctf/sample-app:latest --port=9000 --restart=Never --image-pull-policy=Never`

Once the pod is running, port forward directly to the pod: `kubectl port-forward sample-app-pod 9000`

Open a new terminal window and type `curl localhost:9000/greeting` and you should get a response

```
curl localhost:9000/greeting
Hello World
```

Finally, remove the pod by running the following: `kubectl delete pod sample-app-pod`