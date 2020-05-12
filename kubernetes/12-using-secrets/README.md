## Example 12 - Using Secrets

Secrets are used in a very similar manner to how config maps are. The only real differences are around the ref names:

- Use `secretKeyRef` instead of `configMapKeyRef` for using a specific key in a secret

- Use `secretRef` instead of `configMapRef` for providing the entire secret in `envFrom`

- Use `secret` and `secretName` instead of `configMap` and `name` with volumes

### Secret Key Ref

First create a secret by running the following:

```
kubectl create secret generic sample-app-secret-1 \
--from-literal=GREETING='This is a secret greeting!' \
--from-literal=ADDITIONAL_GREETING='This an additional secret greeting!'
```

Once done, run the following to create a pod that uses the `GREETING` key only as an environment variable:

`kubectl create -f pod-definition-1.yaml`

Once up and running, run `kubectl port-forward sample-app-pod-1 9000` and then use another terminal to curl the `greeting` endpoint. 

```
curl localhost:9000/greeting
This is a secret greeting!
```

When finished, stop the port forwarding by using `ctrl + c`

### Secret Ref

We will use the same secret above but use entire secret so that environment variables will be created for both keys

Create a pod that uses `envFrom` to use all the keys as environment variables by running the following:

`kubectl create -f pod-definition-2.yaml`

Once up and running, run `kubectl port-forward sample-app-pod-2 9000` and then use another terminal to curl the `greeting` endpoint. 

```
curl localhost:9000/greeting
This is a secret greeting!
```

When finished, stop the port forwarding by using `ctrl + c`

### Volumes

For this example we will store the `application.yaml` file as a secret and use a volume to mount it.

First create a new secret containing the `application.yaml` file:

```
kubectl create secret generic sample-app-secret-2 \
--from-file=application.yaml
```

Then run the pod using the following command:

`kubectl create -f pod-definition-3.yaml`

Once up and running, run `kubectl port-forward sample-app-pod-3 9000` and then use another terminal to curl the `greeting` endpoint. 

```
curl localhost:9000/greeting
Hello from an application.yaml file created as a secret and mounted as a volume
```

When finished, stop the port forwarding by using `ctrl + c`

### Tidy Up

When finished with the example, remove all pods by running:

`kubectl delete pods -l app=sample-app`

Then clear up all secrets:

`kubectl delete secrets sample-app-secret-1 sample-app-secret-2`









