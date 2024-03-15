# Import Lambda function names from the Lambda module
module "lambda" {
  source = "../lambda"  # Path to the Lambda module directory
}

# Create API Gateway
resource "aws_api_gateway_rest_api" "api_gateway" {
  name = "my-api-gateway"
}

# Create API Gateway resources and methods for each Lambda function
resource "aws_api_gateway_resource" "api_gateway_resources" {
  count       = length(module.lambda.lambda_function_names)
  rest_api_id = aws_api_gateway_rest_api.api_gateway.id
  parent_id   = aws_api_gateway_rest_api.api_gateway.root_resource_id
  path_part   = module.lambda.lambda_function_names[count.index]
}

// Create a method for each Lambda function
resource "aws_api_gateway_method" "api_gateway_methods" {
  rest_api_id = aws_api_gateway_rest_api.api_gateway.id
  resource_id = aws_api_gateway_resource.api_gateway_resources[count.index].id
  http_method = "POST"
  authorization = ""
}

// Integration between API Gateway and Lambda function
resource "aws_api_gateway_integration" "api_gateway_integrations" {
  rest_api_id = aws_api_gateway_rest_api.api_gateway.id
  resource_id = aws_api_gateway_resource.api_gateway_resources[count.index].id
  http_method = aws_api_gateway_method.api_gateway_methods[count.index].http_method
  integration_http_method = "POST"
  type                    = "AWS_PROXY"
  uri                     = module.lambda.lambda_function_names[count.index].invoke_arn
}

# Deploy API Gateway
resource "aws_api_gateway_deployment" "api_gateway_deployment" {
  depends_on  = [aws_api_gateway_resource.api_gateway_resources, aws_api_gateway_integration.api_gateway_integrations]
  rest_api_id = aws_api_gateway_rest_api.api_gateway.id
  stage_name  = "dev"
}