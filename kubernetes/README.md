# Kubernetes

The purpose of this Readme is to document the process of running the sample application in Kubernetes

I am using a simple Kubernetes cluster that can be enabled via Docker Desktop. This provides a single node Kubernetes cluster that can be used to run the examples.

## Kubernetes Dashboard

You can install a Dashboard to better see whats going on inside your Kubernetes cluster. Please refer to [https://github.com/kubernetes/dashboard](https://github.com/kubernetes/dashboard) on how to install this.

Before attempting to access the dashboard, run `kubectl proxy` in a new terminal window

The dashboard should then be available at [http://localhost:8001/api/v1/namespaces/kubernetes-dashboard/services/https:kubernetes-dashboard:/proxy/](http://localhost:8001/api/v1/namespaces/kubernetes-dashboard/services/https:kubernetes-dashboard:/proxy/)

You will need to follow the instructions to log in. The yaml files you will need have already been created in the `dashboard` directory

Apply them as follows

```
kubectl apply -f dashboard/dashboard-admin-user.yaml
kubectl apply -f dashboard/dashboard-cluster-role-binding.yaml
```

Then run the following to obtain a token

`kubectl -n kubernetes-dashboard describe secret $(kubectl -n kubernetes-dashboard get secret | grep admin-user | awk '{print $1}')`

# Examples

A number of examples are shown below, starting from the most basic and building up to demonstrate more complex concepts

A Kubernetes cheatsheet is available at [https://kubernetes.io/docs/reference/kubectl/cheatsheet/](https://kubernetes.io/docs/reference/kubectl/cheatsheet/)

Before continuing, please ensure you have built the sample applicaton and that there is a local image available to use. Please refer to the README in the `sample-project` folder for building instructions. 

Once build, if you locally run `docker image ls` then there should be an image named `ctf/sample-app` listed with a tag of `latest`

```
docker image ls
REPOSITORY                           TAG                 IMAGE ID            CREATED             SIZE
ctf/sample-app                       latest              30829a15755b        21 hours ago        143MB
```

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

## Example 4 - Generate YAML with Dry Run

We can generate YAML from a `kubectl` command using `dry-run` and use this as a basis to build on. 

For example, we can run the following command:
`kubectl run sample-app-pod --image=ctf/sample-app:latest --port=9000 --restart=Never --image-pull-policy=Never --dry-run -o yaml`

The output will look very similar to the `example-3/pod-definition.yaml` file

## Example 5 - Edit POD

This example shows how to edit a pod configuration.

First run the pod definition file located at `example-5/wrong-image-pod-definition.yaml` 

`kubectl create -f example-5/wrong-image-pod-definition.yaml`

When running the `kubectl get pods` command you will see that there is an error

```
kubectl get pods
NAME             READY   STATUS              RESTARTS   AGE
sample-app-pod   0/1     ErrImageNeverPull   0          4s
```

The error is because we are trying to use an image that does not exist. We can fix this in one of two ways.

- Edit the pod definition yaml file and change the image to be `ctf/sample-app:latest` and then run `kubectl apply -f example-5/wrong-image-pod-definition.yaml`

- Use `kubectl edit pod sample-app-pod` which will throw up an editor where we can change the image to `ctf/sample-app:latest` before saving and exiting.

Once we have performed one of the above actions, the pod will successfully run.

When finished, remove the pod by running: `kubectl delete pod sample-app-pod`

## Example 6 - Replication Controllers

A ReplicationController ensures that a specified number of pod replicas are running at any one time. ReplicaSets via Deployments are now the recommended way to set up replication. ReplicationControllers are still used however and this example shows how.

Create the ReplicationController using the file located in `example-6/rc-definition.yaml`

`kubectl create -f example-6/rc-definition.yaml`

Once run, you can check the status of the ReplicationController by running `kubectl get rc`

```
kubectl get rc 
NAME            DESIRED   CURRENT   READY   AGE
sample-app-rc   3         3         3       10s
```

You can view the pods that have been created by running `kubectl get pods`

```
kubectl get pods
NAME                  READY   STATUS    RESTARTS   AGE
sample-app-rc-l4hlz   1/1     Running   0          81s
sample-app-rc-p592g   1/1     Running   0          81s
sample-app-rc-pvrsf   1/1     Running   0          81s
```

If you delete one of the pods, you will find that the ReplicationController will fire up another one to replace it. In the example below, when we delete pod `sample-app-rc-pvrsf`, a new pod named `sample-app-rc-lszgm` is created in its place.

```
kubectl get pods
NAME                  READY   STATUS    RESTARTS   AGE
sample-app-rc-l4hlz   1/1     Running   0          81s
sample-app-rc-p592g   1/1     Running   0          81s
sample-app-rc-pvrsf   1/1     Running   0          81s

kubectl delete pod sample-app-rc-pvrsf
pod "sample-app-rc-pvrsf" deleted

kubectl get pods                      
NAME                  READY   STATUS    RESTARTS   AGE
sample-app-rc-l4hlz   1/1     Running   0          3m5s
sample-app-rc-lszgm   1/1     Running   0          4s
sample-app-rc-p592g   1/1     Running   0          3m5s
```
When finished, remove the ReplicationController by running `kubectl delete rc sample-app-rc`

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

## Example 8 - Basic Scaling of Replica Sets

There are a number of ways to scale the number of pods in a Replica Set.
We will reuse the Replica Set defintion in example 7 fot this example.

- Update the `example-7/rs-definition.yaml` file and change the number of replicas. When done, use `kubectl replace -f example-7/rs-definition.yaml`
- Use `kubectl scale --replicas=<No Replicas> -f example-6/rs-definition.yaml`
- Use `kubectl scale --replicas=<No Replicas> replicaset sample-app-rs`

Once you have tried one of the above, use the `kubectl get rs` to check that the number of pods have changed. Note that in the last ways to scale, the file is NOT updated.

When finished, remove the ReplicaSet by running `kubectl delete rs sample-app-rs`

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

## Example 11 - Quotas

This example shows how we can apply resource quotas to namespaces 

First create a namespace by running the following command `kubectl create namespace my-namespace`

Next, create the Resource Quota by running the following: `kubectl create -f example-11/resource-quota.yaml`

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

## Example 12


## Example 13


### Some Commands

  
kubectl describe pods  
kubectl describe pod \<pod name\>    
kubectl cluster-info
kubectl get nodes  
kubectl config current-context
kubectl config get-contexts
kubectl config use-context 
