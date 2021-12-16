terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 3.0"
    }
  }


  required_version = ">= 0.14.9"
}

provider "aws" {
  profile = "default"
  region  = "eu-central-1"
  access_key = "AKIAS2ZZ5VO3WRQOWOME"
  secret_key = "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"
}

resource "aws_instance" "app_server" {
  ami           = "ami-830c94e3"
  instance_type = "t2.micro"

  tags = {
    Name = "ExampleAppServerInstance"
  }
}
