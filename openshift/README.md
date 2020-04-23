# Openshift / Minishift
This directory will contain examples of deploying to Openshift including building, deploying and exposing. Also examples of using templates to build and deploy

## Environment
In order to try these examples, I decided to download and run a Minishift cluster. I chose to run it with Virtualbox and provided some additional flags on startup as follows:

`minishift start --vm-driver=virtualbox --memory=8GB`

Once Minishift has finished starting, it outputs the URL to the console along with username and password information.

### Some Helpful Commands

`minishift console` - Shows the console URL

`minishift oc-env` - Will ouput information on how to add `oc` to your path

`minishift status` - Outputs status information about the Minishift environment

`minishift stop` - Stops the VM but does not remove anything. Can be restarted

## Examples
The overall aim of these examples are to show how to deploy a Sample Application into Openshift in a few different ways and to use as many features as possible

- Deploying using `oc` commands
- Using deployment templates, service templates, build templates (S2I Build with a Jar)
- Using Secrets and Config Maps
- Using Environment Variables














