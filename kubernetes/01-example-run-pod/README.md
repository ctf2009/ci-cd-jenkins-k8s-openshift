## Example 1 - Run Pod 

To run a pod using the locally built image you can run the following:

`kubectl run sample --image=ctf/sample-app:latest --image-pull-policy=Never`

To view the deployment run: `kubectl get deployment sample`

```
kubectl get deployment sample
NAME     READY   UP-TO-DATE   AVAILABLE   AGE
sample   1/1     1            1           90s
```

To view the pods run: `kubectl get pods`

```
kubectl get pods             
NAME                      READY   STATUS    RESTARTS   AGE
sample-7b9c799d8f-h7j9q   1/1     Running   0          117s
```

To delete the deployment run the following: `kubectl delete deployment sample`