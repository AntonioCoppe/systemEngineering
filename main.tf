terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "3.26.0"
    }
  }
  required_version = ">= 1.1.1"

  cloud {
    organization = "systemEngineering"

    workspaces {
      name = "SystemEngineeringNew"
    }
  }
}


provider "aws" {
  region = "us-west-2"
}


resource "aws_instance" "web" {
  ami                    = "ami-830c94e3"
  instance_type          = "t2.micro"

  user_data = <<-EOF
              #!/bin/bash
              echo "Hello, World" > index.html
              nohup busybox httpd -f -p 8080 &
              EOF
}

output "web-address" {
  value = "${aws_instance.web.public_dns}:8080"
}
