# SystemEngineering Project
Written by Giulio Sciortino and Antonio Coppe

## Overview
We wanted to develop a CI/CD solution to automate the building, testing and deployment of an application, consisting on a simple workflow that enables the aforementioned steps at every push performed on branch 'main'. In this pipeline we have in the following order:
 - Eclipse as IDE
 - Maven and JUnit 5 for Building and Testing
 - GitHub as repository
 - Docker as image builder
 - AWS as server provider
 - Terraform for infrastructure as code

## Technologies used
We have used the following technologies

- **Eclipse**: we used Eclipse as IDE to write the code of the application. The choice was based on our personal experience
- **Maven**: the application has been developed on eclipse as a Maven project. Maven makes it easier to provide the needed dependencies through the POM file
- **JUnit 5**: as seen in other courses, JUnit 5 is currently one of the most widely used tools for software testing.
- **GitHub**: we created a GitHub repository and its 'Actions' feature to develop a CI/CD pipeline. We also used some of the actions provided by the GitHub actions marketplace.
- **Docker**: one of the jobs performed in the pipeline was the building of a Docker image of the application. This image was then deployed to AWS ECR. Docker is for us the best solution to deploy an application. The image can indeed run on every machine, regardless of its operating system and without the need of installing a virtual machine on it.
- **AWS**: we used aws as a server provider. The final step for our pipeline was indeed to deploy the application on an aws. In particular we have used the following aws services:
  - Elastic Container Registry (ECR): that is a repository where docker images are tagged and stored. Using the aws credentials stored in GitHub secret, it is possible to pull images
  - Elastic Container Services (EC2): we used ECS to create a ec2 Ubuntu 20.04 instance where we install docker, awscli and pull the docker image from the ECR
- **Terraform**: EC2 instances are fully managed by terraform in main.tf, where we set the specifications for the container and its connection. This technology allowed us to skip manual setting every time a new instance had to be created.
- **Terraform Cloud**: terraform cloud is a service used to manage infrastructures and monitor ongoing plans.

## Our Pipeline solution
Our pipeline starts on push on branch main. The first job is to build and test the application via Maven. If the job succeeds, a docker image is created and deployed to: AWS's Elastic Container Registry (ECR) via GitHub actions and to Elastic Container Service (EC2) via Terraform. 

![This is an image](https://github.com/sciortinogiulio0/systemEngineering/blob/main/FinalPipeline.png)

A detailed explanation of the pipeline and its jobs is hereby presented:
- **Build and Test**: sets up the job creating an Ubuntu environment. Uses action setup-java@v1 to set up JDK11 and checkout@v2 to get repository info. Then runs the command 'mvn -B package --file pom.xml' to finally build the project via Maven. For the Demo version of the pipeline we skipped the testing phase.
- **Publish**: after a successful build of the project, the project is built and an artifact of the jar file is created with the command mkdir staging && cp target/*.jar staging.
- **Deploy**: the third step in this workflow is to build a Docker image via a simple bash script. It also configures the AWS credentials contained in the GitHub Secrets. The Docker image is tagged and pushed in the ECR via a CLI command.
- **Terraform**: the final step of the workflow accesses to Terraform Cloud, and creates the infrastructure implemented in main.tf and variable.tf, via the 'Format', 'Init', 'Validate' and 'Apply' jobs. In particular, after the 'Apply' job, a script is executed in the newly created EC2 instance to pull the docker image from the ECR.

