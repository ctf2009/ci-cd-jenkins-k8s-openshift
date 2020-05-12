## Example 8 - Using Environment Variables

This example shows how we can provide Environment Variables to our application with a pod definition file.

As our application so far has not been provided a Spring profile to use, it uses the default profile. When you curl the end point as in example 2 you will see the output `Hello World`

The `pod-definition-1.yaml` file has an Environment Variable set that will tell Spring to use the `alternative` profile which is built into the Sample App. The `alternative` profile is configured with a different greeting. If we have configured the pod defintion correctly we will be able to see this new greeting.

Run `kubectl create -f pod-definition-1.yaml`

If you check the logs from the pod using `kubectl logs sample-app-pod-1` you will see that the `alternative` profile is active:

```
kubectl logs sample-app-pod-1

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.2.6.RELEASE)

2020-05-08 04:27:01.996  INFO 1 --- [           main] com.ctf.sample.Application               : Starting Application on sample-app-pod-1 with PID 1 (/app/classes started by root in /)
2020-05-08 04:27:02.001  INFO 1 --- [           main] com.ctf.sample.Application               : The following profiles are active: alternative
2020-05-08 04:27:03.820  INFO 1 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 9000 (http)
2020-05-08 04:27:03.848  INFO 1 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2020-05-08 04:27:03.849  INFO 1 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.33]
2020-05-08 04:27:03.970  INFO 1 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2020-05-08 04:27:03.970  INFO 1 --- [           main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 1874 ms
2020-05-08 04:27:04.743  INFO 1 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2020-05-08 04:27:05.010  INFO 1 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 9000 (http) with context path ''
2020-05-08 04:27:05.014  INFO 1 --- [           main] com.ctf.sample.Application               : Started Application in 3.877 seconds (JVM running for 5.366)
```

If we port forward to the pod by running `kubectl port-forward sample-app-pod-1 9000` and then access the endpoint `curl localhost:9000/greeting` you will be able to see the alternative greeting:

```
curl localhost:9000/greeting
Hi Everybody!
```

We can also leverage Spring's ability to substitute Environment Variables into application properties.

The `pod-definition-2.yaml` contains a `GREETING` Environment Variable. The value of this will be set as the `greeting` property when the Application runs.

Run `kubectl create -f pod-definition-2.yaml`

If you run `kubectl logs sample-app-pod-2` you will see that the `default` profile is being used. However because we have set the `GREETING` Environment Variable, this will override the value set in the `application.yaml` file in the Sample App

Once the pod is up, run `kubectl port-forward sample-app-pod-2 8000:9000` to allow port forwarding to the pod. **Note:** This command forwards port 8000 on the local machine as this allows us to port forward both the first pod and the new pod at the same time via different local ports.

Accessing the endpoint `curl localhost:8000/greeting` will greet us with the greeting we provided in the pod defintion

```
curl localhost:8000/greeting
Howdy!
```

When finished with the example, you can remove all pods by running `kubectl delete pods -l app=sample-app`


