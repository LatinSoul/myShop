terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 3.0"
    }
  }
}

provider "aws" {
  region = "eu-west-3"  # Change to your desired region
}

module "api_gw" {
  source = "./modules/api_gw"
}

module "lambda" {
  source = "./modules/lambda"
}

module "aurora" {
  source = "./modules/aurora"
}
