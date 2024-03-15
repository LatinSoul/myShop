
# Define output variable to export lambda function ARNs
output "lambda_function_arns" {
  description = "ARNs of the Lambda functions"
  value       = [for name in var.lambda_function_names : aws_lambda_function[name].lambda_functions.arn]
}
output "lambda_function_names" {
  value = var.lambda_function_names
}
