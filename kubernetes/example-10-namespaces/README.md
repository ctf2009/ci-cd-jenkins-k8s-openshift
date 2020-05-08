## Example 10 - Namespaces

Namespaces allow you to divide up cluster resources between multiple users or concerns. Up till now we have been using the default namespace. This example shows you how you can create your own.

You can create namespaces in a couple of different ways

- Use a definition file. e.g `kubectl create -f example-10/namespace-definition.yaml`
- Run the command `kubectl create namespace my-namespace`

To view the namespaces, run `kubectl get namespaces`

You can test the use of namespaces by running `kubectl create -f example-10/namespace-deployment-config.yaml`

**Note:** The above command assumes you have created the namespace `my-namespace` via one of the two options previously outlined.

Once deployed you will be able to check the deployment is in the `my-namespace` namespace by running `kubectl get all -n my-namespace` 

```
kubectl get all -n my-namespace
NAME                                         READY   STATUS    RESTARTS   AGE
pod/sample-app-deployment-5f94945777-9mqkg   1/1     Running   0          24s
pod/sample-app-deployment-5f94945777-t52b7   1/1     Running   0          24s
pod/sample-app-deployment-5f94945777-t67bt   1/1     Running   0          24s

NAME                                    READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/sample-app-deployment   3/3     3            3           24s

NAME                                               DESIRED   CURRENT   READY   AGE
replicaset.apps/sample-app-deployment-5f94945777   3         3         3       24s
```

When finished run `kubectl delete namespace my-namespace` to remove everything created in this example.

To set a namespace preference, run the following: `kubectl config set-context --current --namespace=<NAMESPACE_NAME>`

To verify this change, run the following: `kubectl config view --minify | grep namespace:`