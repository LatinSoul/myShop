provider "aws" {
  region = "eu-west-3"  # Specify your desired AWS region
}

resource "aws_rds_cluster" "aurora_serverless" {
  cluster_identifier        = "aurora-serverless-cluster"
  engine                    = "aurora-postgresql"
  engine_version            = "10.7"
  availability_zones        = ["eu-west-3a"]  # Use a single availability zone
  db_subnet_group_name      = "default"   # Specify your DB subnet group name
  vpc_security_group_ids    = ["sg-0fc9067332e143822"]    # Specify your security group ID
  enable_http_endpoint      = true                          # Enable the Data API

  # Additional configuration for database and table creation
  apply_immediately         = true  # Apply changes immediately
  database_name             = "myShop"   # Specify your database name
  master_username           = "admin"  # Specify your master username
  master_password           = "password"  # Specify your master password
}
