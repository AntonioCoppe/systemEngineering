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
  ami                    = "ami-02da538d84c7792a9"
  instance_type          = "t2.micro"
  vpc_security_group_ids = [aws_security_group.web-sg.id]

  user_data = <<-EOF
              #!/bin/bash
              sudo apt-get update -y
              sudo apt-get upgrade -y
              sudo apt-get install docker.io -y
              sudo apt-get install awscli -y
              sudo service docker start -y
              export AWS_ACCESS_KEY_ID=${var.aws_access_key_id}
              export AWS_SECRET_ACCESS_KEY=${var.aws_secret_access_key}
              export AWS_DEFAULT_REGION=us-west-2
              sudo docker login -u AWS -p $(aws ecr get-login-password --region us-west-2) 099178467731.dkr.ecr.us-west-2.amazonaws.com
              sudo docker pull 099178467731.dkr.ecr.us-west-2.amazonaws.com/systems_engineering:7
              EOF
}

resource "aws_security_group" "web-sg" {
  name = "${random_pet.sg.id}-sg"
  ingress {
    description = "SSH"
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

output "web-address" {
  value = "${aws_instance.web.public_dns}:8080"
}

