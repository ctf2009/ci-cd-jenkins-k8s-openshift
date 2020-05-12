## Example 7 - Quotas

This example shows how we can apply resource quotas to namespaces 

First create a namespace by running the following command `kubectl create namespace my-namespace`

Next, create the Resource Quota by running the following: `kubectl create -f resource-quota.yaml`

You can view the Quote by running `kubectl describe quota compute-quota -n my-namespace`

Next, we will will create some pods as follows:

We can create the first pod as follows with no issues:

`kubectl run pod1 --image=nginx --namespace my-namespace --restart='Never' --limits='cpu=400m,memory=800Mi' --requests='cpu=200m,memory=600Mi'`

When we try to create the second pod, we get an error for exceeding the memory constraints

```
kubectl run pod2 --image=nginx --namespace my-namespace --restart='Never' --limits='cpu=400m,memory=800Mi' --requests='cpu=200m,memory=600Mi'

Error from server (Forbidden): pods "pod2" is forbidden: exceeded quota: compute-quota, requested: requests.memory=600Mi, used: requests.memory=600Mi, limited: requests.memory=1Gi
```
If we reduce the memory required for the second pod, we can successfully deploy it:

```
kubectl run pod2 --image=nginx --namespace my-namespace --restart='Never' --limits='cpu=400m,memory=200Mi' --requests='cpu=200m,memory=100Mi'
pod/pod2 created
```

If we try to deploy a third pod, we will get an error because we already have 2 pods in the namespace and the quota will not allow any more

```
kubectl run pod3 --image=nginx --namespace my-namespace --restart='Never' --limits='cpu=500m,memory=500Mi' --requests='cpu=250m,memory=150Mi'

Error from server (Forbidden): pods "pod3" is forbidden: exceeded quota: compute-quota, requested: pods=1, used: pods=2, limited: pods=2
```

You can remove everything created in this example by running `kubectl delete namespace my-namespace`