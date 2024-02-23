# Creating an Aurora Serverless Cluster:
aws rds create-db-cluster \
  --db-cluster-identifier my-serverless-cluster \
  --engine aurora \
  --engine-mode serverless \
  --engine-version 5.6.10a \
  --scaling-configuration MinCapacity=1,MaxCapacity=16,AutoPause=true,SecondsUntilAutoPause=300 \
  --master-username admin \
  --master-user-password password \
  --tags Key=Name,Value=my-serverless-cluster

# Creating a Database in the Cluster:
aws rds create-db-instance \
  --db-instance-identifier my-serverless-instance \
  --db-cluster-identifier my-serverless-cluster \
  --db-instance-class db.t3.medium \
  --tags Key=Name,Value=my-serverless-instance

# Creating Tables in the Database (Example: User table):
# Note: Execute the SQL statements using a MySQL client connected to the Aurora Serverless cluster.
CREATE TABLE User (
    UserID INT AUTO_INCREMENT,
    Username VARCHAR(50),
    Email VARCHAR(100),
    Password VARCHAR(100),
    Role VARCHAR(20),
    RegistrationDate DATETIME,
    PRIMARY KEY (UserID)
);
