# IAM Role for Lambda execution
resource "aws_iam_role" "lambda_execution_role" {
  name               = "lambda_execution_role"
  assume_role_policy = jsonencode({
    Version   = "2012-10-17",
    Statement = [{
      Effect    = "Allow",
      Principal = {
        Service = "lambda.amazonaws.com"
      },
      Action    = "sts:AssumeRole"
    }]
  })
}

# IAM Policy for Lambda execution role
resource "aws_iam_policy" "lambda_execution_policy" {
  name        = "lambda_execution_policy"
  description = "Policy for Lambda execution role"

  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [{
      Effect    = "Allow",
      Action    = [
        "logs:CreateLogGroup",
        "logs:CreateLogStream",
        "logs:PutLogEvents"
      ],
      Resource  = "arn:aws:logs:*:*:*"
    }]
  })
}

# Attach execution policy to Lambda execution role
resource "aws_iam_role_policy_attachment" "lambda_execution_policy_attachment" {
  role       = aws_iam_role.lambda_execution_role.name
  policy_arn = aws_iam_policy.lambda_execution_policy.arn
}

# Lambda functions
# Create Lambda functions
resource "aws_lambda_function" "lambda_functions" {
  count         = length(var.lambda_function_names)
  function_name = var.lambda_function_names[count.index]
  filename      = "target/myShop-0.0.1-SNAPSHOT-aws.jar"
  source_code_hash = filebase64sha256("target/myShop-0.0.1-SNAPSHOT-aws.jar")
  handler       = "com.dljm.myShop.controller.${var.lambda_function_names[count.index]}::handleRequest"
  runtime       = "java21"
  role          = aws_iam_role.lambda_execution_role.arn
}

