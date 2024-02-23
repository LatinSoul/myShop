resource "aws_rds_cluster" "my_serverless_cluster" {
  cluster_identifier        = "my-serverless-cluster"
  engine                    = "aurora"
  engine_mode               = "serverless"
  engine_version            = "5.6.10a"
  master_username           = "admin"
  master_password           = "password"
  scaling_configuration {
    auto_pause               = true
    min_capacity             = 1
    max_capacity             = 16
    seconds_until_auto_pause = 300
  }
  tags = {
    Name = "my-serverless-cluster"
  }
}

resource "aws_rds_cluster_instance" "my_serverless_instance" {
  cluster_identifier      = aws_rds_cluster.my_serverless_cluster.id
  instance_class         = "db.t3.medium"
  tags = {
    Name = "my-serverless-instance"
  }
}

resource "aws_db_instance" "my_serverless_db_instance" {
  engine            = "aurora"
  engine_mode       = "serverless"
  instance_class    = "db.t3.medium"
  allocated_storage = 20
  name              = "my-serverless-db-instance"

  provisioner "local-exec" {
    command = "mysql -h ${self.address} -u admin -ppassword < create_tables.sql"
  }
}
