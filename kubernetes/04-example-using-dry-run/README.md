## Example 4 - Generate YAML with Dry Run

We can generate YAML from a `kubectl` command using `dry-run` and use this as a basis to build on. 

For example, we can run the following command:
`kubectl run sample-app-pod --image=ctf/sample-app:latest --port=9000 --restart=Never --image-pull-policy=Never --dry-run -o yaml`

The output will look very similar to the `example-3/pod-definition.yaml` file