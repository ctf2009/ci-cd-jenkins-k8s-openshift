# Kubernetes

The aim of this project is to show how to use Kubernetes through a series of examples which use a sample application to demonstrate behaviour

The examples should work on any Kubernetes cluster. There are many ways to get a simple cluster up and running including:

- Docker for Desktop included a single node Kubernetes cluster that can be enables
- Minikube
- K3s

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

An overview of the examples is shown below. Each example folder has its own README.md file with instructions to follow. The examples start off with the basics and build up to more complex concepts. 

A Kubernetes cheatsheet is available at [https://kubernetes.io/docs/reference/kubectl/cheatsheet/](https://kubernetes.io/docs/reference/kubectl/cheatsheet/) which provides common commands and an explanation of what they do.

Before continuing, please ensure you have built the sample applicaton which is available in the `sample-project` folder at the root of the entire repository. There is a README.md file in the sample app folder with instructions on how to build an image using the gradle Jib plugin.

You must have a locally built image named `ctf/sample-app` listed with a tag of `latest` before continuing with the examples

```
docker image ls
REPOSITORY                           TAG                 IMAGE ID            CREATED             SIZE
ctf/sample-app                       latest              30829a15755b        21 hours ago        143MB
```

Each example has a `README.md` file with instructions to follow

### Example 1 - Run Pod
[01-example-run-pod](01-example-run-pod/) shows how to run a simple pod / deployment using the `kubectl run` command

### Example 2 - Editing a Pod
[02-example-edit-pod](02-example-edit-pod/) shows how we can edit pods in a number of different ways

### Example 3 - Replication Controllers
[03-example-replication-controllers](03-example-replication-controllers/) shows how to configure Replication Controllers

### Example 4 - Replica Sets
[04-example-replica-sets](04-example-replica-sets/) shows how to use Replica Sets which are intended to superseed Replication Controllers

### Example 5 - Basic Deployments
[05-example-basic-deployments](05-example-basic-deployments/) demonstrates deployments and how to configure them

### Example 6 - Namespaces
[6-example-namespaces](6-example-namespaces/) shows how to create and use Namespaces

### Example 7 - Quotas
[7-example-namespace-quotas](7-example-namespace-quotas/) shows how we can use Resource Quotas in namespaces and how they work

### Example 8 - Environment Variables
[8-example-using-environment-variables](8-example-using-environment-variables/) shows how we can use Environment Variabes in our deployments

### Example 9 - Creating Config Maps
[9-example-creating-config-maps](9-example-creating-config-maps/) shows how to create Config Maps in a couple of different ways

### Example 10 - Using Config Maps
[10-example-using-config-maps](10-example-using-config-maps/) shows how to create Config Maps in a couple of different ways

### Example 11 - Creating Secrets
[11-creating-secrets](15-creating-secrets/) shows how to use secrets

### Example 12 - Using Secrets
[12-creating-secrets](12-creating-secrets/) shows how to use secrets

### Some Commands
kubectl describe pods  
kubectl describe pod \<pod name\>    
kubectl cluster-info
kubectl get nodes  
kubectl config current-context
kubectl config get-contexts
kubectl config use-context 
