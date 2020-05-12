## Example 2 - Edit POD

This example shows how to edit a pod configuration.

First run the pod definition file located at `wrong-image-pod-definition.yaml` 

`kubectl create -f wrong-image-pod-definition.yaml`

When running the `kubectl get pods` command you will see that there is an error

```
kubectl get pods
NAME             READY   STATUS              RESTARTS   AGE
sample-app-pod   0/1     ErrImageNeverPull   0          4s
```

The error is because we are trying to use an image that does not exist. We can fix this in one of two ways.

- Edit the pod definition yaml file and change the image to be `ctf/sample-app:latest` and then run `kubectl apply -f wrong-image-pod-definition.yaml`

- Use `kubectl edit pod sample-app-pod` which will throw up an editor where we can change the image to `ctf/sample-app:latest` before saving and exiting.

Once we have performed one of the above actions, the pod will successfully run.

When finished, remove the pod by running: `kubectl delete pod sample-app-pod`