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

The error is because we are trying to use an image that does not exist. We can fix this in one of two ways

- Edit the pod definition yaml file and change the image to be `ctf/sample-app:latest` and then run `kubectl apply -f example-5/wrong-image-pod-definition.yaml`

- Use `kubectl edit pod sample-app-pod` which will throw up an editor where we can change the image to `ctf/sample-app:latest` before saving and exiting

Once we have performed one of the above actions, the pod will successfully run

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

If you delete one of the pods, you will find that the ReplicationController will fire up another one to replace it. In the example below, when we delete pod `sample-app-rc-pvrsf`, a new pod named `sample-app-rc-lszgm` is created in its place

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

You can follow similar steps as in Example 6 to delete a pod and see another one get created in its place by the ReplicaSet

When finished, remove the ReplicaSet by running `kubectl delete rs sample-app-rs`

## Example 8 - Basic Scaling of Replica Sets

There are a number of ways to scale the number of pods in a Replica Set

- Update the `example-6/rs-definition.yaml` file and change the number of replicas. When done, use `kubectl replace -f example-6/rs-definition.yaml`
- Use `kubectl scale --replicas=<No Replicas> -f example-6/rs-definition.yaml`
- Use `kubectl scale --replicas=<No Replicas> replicaset sample-app-rs`

Once you have tried one of the above, use the `kubectl get rs` to check that the number of pods have changed. Note that in the last ways to scale, the file is NOT updated

When finished, remove the ReplicaSet by running `kubectl delete rs sample-app-rs`

## Example 9


## Example 10


## Example 11


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
