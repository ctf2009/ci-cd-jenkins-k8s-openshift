# Sample Project

The Sample Project is a very basic Spring Boot application.

You can Build / Run the application in two ways:

- Non Containerised
- Containerised

There are outlined below

# Building / Running Non Containerised

This section shows how to build the Sample Project into an executable Jar

### Build

From the root of this project you can simply run  `../gradlew -p ../ :sample:clean :sample:build`

From the root of the entire repository you can run `./gradlew :sample:clean :sample:build`

**Note:** Gradle needs "just enough" characters to uniquely identify the sub project 

### Output

Once built, the Spring Boot Jar will be located in `build/libs/sample-project.<X.Y.Z>.jar` 

### Running

You can run the application simply by executing the following from this directory:

`java -jar ./build/libs/sample-project.<X.Y.Z>.jar`

You can test the application is working by navigating to:

`http://localhost:9000/greeting`

If you wish to try out the `alternative` profile, you can do this by running

`java -jar -Dspring.profiles.active=alternative ./build/libs/sample-project.<X.Y.Z>.jar`

# Building / Running Containerised

This section shows how to build the Sample Project and produce a Docker image ready to run

### Build

The Sample application makes use of Jib, a tool made by Google. It enables you to build Docker images without a Dockerfile

**Note:** As we are going to be using the `jibDockerBuild` task, a local Docker daemon needs to be running.
Please see the Repo's main README for more information about requirements

Before proceeding, please ensure that Docker is running in your environment.
Run the following from the Sample App root to create a new image:

`../gradlew -p ../ :sample:jibDockerBuild`

### Output

Once complete, you can can run the following to see your newly created image

```shell script
docker image ls ctf/sample-app
REPOSITORY          TAG                 IMAGE ID            CREATED              SIZE
ctf/sample-app      latest              a8da2ebfedd2        About a minute ago   143MB
```

### Running

You can run up the image as follows: 

`docker run -p 9000:9000 ctf/sample-app`

You can test the application is working by navigating to:

`http://localhost:9000/greeting`

If you wish to try out the `alternative` profile, you can do this by running:

`docker run -p 9000:9000 -e "SPRING_PROFILES_ACTIVE=alternative" ctf/sample-app`

You can provide your own greeting by running the following:

`docker run -p 9000:9000 -e "GREETING=My Custom Greeting" ctf/sample-app`

