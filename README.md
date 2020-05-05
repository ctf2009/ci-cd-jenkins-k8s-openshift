# CI/CD, Jenkins, Kubernetes and Openshift
This repo contains examples related to the following

- Deploying to Kubernetes directly, using config-maps, secrets etc
- Using Openshift with Jenkins and Jenkinsfile

It also contains commands used to test the examples

This repo is mainly for testing new Jenkinsfile changes and overall behaviour of deployments and for POC's and demonstrations

## Repo Status

#### Current Status:
- Sample Application built
- README for Sample Application completed
- Introduced Jib tool to build Docker Images
- Kubernetes Examples in Progress

#### Next Step
- Continue Kubernetes Examples

## Requirements
In order to run the examples there are a number of requirements and recommendations

### Docker
In order to build the Sample App image, you need access to a running Docker daemon locally. I chose to install Docker Desktop as is provides this and also enables the setup up a single node Kubernetes cluster which is required for some of the examples

### Kubernetes
You need access to a Kubernetes cluster to try some of the examples. As mentioned above, Docker Desktop gives you the ability to also fire up a single node Kubernetes cluster and also configures `kubectl`. There are other ways to access a Kubernetes, such as MiniKube or kops for example. As the puppose of this repo is aimed at deploying application and not administrating a cluster, using the simplest option available is recommended

### Openshift / Minishift
I have chosen to download and run Minishift although the examples should work with regular Openshift. Again, I have chosen to go for a simple deployment in order to demonstrate how to deploy applications

There is a README file in the `openshift` directory with some information about running Minishift, inclusing some useful commands and links









