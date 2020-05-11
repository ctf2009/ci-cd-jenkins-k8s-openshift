## Example 14 - Using Config Maps

This example shows how you can use config maps with pod definitions.

### Using Config Maps as Environment Variables

We can use config map entries as Environment Variables in a couple of different ways. We can choose specific Environment Variables to use or we can use the entire config map to provide all the Environment Variables declared in it.

Before continuing, create the `sample-app-config-map-envs` by running the following:
`kubectl create -f sample-app-config-map-envs.yaml`


##### Selected Data in Config Map as Environment Variables

The `pod-definition-1.yaml` file sets an Environment Variable using the config map that was created earlier.

Deploy the pod by running `kubectl create -f pod-definition-1.yaml`

Once up, port forward from your local machine by running `kubectl port-forward sample-app-pod-1 9000`

In a new terminal window, curl the apps `greeting` endpoint and the response should show the GREETING value from the config map only. Other properties will be defaults

```
curl localhost:9000/greeting
Hello from sample-app-config-map-envs
```

When finished, stop the port forwarding by closing the terminal window or by hitting `ctrl + c`

##### All Data in Config Map as Environment Variables

The `pod-definition-2.yaml` shows how we can use the `envfrom` field to set all the data in a config map as Environment Variables.

Deploy the pod by running `kubectl create -f pod-definition-2.yaml`

Once up, port forward from your local machine by running `kubectl port-forward sample-app-pod-2 9000` 

In a new terminal window, curl the apps `greeting` endpoint and the response should show the value from the config map

```
curl localhost:9000/greeting
Hello from sample-app-config-map-envs
```

Here, all settings are overridden by the config map Environment Variables

When finished, stop the port forwarding by closing the terminal window or by hitting `ctrl + c`

### Using Config Map File and Volume Mount

We can create a config map from a file (or multiple files) and then use that config map in a volume so we can inject the file into the pod.

To test this, we will create a config map using the `application.yaml` file as follows

`kubectl create configmap sample-app-properties --from-file=application.yaml`

Once up, port forward from your local machine by running `kubectl port-forward sample-app-pod-3 9000`

In a new terminal window, curl the apps `greeting` endpoint and the response should show the value from the `application.yaml` file that was used to create the config map

```
curl localhost:9000/greeting
Hello! This is an example of setting the application.yaml file in a config map
```

**Note:** Spring will look in a `/config` subdirectory in the current directory for an properties file. See [https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-external-config-application-property-files](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-external-config-application-property-files) for more information. This is how Spring picks up `application.yaml` config map value.

When finished, stop the port forwarding by closing the terminal window or by hitting `ctrl + c`