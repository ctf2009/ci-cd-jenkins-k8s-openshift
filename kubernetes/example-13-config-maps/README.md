## Example 13 - Creating Config Maps

This example is based around the Kubernetes online documentation located at [https://kubernetes.io/docs/tasks/configure-pod-container/configure-pod-configmap/](https://kubernetes.io/docs/tasks/configure-pod-container/configure-pod-configmap/)

There are a number of different ways to create config maps. For example:

- From Directories
- From Files (Including multiple files)
- From Environment Files (Including multiple files)
- From Literals

This example will show how we can create config maps with Environment Variables and also by using files (such as an `application.yaml` file). See the Kubernetes documentation for more information on the other ways, including with multiple files

#### Viewing Config Maps

You can get all the config maps by running `kubectl get configmaps` and you can look at a config map in more detail by running `kubectl describe configmap <Name of ConfigMap>`

### Creating Overview

- Using `kubectl create configmap` imperative command
- Declaring a config map definition and running `kubectl create` command


#### Using Imperative Method 

The following shows how we can create a config map with a few Environment Variables in using Imperative Declaration

##### Using Literals

The following creates a config map with two variables we can later use as Environment Variables:

```
kubectl create configmap sample-config-1 \
--from-literal=GREETING='Hello from sample-config-1' \
--from-literal=ADDITIONAL_GREETING='Have a great day'
```

##### Using an Env-File

You can use an Env-File instead of declaring the Environment Variables one by one using the following:

```
kubectl create configmap sample-config-2 --from-env-file=environment_file
```

##### Using a Regular File

You can create config maps from regular files (e.g an `application.yaml` file for Spring. To do this, you can run the following:

```
kubectl create configmap sample-config-3 --from-file=application.yaml
```

With the last example, a key gets created. By default it is the name of the file, but we can change this if we want as follows:

```
kubectl create configmap sample-config-4 --from-file=my-config-file.yaml=application.yaml
```

#### Using Declarative Method

##### Environment Variables

The `config-map-declarative-1.yaml` file is an example of declaring a config map that stores Environment Variables. It is similar to the Imperative method using an Env-File. Run the following to create the config map

`kubectl create -f config-map-declarative-1.yaml`

##### Using a Regular File

Its much easier to creat a config map from a file using the Imperative approach. However it is possible to achieve the same result in a Declarative way.

The `config-map-declarative-2.yaml` shows an example on how to do this. You can decide the key name when creating it also. Run the following to create the config map

`kubectl create -f config-map-declarative-2.yaml`

If you run `kubectl describe configmap config-map-declarative-2` you will see that we have used a key of `application.yaml`

```
kubectl describe configmap config-map-declarative-2
Name:         config-map-declarative-2
Namespace:    default
Labels:       <none>
Annotations:  <none>

Data
====
application.yaml:
----
server:
  port: 9000

greeting: "Hello World from config-map-declarative-2. The entire configuration is a config-map"
additional_greeting: "Have a super day!"
Events:  <none>
```








