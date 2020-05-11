## Example 9 - Basic Deployments

Deployments take things a step further. They provide additional features such as the ability to perform rolling updates and rollbacks. Deployments use Replica Sets in order to manage rolling updates and can scale old pods down as new pods scale up.

Create the ReplicaSet using the file located in `example-9/deployment-config.yaml`

`kubectl create -f example-9/deployment-config.yaml`

Once deployed, you can run `kubectl get all -l app=sample-app` to view everything the Deployment created.

```
kubectl get all -l app=sample-app
NAME                                         READY   STATUS    RESTARTS   AGE
pod/sample-app-deployment-5f94945777-56pjf   1/1     Running   0          3m31s
pod/sample-app-deployment-5f94945777-78bmz   1/1     Running   0          3m31s
pod/sample-app-deployment-5f94945777-bg6rx   1/1     Running   0          3m31s

NAME                                    READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/sample-app-deployment   3/3     3            3           3m31s

NAME                                               DESIRED   CURRENT   READY   AGE
replicaset.apps/sample-app-deployment-5f94945777   3         3         3       3m31s
```

When finished, remove the deployment by running `kubectl delete deployment sample-app-deployment`