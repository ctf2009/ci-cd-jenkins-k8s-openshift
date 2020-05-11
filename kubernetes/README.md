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

Each example has a `README.md` file with instructions to follow

### Example 1 - Run Pod
[01-example-run-pod](01-example-run-pod/) shows how to run a simple pod / deployment using the `kubectl run` command

### Example 2 - Run Pod and Port Forward
[02-example-run-pod-port-forward](02-example-run-pod-port-forward/) is a small extension to Example 1 and shows how we can use the `kubectl port-forward` command to port forward to a single pod

### Example 3 - Using Pod Definition YAML 
[03-example-pods-with-yaml](03-example-pods-with-yaml/) shows how to write a pod definition file in YAML

### Example 4 - Using the Dry Run Command
[04-example-using-dry-run](04-example-using-dry-run/) shows how we can use `--dry-run` to test commands and generate YAML definitions

### Example 5 - Editing a Pod
[05-example-edit-pod](05-example-edit-pod/) shows how we can edit pods in a number of different ways

### Example 6 - Replication Controllers
[06-example-replication-controllers](06-example-replication-controllers/) shows how to configure Replication Controllers

### Example 7 - Replica Sets
[07-example-replica-sets](07-example-replica-sets/) shows how to use Replica Sets which are intended to superseed Replication Controllers

### Example 8 - Scaling Replica Sets
[08-example-scaling-replica-sets](08-example-scaling-replica-sets/) shows how we can scale Replica Sets using the `kubectl scale` command

### Example 9 - Basic Deployments
[09-example-basic-deployments](09-example-basic-deployments/) demonstrates deployments and how to configure them

### Example 10 - Namespaces
[10-example-namespaces](10-example-namespaces/) shows how to create and use Namespaces

### Example 11 - Quotas
[11-example-namespace-quotas](11-example-namespace-quotas/) shows how we can use Resource Quotas in namespaces and how they work

### Example 12 - Environment Variables
[12-example-using-environment-variables](12-example-using-environment-variables/) shows how we can use Environment Variabes in our deployments

### Example 13 - Creating Config Maps
[13-example-config-maps](13-example-config-maps/) shows how to create Config Maps in a couple of different ways

### Example 14 - Using Config Maps
[14-example-using-config-maps](14-example-using-config-maps/) shows how to create Config Maps in a couple of different ways

### Example 15 - Secrets
[15-secrets](15-secrets/) shows how to use secrets

### Some Commands
kubectl describe pods  
kubectl describe pod \<pod name\>    
kubectl cluster-info
kubectl get nodes  
kubectl config current-context
kubectl config get-contexts
kubectl config use-context 
