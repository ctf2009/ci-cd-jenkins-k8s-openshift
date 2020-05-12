## Example 15 - Secrets

Secrets are designed to help manage sensitive information a bit more securely. Storing sensitive data in a secret is much safer than declaring it in a pod. 

Storing information in secrets doesnt make them completely secure. By default they are just base64 encoded but it does mean that only pods that request them get them and they aren't written to disk where possible.

Secrets work in a very similar way to config maps. We can create them both an imperative and declarative manner. 

**Note:** If using a declarative approach, you have to ensure you provide base64 values. This example is only going to demonstrate the imperative approach. For information on how to use the declarative method, please refer to the documentation at [https://kubernetes.io/docs/concepts/configuration/secret/](https://kubernetes.io/docs/concepts/configuration/secret/)

### Creating Secrets from Literals

To create a secret using literals by running something like the following:

```
kubectl create secret generic sample-secret-1 \
--from-literal=GREETING='This is a secret greeting!'
```
You can update the secret by adding another literal and running the following

```
kubectl create secret generic sample-secret-1 \
--from-literal=GREETING='This is a secret greeting!' \
--from-literal=ADDITIONAL_GREETING='This is from sample-secret-1' --dry-run -o yaml | kubectl apply -f -
```

To see the secret including the base64 encoded value, you can run: 

`kubectl get secret sample-secret-1 -o yaml`

### Creating Secrets from Env Files

Similar to config maps, instead of declaring `--from-literal` for each Environment Variable, you can put them all into tha file and create a secret for each one.

Run the following to demonstrate this:

```
kubectl create secret generic sample-secret-2 \
--from-env-file=my-secret-envs
```

### Create Secrets from Files

You can create secrets from one of more files. You can run the following command to demonstrate this:

```
kubectl create secret generic sample-secret-3 \
--from-file=greeting-secret.txt \
--from-file=additional-greeting-secret.txt
```

Each secret is given a key of the filename that was provided. You can override this by using `--from-file=<KEY_NAME>=<FILE_NAME>`
