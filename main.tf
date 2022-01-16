terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "3.26.0"
    }
    random = {
      source  = "hashicorp/random"
      version = "3.0.1"
    }
  }
  required_version = ">= 1.1.0"

  cloud {
    organization = "sciorcoppe"

    workspaces {
      name = "systemsEngineering"
    }
  }
}


provider "aws" {
  region = "us-west-2"
}



resource "random_pet" "sg" {}

resource "aws_instance" "web" {
  ami                    = "ami-830c94e3"
  instance_type          = "t2.micro"
  vpc_security_group_ids = [aws_security_group.web-sg.id]

  user_data = <<-EOF
              #!/bin/bash
              echo "breakpoint1"
              sudo ufw default allow outgoing
              echo "breakpoint2"
              #echo "" >> 
              sudo sed -i '1ideb mirror://mirrors.ubuntu.com/mirrors.txt precise main restricted universe multiverse\ndeb mirror://mirrors.ubuntu.com/mirrors.txt precise-updates main restricted universe multiverse\ndeb mirror://mirrors.ubuntu.com/mirrors.txt precise-backports main restricted universe multiverse\ndeb mirror://mirrors.ubuntu.com/mirrors.txt precise-security main restricted universe multiverse\n\n' /etc/apt/sources.list
              echo "breakpoint3"
              sudo apt-get update
              echo "breakpoint4"
              sudo apt-get install docker
              sudo service docker start
              sudo usermod -a -G docker ec2-user
              docker info
              sudo docker pull 099178467731.dkr.ecr.us-east-1.amazonaws.com/my-app:4
              EOF
}

resource "aws_security_group" "web-sg" {
  name = "${random_pet.sg.id}-sg"
  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  
}

output "web-address" {
  value = "${aws_instance.web.public_dns}:8080"
}

